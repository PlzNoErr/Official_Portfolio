<template>
  <div>
    <div v-if="title.length > 0">
      <h3 class="row-title">{{ title }}</h3>
    </div>
    <div v-else>
      <ul class="nav justify-content-center nav-pills">
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: videos.categoryNow === '전신' }"
            href="#"
            @click.prevent="setCategory('전신')"
            >전신</a
          >
        </li>
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: videos.categoryNow === '상체' }"
            href="#"
            @click.prevent="setCategory('상체')"
            >상체</a
          >
        </li>
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: videos.categoryNow === '하체' }"
            href="#"
            @click.prevent="setCategory('하체')"
            >하체</a
          >
        </li>
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: videos.categoryNow === '코어' }"
            href="#"
            @click.prevent="setCategory('코어')"
            >코어</a
          >
        </li>
        <li class="nav-item">
          <a
            class="nav-link"
            :class="{ active: videos.categoryNow === '기타' }"
            href="#"
            @click.prevent="setCategory('기타')"
            >기타</a
          >
        </li>
      </ul>
    </div>
    <div :id="idprop" class="carousel slide" data-bs-ride="true">
      <div class="carousel-inner">
        <div class="carousel-item carousel-item-a active">
          <div class="d-flex justify-content-center">
            <div v-for="(video, index) in videos1" :key="index">
              <video-column :video="video" />
            </div>
          </div>
        </div>
        <div v-if="videos2.length > 0" class="carousel-item carousel-item-a">
          <div class="d-flex justify-content-center">
            <div v-for="(video, index) in videos2" :key="index">
              <video-column :video="video" />
            </div>
          </div>
        </div>
        <div v-if="videos3.length > 0" class="carousel-item carousel-item-a">
          <div class="d-flex justify-content-center">
            <div v-for="(video, index) in videos3" :key="index">
              <video-column :video="video" />
            </div>
          </div>
        </div>
      </div>
      <button
        class="carousel-control-prev-2"
        type="button"
        :data-bs-target="`#${idprop}`"
        data-bs-slide="prev"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="20"
          height="60"
          fill="currentColor"
          class="bi bi-chevron-compact-left"
          viewBox="0 0 16 16"
        >
          <path
            fill-rule="evenodd"
            d="M9.224 1.553a.5.5 0 0 1 .223.67L6.56 8l2.888 5.776a.5.5 0 1 1-.894.448l-3-6a.5.5 0 0 1 0-.448l3-6a.5.5 0 0 1 .67-.223z"
          />
        </svg>
        <span class="visually-hidden">Previous</span>
      </button>
      <button
        class="carousel-control-next-2"
        type="button"
        :data-bs-target="`#${idprop}`"
        data-bs-slide="next"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="20"
          height="60"
          fill="currentColor"
          class="bi bi-chevron-compact-right"
          viewBox="0 0 16 16"
        >
          <path
            fill-rule="evenodd"
            d="M6.776 1.553a.5.5 0 0 1 .671.223l3 6a.5.5 0 0 1 0 .448l-3 6a.5.5 0 1 1-.894-.448L9.44 8 6.553 2.224a.5.5 0 0 1 .223-.671z"
          />
        </svg>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import VideoColumn from "./VideoColumn.vue";
export default {
  name: "VideoRow",
  props: {
    title: String,
    videoList: Array,
    idprop: String,
  },
  components: { VideoColumn },
  computed: {
    ...mapState(["videos"]),
    videos1() {
      return this.videoList.slice(0, 4);
    },
    videos2() {
      return this.videoList.slice(4, 8);
    },
    videos3() {
      return this.videoList.slice(8, 12);
    },
    // videos4() {
    //   return this.videoList.slice(9, 12);
    // },
  },
  methods: {
    setCategory(category) {
      this.$store.dispatch("getCategoryVideoList", category);
    },
  },
};
</script>

<style scoped>
.row-title {
  margin: 5px 30px;
}
.black {
  color: black;
}
.button-place {
  width: 100px;
}
.carousel-item-a {
  /* background-color: white; */
  height: 280px;
}
.carousel-control-prev-2 {
  position: absolute;
  top: 0px;
  left: 0px;
  height: 100%;
  border: 0;
  background: none;
  padding-left: 0;
  padding-right: 0;
}
.carousel-control-next-2 {
  position: absolute;
  top: 0px;
  right: 0px;
  height: 100%;
  border: 0;
  background: none;
  padding-left: 0;
  padding-right: 0;
}
.nav-pills {
  --bs-nav-link-color: black;
  --bs-nav-link-hover-color: rgba(0, 0, 0, 0.5);
  --bs-nav-pills-link-active-bg: rgba(0, 0, 0, 0.5);
}
</style>
