<template>
  <div>
    <div class="d-flex table-col">
      <div
        class="col-title"
        :class="{ deleted: comment.deleted != 0 }"
        :style="{ 'padding-left': comment.depth * 10 + 'px' }"
      >
        <span v-if="comment.depth > 0 && comment.deleted < 1">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-arrow-return-right"
            viewBox="0 0 16 16"
          >
            <path
              fill-rule="evenodd"
              d="M1.5 1.5A.5.5 0 0 0 1 2v4.8a2.5 2.5 0 0 0 2.5 2.5h9.793l-3.347 3.346a.5.5 0 0 0 .708.708l4.2-4.2a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708.708L13.293 8.3H3.5A1.5 1.5 0 0 1 2 6.8V2a.5.5 0 0 0-.5-.5z"
            />
          </svg>
        </span>
        {{ comment.content }}
      </div>
      <div class="col-nickname">
        <a
          class="a-for-follow"
          @click="clickedFollow(comment.userSeq, comment.nickName)"
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
        >
          {{ comment.nickName }}</a
        >
      </div>
      <div class="col-time">{{ comment.createTimeStr }}</div>
      <div class="col-btns">
        <button
          v-if="comment.nickName && comment.deleted == 0"
          @click="toggleReComment"
          class="btn btn-sm btn-outline-secondary me-1"
        >
          답글
        </button>
        <button
          v-if="
            users.LoginNickname === comment.nickName && comment.deleted == 0
          "
          @click="deleteComment"
          class="btn btn-sm btn-outline-danger"
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
    </div>
    <div v-show="formOpen">
      <comment-form @create-comment="createReComment" />
    </div>
  </div>
</template>

<script>
import CommentForm from "./CommentForm.vue";
import { mapGetters, mapState } from "vuex";
export default {
  name: "CommentRow",
  components: { CommentForm },
  props: { comment: { type: Object } },
  computed: {
    ...mapState(["users"]),
    ...mapGetters(["loginUser"]),
  },
  data() {
    return {
      formOpen: false,
    };
  },
  created() {
    this.formOpen = this.comment.formOpen;
  },
  methods: {
    createReComment(content) {
      const data = {
        content: content,
        userSeq: this.loginUser.userSeq,
        originalCommentId: this.comment.commentId,
      };
      this.$emit("create-recomment", data);
      this.formOpen = false;
    },
    toggleReComment() {
      this.formOpen = !this.formOpen;
    },
    deleteComment() {
      this.$emit("delete-comment", this.comment.commentId);
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
  },
};
</script>

<style scoped>
.table-title {
  font-size: 1.2rem;
  font-weight: bold;
  border-bottom: solid 1px rgb(138, 138, 138);
}
.table-col {
  border-top: solid 1px rgb(221, 221, 221);
  padding: 5px;
  height: 42px;
}
.deleted {
  color: gray;
}
.col-title {
  width: 700px;
}
.col-nickname {
  width: 120px;
}
.col-time {
  width: 250px;
}
.col-btns {
  width: 140px;
}
</style>
