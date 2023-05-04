import axios from "axios";

// axios 객체 생성
export const $ = axios.create({
  baseURL: "https://algopat.kr/test",
  headers: {
    "Content-Type": "application/json",
  },
});

$.interceptors.request.use((config) => {
  config.headers["authorization"] = localStorage.getItem("access-token");
  return config;
});

$.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      $.get("/token/accesstoken", {
        withCredentials: true,
      }).then((res) => {
        const accessToken = res.headers["authorization"];
        console.log(accessToken);
        console.log("토큰 세팅 시작");
        localStorage.setItem("access-token", accessToken);
      });
    }
    return Promise.reject(error);
  }
);
