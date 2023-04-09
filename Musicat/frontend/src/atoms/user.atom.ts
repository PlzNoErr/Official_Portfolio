import { $ } from "@/connect/axios/setting";
import { NavigateFunction } from "react-router-dom";
import { atom, Resetter } from "recoil";

export const userInfoState = atom({
  key: "userInfo",
  default: {
    userSeq: 0,
    userRole: "",
    userProfile: "",
    userNick: "",
  },
});

export function logoutUser(func: Resetter, navigate: NavigateFunction) {
  $.post("/logout", {});
  sessionStorage.clear();
  func();
  navigate("/");
}
