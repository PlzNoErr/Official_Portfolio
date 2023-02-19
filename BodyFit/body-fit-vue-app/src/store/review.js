import $ from "@/util/http-common";
import router from "@/router";

export default {
  state: {
    reviewList: [],
    pageNavigation: {},
    review: {},
    reviewCommentList: [],
    commentPageNavigation: {},
  },
  getters: {},
  mutations: {
    GET_REVIEWLIST(state, data) {
      state.reviewList = data.list;
      state.pageNavigation = data.navigation;
    },
    SET_REVIEW(state, data) {
      state.review = data;
    },
    GET_COMMENT(state, data) {
      state.reviewCommentList = data.list;
      state.commentPageNavigation = data.navigation;
    },
  },
  actions: {
    getReviewListByVideoId(context, params) {
      let API_URL = `/api/reviews/video/${params.videoId}/${params.pageNum}`;
      $.get(API_URL)
        .then((res) => {
          if (res.status === 200) {
            context.commit("GET_REVIEWLIST", res.data);
          } else if (res.status === 204) {
            context.commit("GET_REVIEWLIST", { list: [], navigation: {} });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getReviewListByuserSeq(context, params) {
      let API_URL = `/api/reviews/myvideo/${params.userSeq}/${params.pageNum}`;
      $.get(API_URL)
        .then((res) => {
          if (res.status === 200) {
            context.commit("GET_REVIEWLIST", res.data);
          } else if (res.status === 204) {
            context.commit("GET_REVIEWLIST", { list: [], navigation: {} });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getReviewByReviewId(context, reviewId) {
      let API_URL = `/api/reviews/${reviewId}`;
      $.get(API_URL)
        .then((res) => {
          if (res.status === 200) {
            context.commit("SET_REVIEW", res.data);
            context.dispatch("getVideoByVideoId", res.data.videoId);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getReviewComments(context, params) {
      let API_URL = `/api/reviews/${params.reviewId}/comments/${params.pageNum}`;
      $.get(API_URL)
        .then((res) => {
          if (res.status === 200) {
            context.commit("GET_COMMENT", res.data);
          } else if (res.status === 204) {
            context.commit("GET_COMMENT", { list: [], navigation: {} });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getRecentReviewComments(context, params) {
      let API_URL = `/api/reviews/${params.reviewId}/comments`;
      $.get(API_URL)
        .then((res) => {
          if (res.status === 200) {
            context.commit("GET_COMMENT", res.data);
          } else if (res.status === 204) {
            context.commit("GET_COMMENT", { list: [], navigation: {} });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    createReviewComment(context, data) {
      let API_URL = `/api/reviews/${data.reviewId}/comments`;
      $.post(API_URL, data)
        .then((res) => {
          if (res.status === 201) {
            context.dispatch("getRecentReviewComments", {
              reviewId: data.reviewId,
              pageNum: 1,
            });
          }
        })
        .catch((err) => {
          if(err.response.status===401){
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
    deleteReviewComment(context, params) {
      if (!confirm("정말로 삭제하시겠습니까?")) {
        return;
      }
      let API_URL = `/api/reviews/${params.reviewId}/comments/${params.commentId}`;
      $.delete(API_URL)
        .then((res) => {
          if (res.status === 202) {
            context.dispatch("getRecentReviewComments", {
              reviewId: params.reviewId,
              pageNum: 1,
            });
          }
        })
        .catch((err) => {
          if(err.response.status===401){
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
    createReview(context, data) {
      let API_URL = `/api/reviews`;
      $.post(API_URL, data)
        .then((res) => {
          if (res.status === 201) {
            alert("리뷰가 작성되었습니다.");
            router.push({
              name: "VideoDetail",
              params: { videoId: data.videoId },
            });
          }
        })
        .catch((err) => {
          if(err.response.status===401){
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
    updateReview(context, data) {
      let API_URL = `/api/reviews`;
      $.put(API_URL, data)
        .then((res) => {
          if (res.status === 202) {
            alert("리뷰가 수정되었습니다.");
            router.push({
              name: "ReviewDetail",
              params: { reviewId: data.reviewId },
            });
          }
        })
        .catch((err) => {
          if(err.response.status===401){
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
    deleteReview(context, params) {
      if (!confirm("정말로 삭제하시겠습니까?")) {
        return;
      }
      let API_URL = `/api/reviews/${params.reviewId}`;
      $.delete(API_URL)
        .then((res) => {
          if (res.status === 202) {
            alert("리뷰가 삭제되었습니다.");
            router.push({
              name: "VideoDetail",
              params: { videoId: params.videoId },
            });
          }
        })
        .catch((err) => {
          if(err.response.status===401){
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
  },
};
