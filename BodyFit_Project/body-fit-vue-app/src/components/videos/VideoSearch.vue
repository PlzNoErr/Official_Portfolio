<template>
  <div>
    <div style="margin-bottom: 20px;">
      <h3 class="text-center" style="font-family: 'Gowun Dodum', sans-serif; padding-top: 30px;">영상 저장소</h3><hr>
    </div>
    <search-box
      :mod-list="searchMod"
      :orderList="searchOrder"
      @search-event="searchVideo"
    />
    <h3 class="my-3 mx-2" style="font-family: 'Gowun Dodum', sans-serif;">{{ title }}</h3>
    <div class="row row-cols-4 py-3 video-container">
      <div
        class="video-module col"
        v-for="(video, index) in videos.searchedVideoList"
        :key="index"
      >
        <video-column :video="video" />
      </div>
    </div>
    <pagination-box @page-change="pageChange" :nav="videos.pageNavigation" />
  </div>
</template>

<script>
import SearchBox from "../common/SearchBox.vue";
import PaginationBox from "../common/PaginationBox.vue";
import VideoColumn from "./VideoColumn.vue";
import { mapState } from "vuex";
export default {
  name: "VideoSearch",
  components: { SearchBox, PaginationBox, VideoColumn },
  data() {
    return {
      searchMod: [
        {
          key: "title",
          word: "제목",
        },
        {
          key: "channel",
          word: "채널",
        },
        {
          key: "description",
          word: "설명",
        },
        {
          key: "category",
          word: "카테고리",
        },
      ],
      searchOrder: [
        {
          key: "",
          word: "최신 순",
        },
        {
          key: "view_count",
          word: "조회수 순",
        },
        {
          key: "like_count",
          word: "좋아요 순",
        },
        {
          key: "review_count",
          word: "리뷰 많은 순",
        },
      ],
    };
  },
  computed: {
    ...mapState(["videos"]),
    title() {
      return "최근 등록된 영상";
    },
  },
  methods: {
    searchVideo(params) {
      this.$store.dispatch("searchVideos", params);
    },
    pageChange(num) {
      let params = this.videos.searchedParams;
      params.currentPage = num;
      this.$store.dispatch("searchVideos", params);
    },
  },
  created() {
    if (this.$route.name === "VideoSearch")
      this.$store.dispatch("searchVideos", {});
  },
};
</script>

<style>
.video-container {
  width: 1200px;
  margin-left: 0px;
}
.video-module {
  width: 300px;
  padding: 5px;
}
</style>
