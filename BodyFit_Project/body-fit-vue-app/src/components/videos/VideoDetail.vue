<template>
  <div>
    <div class="mx-auto mb-4 iframe-container">
      <iframe
        width="1200"
        height="700"
        :src="`https://www.youtube.com/embed/${video.videoUrl}`"
        title="YouTube video player"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen
      ></iframe>
    </div>
    <div>
      <div class="row">
        <div
          class="video-infos col-10"
          style="font-family: 'Gowun Batang', serif"
        >
          <h3>{{ video.title }}</h3>
          <div class="d-flex">
            <p class="me-4">{{ video.channel }}</p>
            <p class="me-4">조회수 {{ video.viewCount }}회</p>
            <p class="me-4">좋아요 {{ video.likeCount }}개</p>
          </div>
        </div>
        <div
          class="
            col-2
            text-center
            d-flex
            justify-content-end
            align-items-center
          "
        >
          <button v-if="!videos.isLiked" class="btn btn-danger" @click="like">
            좋아요 ♡
          </button>
          <button v-else class="btn btn-outline-danger" @click="like">
            좋아요 취소
          </button>
        </div>
      </div>
      <div>
        <p style="font-family: 'Gowun Dodum', sans-serif">
          {{ video.description }}
        </p>
      </div>
    </div>
    <div>
      <hr />
      <div class="d-flex justify-content-between">
        <div>
          <h4
            style="
              display: inline-block;
              margin-right: 5px;
              font-family: 'Gowun Batang', serif;
            "
          >
            리뷰
          </h4>
          <span style="font-family: 'Gowun Dodum', sans-serif"
            >{{ video.reviewCount }}개의 리뷰</span
          >
        </div>
        <div>
          <button class="btn btn-success" @click="toCreateReview">
            리뷰 작성
          </button>
        </div>
      </div>
      <review-list />
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";

import ReviewList from "./reviews/ReviewList.vue";
export default {
  name: "VideoDetail",
  components: { ReviewList },
  computed: {
    ...mapState(["videos", "users"]),
    ...mapGetters(["loginUser"]),
    video() {
      return this.videos.video;
    },
  },
  created() {
    let id = this.$route.params.videoId;
    this.$store.dispatch("getVideoByVideoId", id);
    if (this.users.LoginNickname) {
      let params = {
        videoId: this.$route.params.videoId,
        userSeq: this.loginUser.userSeq,
      };
      this.$store.dispatch("getIsLiked", params);
    }
  },
  methods: {
    toCreateReview() {
      if (!this.users.LoginNickname) {
        alert("로그인이 필요합니다!!");
        this.$router.push("/users/login");
      } else {
        this.$router.push({
          name: "ReviewCreate",
          params: { videoId: this.$route.params.videoId },
        });
      }
    },
    like() {
      if (!this.users.LoginNickname) {
        alert("로그인이 필요합니다!!");
        this.$router.push("/users/login");
      } else {
        let params = {
          videoId: this.$route.params.videoId,
          userSeq: this.loginUser.userSeq,
        };
        this.$store.dispatch("toggleIsLiked", params);
      }
    },
  },
};
</script>

<style scoped>
.iframe-container {
  width: 1200px;
}
</style>
