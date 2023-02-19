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
        <h4 class="video-title" >{{ video.title }}</h4>
        <div>{{ video.channel }}</div>
        <div>조회수 {{ video.viewCount }}회</div>
      </div>
    </div>
    <hr />
    <form @submit.prevent="createReview" class="review-form">
      <h3 style="font-family: 'Gowun Dodum', sans-serif;">리뷰 작성하기</h3>
      <div class="my-3">
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
        <button class="btn btn-primary me-3">등록하기</button>
        <router-link
          :to="{
            name: 'VideoDetail',
            params: { videoId: $route.params.videoId },
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
  name: "ReviewCreate",
  created() {
    let id = this.$route.params.videoId;
    if (this.videos.video.videoId !== id) {
      this.$store.dispatch("getVideoByVideoId", id);
    }
  },
  computed: {
    ...mapState(["videos", "reviews"]),
    ...mapGetters(["loginUser"]),
    video() {
      return this.videos.video;
    },
  },
  methods: {
    createReview() {
      if (this.title === "" || this.content === "") {
        alert("빈칸을 채워주세요");
        return;
      }
      let data = {
        videoId: this.video.videoId,
        userSeq: this.loginUser.userSeq,
        title: this.title,
        content: this.content,
      };
      this.$store.dispatch("createReview", data);
    },
  },
  data() {
    return {
      title: "",
      content: "",
    };
  },
};
</script>

<style></style>
