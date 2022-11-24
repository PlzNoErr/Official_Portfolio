import $ from "@/util/http-common";
import router from "@/router";
import { Buffer } from "buffer";

export default {
  state: {
    LoginNickname: "",
    MyFollowingList: [],
    MyFollowedList: [],
    ClickedUser: "",
    ClickedUserNickName: "",
    isFollowed: false,
  },
  getters: {
    loginUser(state) {
      state.LoginNickname;
      let token = sessionStorage.getItem("access-token");
      if (token) {
        let base64Payload = token.split(".")[1]; //value 0 -> header, 1 -> payload, 2 -> VERIFY SIGNATURE
        let payload = Buffer.from(base64Payload, "base64");
        let result = JSON.parse(payload.toString());
        return result;
      } else {
        return {};
      }
    },
  },
  mutations: {
    LOGIN_CHECK(state, nickName) {
      state.LoginNickname = nickName;
    },
    LOGOUTREQUEST(state) {
      sessionStorage.removeItem("access-token");
      state.LoginNickname = "";
      router.push("/");
    },
    SESSION_CHECK(state) {
      let token = sessionStorage.getItem("access-token");
      if (token) {
        let base64Payload = token.split(".")[1]; //value 0 -> header, 1 -> payload, 2 -> VERIFY SIGNATURE
        let payload = Buffer.from(base64Payload, "base64");
        let result = JSON.parse(payload.toString());
        state.LoginNickname = result.nickName;
      }
      else { state.LoginNickname = "" }
    },
    SET_FOLLOWINGLIST(state, followlist) {
      state.MyFollowingList = followlist.followinglist;
      state.MyFollowedList = followlist.followedlist;
    },
    UPDATE_FOLLOW(state, info) {
      state.ClickedUser = info.userSeq;
      state.ClickedUserNickName = info.nickName;
    },
    SET_IS_FOLLOW(state, bool) {
      state.isFollowed = bool;
    },
  },
  actions: {
    loginCheck({ commit }) {
      let data = sessionStorage.getItem("nickName");
      if (data) {
        commit("LOGIN_CHECK", data);
      }
    },

    loginRequest({ commit }, payload) {
      const URL = `/api/users/login`;
      $({
        url: URL,
        method: "POST",
        data: {
          userId: payload.userId,
          password: payload.password,
        },
      })
        .then((res) => {
          console.log(payload);
          if (res.data.userId) {
            sessionStorage.setItem("access-token", res.data["access-token"]);
            if (!payload.modify) {
              alert("로그인 성공!");
            }
            commit("LOGIN_CHECK", res.data.nickName);
            $.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
            router.push("/");
            return;
          }
          alert("아이디와 비밀번호를 확인해 주세요!");
        })
        .catch((err) => console.log(err));
    },

    signupRegist({ commit }, payload) {
      const URL = `/api/users`;
      $({
        url: URL,
        method: "POST",
        data: {
          userId: payload.id,
          password: payload.password,
          email: payload.email,
          nickName: payload.nickName,
        },
      })
        .then(() => {
          alert("회원가입 성공!");
          router.push("/users/login");
          commit;
          return;
        })
        .catch((err) => console.log(err));
    },
    userModify(context, payload) {
      const URL = `/api/users`;
      $({
        url: URL,
        method: "PUT",
        data: {
          userId: payload.userId,
          password: payload.password,
          email: payload.email,
          nickName: payload.nickName,
        },
      })
        .then(() => {
          payload.modify = true;
          context.dispatch("loginRequest", payload);
          alert("정보수정 성공!");
          router.push("/");
          return;
        })
        .catch((err) => {
          if (err.response.status === 401) {
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },

    getMyFollowInfo(context) {
      let token = sessionStorage.getItem("access-token");
      let base64Payload = token.split(".")[1]; //value 0 -> header, 1 -> payload, 2 -> VERIFY SIGNATURE
      let payload = Buffer.from(base64Payload, "base64");
      let result = JSON.parse(payload.toString());


      let API_URL = `/api/users/followlist/${result.userSeq}`;

      $.get(API_URL)
        .then((res) => {
          context.commit("SET_FOLLOWINGLIST", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    getIsFollowed(context, params) {
      let API_URL = `/api/users/checkfollow`;
      $.post(API_URL, params)
        .then((res) => {
          if (res.status === 200) {
            context.commit("SET_IS_FOLLOW", true);
          } else {
            context.commit("SET_IS_FOLLOW", false);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    toggleIsFollowed(context, params) {
      let API_URL = `/api/users/follow`;
      $.post(API_URL, params)
        .then((res) => {
          if (res.status === 200) {
            context.dispatch("getIsFollowed", params);
            context.dispatch("getMyFollowInfo");
          }
        })
        .catch((err) => {
          if (err.response.status === 401) {
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
    toggleIsUnFollowed(context, params) {
      let API_URL = `/api/users/follow`;
      $({
        url: API_URL,
        method: "DELETE",
        data: {
          followedSeq: params.followedSeq,
          followingSeq: params.followingSeq,
        },
      })
        .then((res) => {
          if (res.status === 200) {
            context.dispatch("getIsFollowed", params);
            context.dispatch("getMyFollowInfo");
          }
        })
        .catch((err) => {
          if (err.response.status === 401) {
            alert("로그인이 필요한 요청입니다!")
            router.push("/users/login");
          }
          console.log(err);
        });
    },
  },
};
