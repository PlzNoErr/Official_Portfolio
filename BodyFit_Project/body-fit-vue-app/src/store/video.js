import $ from "@/util/http-common";

export default {
  state: {
    recommendVideoList: [],
    categoryVideoList: [],
    searchedVideoList: [],
    likedVideoList: [],
    pageNavigation: {},
    searchedParams: {},
    video: {},
    isLiked: false,
    categoryNow: "",
  },
  getters: {
    videoId(state) {
      return state.video.videoId;
    },
  },
  mutations: {
    GET_RECOMMEND_VIDEO_LIST(state, list) {
      state.recommendVideoList = list;
    },
    GET_CATEGORY_VIDEO_LIST(state, list) {
      state.categoryVideoList = list;
    },
    SEARCH_VIDEOS(state, data) {
      state.searchedVideoList = data.list;
      state.pageNavigation = data.navigation;
    },
    SEARCHED_PARAMS(state, params) {
      state.searchedParams = params;
    },
    SET_VIDEO(state, data) {
      state.video = data;
    },
    LIKED_VIDEOS(state, data) {
      state.likedVideoList = data.list;
      state.pageNavigation = data.navigation;
    },
    SET_IS_LIKED(state, bool) {
      state.isLiked = bool;
    },
    SET_CATEGORY(state, category) {
      state.categoryNow = category;
    },
  },
  actions: {
    getRecommendVideoList(context) {
      let API_URL = "";
      if (!context.state.user) {
        API_URL = `/api/videos/search?orderBy=view_count`;
      } else {
        API_URL = `/api/videos/${context.state.user.userSeq}/follow/1`;
      }
      $.get(API_URL)
        .then((res) => {
          context.commit("GET_RECOMMEND_VIDEO_LIST", res.data.list);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getCategoryVideoList(context, category) {
      let API_URL = `/api/videos/search?orderBy=view_count&category=${category}`;

      $.get(API_URL)
        .then((res) => {
          context.commit("GET_CATEGORY_VIDEO_LIST", res.data.list);
          context.commit("SET_CATEGORY", category);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    searchVideos(context, params) {
      let API_URL = `/api/videos/search`;

      $.get(API_URL, { params: params })
        .then((res) => {
          context.commit("SEARCHED_PARAMS", params);
          context.commit("SEARCH_VIDEOS", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getVideoByVideoId(context, videoId) {
      let API_URL = `/api/videos/${videoId}`;

      $.get(API_URL)
        .then((res) => {
          context.commit("SET_VIDEO", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    searchlikedVideos(context, num) {
      if (!num) {
        num = 1;
      }
      let API_URL = `/api/videos/like/${context.getters.loginUser.userSeq}/${num}`;
      $.get(API_URL)
        .then((res) => {
          context.commit("LIKED_VIDEOS", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getIsLiked(context, params) {
      let API_URL = `/api/videos/${params.videoId}/like/${params.userSeq}`;
      $.get(API_URL)
        .then((res) => {
          if (res.status === 200) {
            context.commit("SET_IS_LIKED", true);
          } else {
            context.commit("SET_IS_LIKED", false);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    toggleIsLiked(context, params) {
      let API_URL = `/api/videos/like`;
      $.post(API_URL, params)
        .then((res) => {
          if (res.status === 200) {
            context.dispatch("getIsLiked", params);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    createVideo(context, data) {
      let API_URL = `/api/videos`;
      $.post(API_URL, data)
        .then((res) => {
          if (res.status === 201) {
            alert("비디오 등록됨!");
          } else {
            alert("비디오 등록 실패!!");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteVideo(context, videoId) {
      if (!confirm(`정말로 삭제하시겠습니까? videoId: ${videoId}`)) {
        return;
      }
      let API_URL = `/api/videos/${videoId}`;
      $.delete(API_URL)
        .then((res) => {
          if (res.status === 202) {
            alert("비디오 삭제됨!");
          } else {
            alert("비디오 삭제 실패!!");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
