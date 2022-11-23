<template>
  <div>
    <div class="detail-container">
      <div class="video-info d-flex" style="padding: 15px">
        <div>
          <img
            :src="`https://i1.ytimg.com/vi/${video.videoUrl}/mqdefault.jpg`"
            alt="thumbnail"
            class="video-thumbnail me-3"
          />
        </div>
        <div>
          <router-link
            :to="{ name: 'VideoDetail', params: { videoId: video.videoId } }"
            id="no-more-a-tag"
            style="font-family: 'Gowun Batang', serif"
          >
            {{ video.title }}
          </router-link>
          <div style="font-family: 'Gowun Dodum', sans-serif; font-size: 18px;">{{ video.channel }}</div>
          <div style="font-family: 'Gowun Dodum', sans-serif; font-size: 17px;">조회수 {{ video.viewCount }}회</div>
        </div>
      </div>
      <hr />
      <div class="review-details mb-5" style="padding: 15px">
        <div class="row review-info-container" style="font-family: 'Gowun Dodum', sans-serif">
          <div class="review-infos col-9">
            <h3 style="font-family: 'Gowun Batang', serif">{{ review.title }}</h3>
            <div class="d-flex">
              <p class="me-4">
                <a
                  class="a-for-follow"
                  @click="clickedFollow(review.userSeq, review.nickName)"
                  data-bs-toggle="modal"
                  data-bs-target="#exampleModal"
                  >{{ review.nickName }}</a
                >
              </p>
              <p class="me-4">조회수 {{ review.viewCount }}회</p>
              <p class="me-4">{{ reviewCreateTime }}</p>
            </div>
          </div>
          <div
            v-if="review.nickName === users.LoginNickname"
            class="
              col-3
              text-center
              d-flex
              justify-content-end
              align-items-center
            "
          >
            <button class="btn btn-outline-info me-1" @click="updateReview">
              수정
            </button>
            <button class="btn btn-outline-danger" @click="deleteReview">
              삭제
            </button>
          </div>
        </div>
        <div>
          <div class="review-content" style="font-family: 'Gowun Dodum', sans-serif; font-size: 20px;">{{ review.content }}</div>
        </div>
      </div>
    </div>
    <comment-list
      :comment-list="reviews.reviewCommentList"
      :nav="reviews.commentPageNavigation"
      @page-change="pageChange"
      @create-comment="createComment"
      @create-recomment="createReComment"
      @delete-comment="deleteComment"
    />
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import CommentList from "@/components/comments/CommentList.vue";
export default {
  name: "ReviewDetail",
  components: { CommentList },
  computed: {
    ...mapState(["reviews", "videos", "users"]),
    ...mapGetters(["loginUser"]),
    video() {
      return this.videos.video;
    },
    review() {
      return this.reviews.review;
    },
    reviewCreateTime() {
      return new Date(
        this.review.createTime - 1000 * 60 * 60 * 9
      ).toLocaleString();
    },
  },
  methods: {
    createComment(data) {
      data.reviewId = this.review.reviewId;
      this.$store.dispatch("createReviewComment", data);
    },
    createReComment(data) {
      data.reviewId = this.review.reviewId;
      this.$store.dispatch("createReviewComment", data);
    },
    deleteComment(commentId) {
      let params = {
        commentId: commentId,
        reviewId: this.$route.params.reviewId,
      };
      this.$store.dispatch("deleteReviewComment", params);
    },
    updateReview() {
      this.$router.push({
        name: "ReviewUpdate",
        params: { reviewId: this.$route.params.reviewId },
      });
    },
    deleteReview() {
      this.$store.dispatch("deleteReview", {
        reviewId: this.$route.params.reviewId,
        videoId: this.video.videoId,
      });
    },
    clickedFollow(userSeqnum, nickNamenum, ClickedUserSeqnum) {
      let info = {
        userSeq: userSeqnum,
        nickName: nickNamenum,
        ClickedUserSeq: ClickedUserSeqnum,
      };
      this.$store.commit("UPDATE_FOLLOW", info);
      let params = {
        followedSeq: this.users.ClickedUser,
        followingSeq: this.loginUser.userSeq,
      };
      this.$store.dispatch("getIsFollowed", params);
    },
    pageChange(num) {
      this.$store.dispatch("getReviewComments", {
        reviewId: this.$route.params.reviewId,
        pageNum: num,
      });
    },
  },
  created() {
    this.$store.dispatch("getReviewByReviewId", this.$route.params.reviewId);
    this.$store.dispatch("getRecentReviewComments", {
      reviewId: this.$route.params.reviewId,
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
.review-content {
  width: 100%;
  white-space: normal;
  word-wrap: break-word;
}
.btn-outline-info {
  --bs-btn-color: #125e07;
  --bs-btn-border-color: #125e07;
  --bs-btn-hover-color: rgb(230, 212, 212);
  --bs-btn-hover-bg: #439336;
  --bs-btn-hover-border-color: #4aa73b;
  --bs-btn-focus-shadow-rgb: 13, 202, 240;
  --bs-btn-active-color: #000;
  --bs-btn-active-bg: #0dcaf0;
  --bs-btn-active-border-color: #0dcaf0;
  --bs-btn-active-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
  --bs-btn-disabled-color: #0dcaf0;
  --bs-btn-disabled-bg: transparent;
  --bs-btn-disabled-border-color: #0dcaf0;
  --bs-gradient: none;
}
#no-more-a-tag {
  text-decoration: none;
  color: #333;
  font-size: 24px;
}
#no-more-a-tag:hover {
  color: rgb(99, 105, 215);
}
.video-thumbnail {
  background-color: black;
  width: 270px;
  border-radius: 10px;
  margin-bottom: 10px;
}
</style>
