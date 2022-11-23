import $ from "@/util/http-common";
import router from "@/router";

export default {
  state: {
    searchedArticleList: [],
    pageNavigation: {},
    searchedParams: {},
    article: {},
    articleCommentList: [],
    commentPageNavigation: {},
  },
  getters: {},
  mutations: {
    SEARCH_ARTICLES(state, data) {
      state.searchedArticleList = data.list;
      state.pageNavigation = data.navigation;
    },
    SEARCHED_PARAMS(state, params) {
      state.searchedParams = params;
    },
    SET_ARTICLE(state, data) {
      state.article = data;
    },
    GET_COMMENT(state, data) {
      state.articleCommentList = data.list;
      state.commentPageNavigation = data.navigation;
    },
  },
  actions: {
    searchArticles(context, params) {
      let API_URL = `/api/articles/search`;

      $.get(API_URL, { params: params })
        .then((res) => {
          context.commit("SEARCHED_PARAMS", params);
          context.commit("SEARCH_ARTICLES", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    getArticleByArticleId(context, articleId) {
      let API_URL = `/api/articles/${articleId}`;

      $.get(API_URL)
        .then((res) => {
          context.commit("SET_ARTICLE", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    modifyArticle({ commit }, payload) {
      const URL = "/api/articles/";
      $({
        url: URL,
        method: "PUT",
        data: {
          title: payload.title,
          content: payload.content,
          articleId: payload.articleId,
        },
      })
        .then(() => {
          alert("게시글 수정 성공!");
          router.push(`/articles/${payload.articleId}`);
          commit;
          return;
        })
        .catch((err) => {
          if(err.response.status===401){
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },

    createArticle(context, payload) {
      const URL = "/api/articles/";
      $({
        url: URL,
        method: "POST",
        data: {
          title: payload.title,
          content: payload.content,
          userSeq: context.getters.loginUser.userSeq,
        },
      })
        .then(() => {
          alert("새글 작성 성공!");
          router.push(`/articles`);
          return;
        })
        .catch((err) => {
          if(err.response.status===401){
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
    createNotice(context, payload) {
      const URL = "/api/articles/";
      $({
        url: URL,
        method: "POST",
        data: {
          title: payload.title,
          content: payload.content,
          userSeq: context.getters.loginUser.userSeq,
          boardType: 1,
        },
      })
        .then(() => {
          alert("공지사항 작성 성공!");
          router.push(`/articles/notice/main`);
          return;
        })
        .catch((err) => {
          if(err.response.status===401){
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
    deleteArticle({ commit }, articleId) {
      if (confirm("정말로 삭제하시겠습니까?")) {
        let API_URL = `/api/articles/${articleId}`;

        $.delete(API_URL)
          .then(() => {
            alert("삭제되었습니다!");
            commit;
          })
          .catch((err) => {
            if(err.response.status===401){
              alert("로그인이 필요한 요청입니다!")
              router.push("/users/login");
            }
            console.log(err);
          });
      }
    },

    getArticleComments(context, params) {
      let API_URL = `/api/articles/${params.articleId}/comments/${params.pageNum}`;
      $.get(API_URL)
        .then((res) => {
          if (res.status === 200) {
            context.commit("GET_COMMENT", res.data);
          } else if (res.status === 204) {
            context.commit("GET_COMMENT", { list: [], navigation: {} });
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
    getRecentArticleComments(context, params) {
      let API_URL = `/api/articles/${params.articleId}/comments`;
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
    createArticleComment(context, data) {
      let API_URL = `/api/articles/${data.articleId}/comments`;
      $.post(API_URL, data)
        .then((res) => {
          if (res.status === 201) {
            context.dispatch("getRecentArticleComments", {
              articleId: data.articleId,
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
    deleteArticleComment(context, params) {
      if (!confirm("정말로 삭제하시겠습니까?")) {
        return;
      }
      let API_URL = `/api/articles/${params.articleId}/comments/${params.commentId}`;
      $.delete(API_URL)
        .then((res) => {
          if (res.status === 202) {
            context.dispatch("getRecentArticleComments", {
              articleId: params.articleId,
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
  },
};
