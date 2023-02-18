import Vue from "vue";
import Vuex from "vuex";
import Video from "./video";
import User from "./user";
import Review from "./review";
import Article from "./article";
import Gym from "./gym";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    videos: Video,
    users: User,
    reviews: Review,
    articles: Article,
    gyms: Gym,
  },
});
