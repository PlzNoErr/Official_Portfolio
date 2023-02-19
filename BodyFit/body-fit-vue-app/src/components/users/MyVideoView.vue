<template>
  <div class="container">
    <h3 class="text-center" style="font-family: 'Gowun Dodum', sans-serif;">MY 찜한 영상</h3>
    <hr />
    <div class="row row-cols-4 py-3 video-container">
      <div
        class="video-module col"
        v-for="(video, index) in videos.likedVideoList"
        :key="index"
      >
        <video-column :video="video" />
      </div>
    </div>
    <pagination-box @page-change="pageChange" :nav="videos.pageNavigation" />
  </div>
</template>

<script>
import PaginationBox from "../common/PaginationBox.vue";
import VideoColumn from "../videos/VideoColumn.vue";
import { mapState } from "vuex";
export default {
  name: "MyVideoView",
  components: { PaginationBox, VideoColumn },
  computed: {
    ...mapState(["videos"]),
  },
  methods: {
    pageChange(num) {
      this.$store.dispatch("searchlikedVideos", num);
    },
  },
  created() {
    if (this.$route.name === "MyVideoView")
      this.$store.dispatch("searchlikedVideos", "");
  },
};
</script>

<style scoped>
.video-container {
  width: 960px;
  margin: 0 auto;
}
.video-module {
  width: 300px;
}
.container {
  padding-top: 30px;
  text-align: center;
  width: 900px;
  margin: 0 auto;
  align-items: center;
}
</style>
