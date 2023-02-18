<template>
  <div>
    <div class="detail-container">
      <div style="padding: 15px">
        <h2 style="font-family: 'Gowun Batang'">{{ articles.article.title }}</h2>
        <div style="text-align: right" v-if="isWriter">
          <router-link
            :to="`/articles/${articles.article.articleId}/update`"
            type="button"
            class="btn btn-outline-info me-1"
            style="width: 60px"
          >
            수정
          </router-link>
          <button
            type="button"
            @click.prevent="deleteArticle"
            class="btn btn-outline-danger"
            style="width: 80px"
          >
            삭제
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-trash"
              viewBox="0 0 16 16"
            >
              <path
                d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"
              />
              <path
                fill-rule="evenodd"
                d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"
              />
            </svg>
          </button>
        </div>
        <div style="height: 5px" v-else />
        <span style="margin: 0px 30px 0px 0px">
          <a
            class="a-for-follow"
            @click="
              clickedFollow(articles.article.userSeq, articles.article.nickName)
            "
            data-bs-toggle="modal"
            data-bs-target="#exampleModal"
            style="font-family: 'Gowun Dodum', sans-serif;"
          >
            작성자 : {{ articles.article.nickName }}</a
          ></span
        >
        <span style="font-family: 'Gowun Dodum', sans-serif;">조회수 : {{ articles.article.viewCount }}</span>
        <div class="contentbox" style="font-family: 'Gowun Dodum', sans-serif; font-size: 20px">
          {{ articles.article.content }}
        </div>
      </div>
    </div>

    <comment-list
      :comment-list="articles.articleCommentList"
      :nav="articles.commentPageNavigation"
      @page-change="pageChange"
      @create-comment="createComment"
      @create-recomment="createReComment"
      @delete-comment="deleteComment"
    />
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import $ from "@/util/http-common";
import CommentList from "@/components/comments/CommentList.vue";
export default {
  name: "ArticleDetail",

  components: { CommentList },

  methods: {
    deleteArticle() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        let API_URL = `/api/articles/${this.articles.article.articleId}`;

        $.delete(API_URL)
          .then(() => {
            this.$router.push("/articles");
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    clickedFollow(userSeqnum, nickNamenum) {
      let info = { userSeq: userSeqnum, nickName: nickNamenum };
      this.$store.commit("UPDATE_FOLLOW", info);
      let params = {
        followedSeq: this.users.ClickedUser,
        followingSeq: this.loginUser.userSeq,
      };
      this.$store.dispatch("getIsFollowed", params);
    },

    createComment(data) {
      data.articleId = this.articles.article.articleId;
      this.$store.dispatch("createArticleComment", data);
    },
    createReComment(data) {
      data.articleId = this.articles.article.articleId;
      this.$store.dispatch("createArticleComment", data);
    },
    deleteComment(commentId) {
      let params = {
        commentId: commentId,
        articleId: this.$route.params.articleId,
      };
      this.$store.dispatch("deleteArticleComment", params);
    },
    pageChange(num) {
      this.$store.dispatch("getArticleComments", {
        articleId: this.$route.params.articleId,
        pageNum: num,
      });
    },
  },

  computed: {
    ...mapState(["articles", "users"]),
    ...mapGetters(["loginUser"]),
    isWriter() {
      if (this.articles.article.boardType === 1) {
        return false;
      }
      if (this.articles.article.nickName === this.loginUser.nickName) {
        return true;
      } else {
        return false;
      }
    },
  },

  created() {
    if (this.$route.name === "ArticleDetail")
      this.$store.dispatch(
        "getArticleByArticleId",
        this.$route.params.articleId
      );
    this.$store.dispatch("getRecentArticleComments", {
      articleId: this.$route.params.articleId,
      pageNum: 1,
    });
  },
};
</script>

<style scoped>
.detail-container {
  min-height: 350px;
  border-radius: 10px;
  border: 1px solid #afaeac;
  border-radius: 16px;
  box-shadow: inset 0 0 4px #ababab;
}
.contentbox {
  margin: 35px 0px 30px 0px;
}

.btn-outline-info {
    --bs-btn-color: #125e07;
    --bs-btn-border-color: #125e07;
    --bs-btn-hover-color: rgb(230, 212, 212);
    --bs-btn-hover-bg: #439336;
    --bs-btn-hover-border-color: #4aa73b;
    --bs-btn-focus-shadow-rgb: 13,202,240;
    --bs-btn-active-color: #000;
    --bs-btn-active-bg: #0dcaf0;
    --bs-btn-active-border-color: #0dcaf0;
    --bs-btn-active-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
    --bs-btn-disabled-color: #0dcaf0;
    --bs-btn-disabled-bg: transparent;
    --bs-btn-disabled-border-color: #0dcaf0;
    --bs-gradient: none;
}

</style>
