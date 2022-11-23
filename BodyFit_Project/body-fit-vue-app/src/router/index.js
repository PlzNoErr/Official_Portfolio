import Vue from "vue";
import VueRouter from "vue-router";

import GymView from "@/views/GymView.vue";
import HomeView from "@/views/HomeView.vue";
import UserView from "@/views/UserView.vue";
import VideoView from "@/views/VideoView.vue";

import ArticleCreate from "@/components/articles/ArticleCreate.vue";
import ArticleDetail from "@/components/articles/ArticleDetail.vue";
import ArticleUpdate from "@/components/articles/ArticleUpdate.vue";
import ArticleSearch from "@/components/articles/ArticleSearch.vue";
import ArticleNoticeView from "@/components/articles/ArticleNoticeView.vue";
import ArticleView from "@/views/ArticleView.vue";

import MyFollowView from "@/components/users/MyFollowView.vue";
import MyVideoView from "@/components/users/MyVideoView.vue";
import SingupView from "@/components/users/SingupView.vue";
import UserModify from "@/components/users/UserModify.vue";
import LoginView from "@/components/users/LoginView.vue";
import MyReviewView from "@/components/users/MyReviewView.vue";
import FollowReviewView from "@/components/users/FollowReviewView.vue";

import VideoDetail from "@/components/videos/VideoDetail.vue";
import VideoSearch from "@/components/videos/VideoSearch.vue";
import VideoAllBody from "@/components/videos/VideoAllBody.vue";
import VideoUpper from "@/components/videos/VideoUpper.vue";
import VideoLower from "@/components/videos/VideoLower.vue";
import VideoCore from "@/components/videos/VideoCore.vue";
import VideoETC from "@/components/videos/VideoETC.vue";

import ReviewCreate from "@/components/videos/reviews/ReviewCreate.vue";
import ReviewDetail from "@/components/videos/reviews/ReviewDetail.vue";
import ReviewUpdate from "@/components/videos/reviews/ReviewUpdate.vue";

import AdminView from "@/views/AdminView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "HomeView",
    component: HomeView,
  },
  {
    path: "/users",
    name: "UserView",
    component: UserView,
    children: [
      {
        path: "login",
        name: "LoginView",
        component: LoginView,
      },
      {
        path: "signup",
        name: "SingupView",
        component: SingupView,
      },
      {
        path: "modify",
        name: "UserModify",
        component: UserModify,
      },
      {
        path: "myfollow",
        name: "MyFollowView",
        component: MyFollowView,
      },
      {
        path: "myvideo",
        name: "MyVideoView",
        component: MyVideoView,
      },
      {
        path: "myreview",
        name: "MyReviewView",
        component: MyReviewView,
      },
      {
        path: "myreview/:userSeq",
        name: "FollowReviewView",
        component: FollowReviewView,
      },
    ],
  },
  {
    path: "/videos",
    component: VideoView,
    children: [
      {
        path: "",
        name: "VideoSearch",
        component: VideoSearch,
      },
      {
        path: "/category/allbody",
        name: "VideoAllBody",
        component: VideoAllBody,
      },
      {
        path: "/category/upper",
        name: "VideoUpper",
        component: VideoUpper,
      },
      {
        path: "/category/lower",
        name: "VideoLower",
        component: VideoLower,
      },
      {
        path: "/category/core",
        name: "VideoCore",
        component: VideoCore,
      },
      {
        path: "/category/etc",
        name: "VideoETC",
        component: VideoETC,
      },
      {
        path: ":videoId",
        name: "VideoDetail",
        component: VideoDetail,
      },
      {
        path: ":videoId/review",
        name: "ReviewCreate",
        component: ReviewCreate,
      },
      {
        path: "/review/:reviewId",
        name: "ReviewDetail",
        component: ReviewDetail,
      },
      {
        path: "/review/:reviewId/update",
        name: "ReviewUpdate",
        component: ReviewUpdate,
      },
    ],
  },
  {
    path: "/articles",
    component: ArticleView,
    children: [
      {
        path: "",
        name: "ArticleSearch",
        component: ArticleSearch,
      },
      {
        path: ":articleId",
        name: "ArticleDetail",
        component: ArticleDetail,
      },
      {
        path: ":articleId/update",
        name: "ArticleUpdate",
        component: ArticleUpdate,
      },
      {
        path: "create/new",
        name: "ArticleCreate",
        component: ArticleCreate,
      },
      {
        path: "notice/main",
        name: "ArticleNoticeView",
        component: ArticleNoticeView,
      },
    ],
  },
  {
    path: "/admin",
    name: "AdminView",
    component: AdminView,
  },
  {
    path: "/gym",
    name: "GymView",
    component: GymView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
