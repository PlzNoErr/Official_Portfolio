<template>
  <div class="video-col" style="font-family: 'Gowun Dodum', sans-serif;">
    <router-link
      class="video-link"
      :to="{ name: 'VideoDetail', params: { videoId: video.videoId } }"
    >
      <img :src="imgSrc" alt="thumbnail" class="video-thumbnail" />
      <h4 class="video-title">{{ video.title }}</h4>
    </router-link>
    <div class="d-flex justify-content-between align-items-center">
      <div>{{ video.channel }}</div>
      <div>조회수 {{ video.viewCount }}회</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "VideoColumn",
  props: {
    video: {
      type: Object,
    },
  },
  computed: {
    imgSrc() {
      return `https://i1.ytimg.com/vi/${this.video.videoUrl}/mqdefault.jpg`;
    },
  },
};
</script>

<style scoped>
.video-col {
  padding: 5px;
  width: 290px;
  height: 270px;
  margin-bottom: 10px;
}
.video-link {
  color: black;
  text-decoration: none;
}
.video-thumbnail {
  background-color: black;
  width: 270px;
  border-radius: 10px;
  margin-bottom: 10px;
}
.video-title {
  font-size: 1.2rem;
  font-weight: 550;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
