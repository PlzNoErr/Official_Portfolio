<template>
  <div class="main-container">
    <!-- <div class="main-banner" :style="{ 'background-image': bannerSrc }"></div> -->
    <video-row
      title="최근 핫한 영상들"
      :idprop="'hotvideos'"
      :videoList="recommend"
      style="font-family: 'Gowun Dodum', sans-serif;"
    />
    <video-row :title="``" :idprop="'categories'" :video-list="categories" style="font-family: 'Gowun Dodum', sans-serif;" />
  </div>
</template>

<script>
import VideoRow from "@/components/videos/VideoRow.vue";
import { mapState } from "vuex";
export default {
  name: "HomeView",
  data() {
    return {
      bannerSrc: require("@/static/img/athlete.jpg"),
    };
  },
  components: { VideoRow },
  created() {
    this.$store.dispatch("getRecommendVideoList");
    this.$store.dispatch("getCategoryVideoList", "전신");
  },
  computed: {
    ...mapState(["videos"]),
    recommend() {
      return this.videos.recommendVideoList;
    },
    categories() {
      return this.videos.categoryVideoList;
    },
  },
  methods: {},
};
</script>

<style>
.main-container {
  width: 1280px;
  margin: 0 auto;
  padding: 20px 40px 40px;
  min-height: 700px;
  background: rgba(255, 255, 255, 0.8);
}
.main-banner {
  width: 1200px;
  height: 200px;
  margin-top: -20px;
  margin-bottom: 20px;
  background-image: url("../static/img/athlete.jpg");
  background-position: center;
  background-size: cover;
}
</style>
