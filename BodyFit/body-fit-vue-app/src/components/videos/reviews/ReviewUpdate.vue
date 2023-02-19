<template>
  <div>
    <div class="video-info d-flex">
      <div>
        <img
          :src="`https://i1.ytimg.com/vi/${video.videoUrl}/mqdefault.jpg`"
          alt="thumbnail"
          class="video-thumbnail me-3"
          style="border-radius: 10px;"
        />
      </div>
      <div style="font-family: 'Gowun Dodum', sans-serif;">
        <h4 class="video-title">{{ video.title }}</h4>
        <div>{{ video.channel }}</div>
        <div>조회수 {{ video.viewCount }}회</div>
      </div>
    </div>
    <hr />
    <form @submit.prevent="updateReview" class="review-form" >
      <h3 style="font-family: 'Gowun Dodum', sans-serif;">리뷰 수정하기</h3>
      <div class="my-3" >
        <label for="review-title" class="form-label" style="font-family: 'Gowun Dodum', sans-serif;">제목</label>
        <input
          type="text"
          class="form-control"
          id="review-title"
          v-model="title"
          placeholder="제목을 입력해주세요."
        />
      </div>

      <div class="my-3">
        <label for="review-content" class="form-label" style="font-family: 'Gowun Dodum', sans-serif;">내용</label>
        <textarea
          class="form-control"
          id="review-content"
          v-model="content"
          row="10"
          placeholder="내용을 입력해주세요."
        />
      </div>
      <div>
        <button class="btn btn-primary me-3">수정하기</button>
        <router-link
          :to="{
            name: 'ReviewDetail',
            params: { reviewId: $route.params.reviewId },
          }"
          class="btn btn-secondary"
          >돌아가기</router-link
        >
      </div>
    </form>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
  name: "ReviewUpdate",
  components: {},
  data() {
    return {
      title: "",
      content: "",
    };
  },
  computed: {
    ...mapState(["reviews", "videos", "users"]),
    ...mapGetters(["loginUser"]),
    video() {
      return this.videos.video;
    },
    review() {
      return this.reviews.review;
    },
  },
  methods: {
    updateReview() {
      let data = {
        title: this.title,
        content: this.content,
        reviewId: this.$route.params.reviewId,
      };
      this.$store.dispatch("updateReview", data);
    },
  },
  created() {
    this.$store.dispatch("getReviewByReviewId", this.$route.params.reviewId);
    this.$store.dispatch("getReviewComments", {
      reviewId: this.$route.params.reviewId,
      pageNum: 1,
    });
  },
  watch: {
    review() {
      this.title = this.review.title;
      this.content = this.review.content;
    },
  },
};
</script>

<style scoped>
.review-content {
  width: 100%;
  white-space: normal;
  word-wrap: break-word;
}
</style>
