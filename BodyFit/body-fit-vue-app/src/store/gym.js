import $ from "@/util/http-common";
import router from "@/router";

export default {
  state: {
    gymList: [],
  },
  getters: {},
  mutations: {
    SEARCH_GYM(state, data) {
      state.gymList = data;
    },
  },
  actions: {
    createGym(context, data) {
      let API_URL = `/api/gyms/`;
      $.post(API_URL, data)
        .then((res) => {
          if (res.status === 201) {
            alert("헬스장이 등록되었습니다!");
          } else {
            alert("등록 실패");
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
    deleteGym(context, gymId) {
      let API_URL = `/api/gyms/${gymId}`;
      $.delete(API_URL)
        .then((res) => {
          if (res.status === 202) {
            alert("헬스장이 삭제되었습니다!");
          } else {
            alert("삭제 실패");
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
    searchGym(context, params) {
      let API_URL = `/api/gyms/search?latitude=${params.latitude}&longitude=${params.longitude}`;
      $.get(API_URL)
        .then((res) => {
          if (res.status === 200) {
            context.commit("SEARCH_GYM", res.data);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
