<template>
  <div class="main-container">
    <h2 style="font-family: 'Gowun Dodum', sans-serif;">관리자 페이지</h2><br>
    <h4>비디오 등록</h4>
    <button class="btn btn-outline-primary" @click="toggleVC">열기/닫기</button>
    <div v-show="videoCreateForm">
      <form @submit.prevent="createVideo">
        <div class="mb-3">
          <label for="" class="form-label">title</label>
          <input type="text" class="form-control" v-model="videoCreate.title" />
        </div>
        <div class="mb-3">
          <label for="" class="form-label">channel</label>
          <input
            type="text"
            class="form-control"
            v-model="videoCreate.channel"
          />
        </div>
        <div class="mb-3">
          <label for="" class="form-label">category</label>
          <input
            type="text"
            class="form-control"
            v-model="videoCreate.category"
          />
        </div>
        <div class="mb-3">
          <label for="" class="form-label">videoUrl</label>
          <input
            type="text"
            class="form-control"
            v-model="videoCreate.videoUrl"
          />
        </div>
        <div class="mb-3">
          <label for="" class="form-label">description</label>
          <textarea class="form-control" v-model="videoCreate.description" />
        </div>
        <button class="btn btn-primary">제출</button>
      </form>
    </div>
    <h4>비디오 삭제</h4>
    <button class="btn btn-outline-primary" @click="toggleVD">열기/닫기</button>
    <div v-show="videoDeleteForm">
      <form @submit.prevent="deleteVideo">
        <div class="mb-3">
          <label for="" class="form-label">videoId</label>
          <input type="text" class="form-control" v-model="videoIdDelete" />
        </div>
        <button class="btn btn-danger">삭제</button>
      </form>
    </div>
    <h4>공지사항 등록</h4>
    <button class="btn btn-outline-primary" @click="toggleNC">열기/닫기</button>
    <div class="" v-show="noticeCreateForm">
      <form @submit.prevent="createNotice">
        <div class="mb-3">
          <label for="" class="form-label">title</label>
          <input
            type="text"
            class="form-control"
            v-model="noticeCreate.title"
          />
        </div>
        <div class="mb-3">
          <label for="" class="form-label">content</label>
          <textarea class="form-control" v-model="noticeCreate.content" />
        </div>
        <button class="btn btn-primary">제출</button>
      </form>
    </div>
    <h4>공지사항 삭제</h4>
    <button class="btn btn-outline-primary" @click="toggleND">열기/닫기</button>
    <div v-show="noticeDeleteForm">
      <form @submit.prevent="deleteNotice">
        <div class="mb-3">
          <label for="" class="form-label">articleId</label>
          <input type="text" class="form-control" v-model="noticeIdDelete" />
        </div>
        <button class="btn btn-danger">삭제</button>
      </form>
    </div>
    <h4>헬스장 등록</h4>
    <button class="btn btn-outline-primary" @click="toggleGC">열기/닫기</button>
    <div class="" v-show="gymCreateForm">
      <form @submit.prevent="createGym">
        <div class="mb-3">
          <label for="" class="form-label">gymName</label>
          <input type="text" class="form-control" v-model="gymCreate.gymName" />
        </div>
        <div class="mb-3">
          <label for="" class="form-label">address</label>
          <input type="text" class="form-control" v-model="gymCreate.address" />
        </div>
        <div class="mb-3">
          <label for="" class="form-label">latitude</label>
          <input
            type="text"
            class="form-control"
            v-model="gymCreate.latitude"
          />
        </div>
        <div class="mb-3">
          <label for="" class="form-label">longitude</label>
          <input
            type="text"
            class="form-control"
            v-model="gymCreate.longitude"
          />
        </div>
        <button class="btn btn-primary">제출</button>
      </form>
    </div>
    <h4>헬스장 삭제</h4>
    <button class="btn btn-outline-primary" @click="toggleGD">열기/닫기</button>
    <div v-show="gymDeleteForm">
      <form @submit.prevent="deleteGym">
        <div class="mb-3">
          <label for="" class="form-label">gymId</label>
          <input type="text" class="form-control" v-model="gymIdDelete" />
        </div>
        <button class="btn btn-danger">삭제</button>
      </form>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
export default {
  name: "AdminView",
  data() {
    return {
      videoCreateForm: false,
      videoDeleteForm: false,
      noticeCreateForm: false,
      noticeDeleteForm: false,
      videoCreate: {
        category: "",
        channel: "",
        description: "",
        title: "",
        videoUrl: "",
      },
      videoIdDelete: "",
      noticeCreate: {
        title: "",
        content: "",
      },
      noticeIdDelete: "",
      gymCreateForm: false,
      gymDeleteForm: false,
      gymCreate: {
        gymName: "",
        address: "",
        latitude: "",
        longitude: "",
      },
      gymIdDelete: "",
    };
  },
  methods: {
    toggleVC() {
      this.videoCreateForm = !this.videoCreateForm;
    },
    toggleVD() {
      this.videoDeleteForm = !this.videoDeleteForm;
    },
    toggleNC() {
      this.noticeCreateForm = !this.noticeCreateForm;
    },
    toggleND() {
      this.noticeDeleteForm = !this.noticeDeleteForm;
    },
    toggleGC() {
      this.gymCreateForm = !this.gymCreateForm;
    },
    toggleGD() {
      this.gymDeleteForm = !this.gymDeleteForm;
    },
    createVideo() {
      this.$store.dispatch("createVideo", this.videoCreate);
      this.videoCreate = {
        category: "",
        channel: "",
        description: "",
        title: "",
        videoUrl: "",
      };
    },
    deleteVideo() {
      this.$store.dispatch("deleteVideo", this.videoIdDelete);
      this.videoIdDelete = "";
    },
    createNotice() {
      this.$store.dispatch("createNotice", this.noticeCreate);
      this.noticeCreate = {
        title: "",
        content: "",
      };
    },
    deleteNotice() {
      this.$store.dispatch("deleteArticle", this.noticeIdDelete);
      this.noticeIdDelete = "";
      // this.$router.push("/article/notice/main");
    },
    createGym() {
      this.$store.dispatch("createGym", this.gymCreate);
      this.createGym = {
        gymName: "",
        address: "",
        latitude: "",
        longitude: "",
      };
    },
    deleteGym() {
      this.$store.dispatch("deleteGym", this.gymIdDelete);
    },
  },
  computed: {
    ...mapState(["users"]),
    ...mapGetters(["loginUser"]),
  },
  created() {
    if (!this.loginUser.rank || this.loginUser.rank < 1) {
      this.$router.push("/users/login");
    }
  },
};
</script>

<style></style>
