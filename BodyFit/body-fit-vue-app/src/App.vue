<template>
  <div id="APP">
    <common-header></common-header>
    <router-view />
    <common-footer></common-footer>
    <follow-modal></follow-modal>
  </div>
</template>

<script>
import CommonFooter from "./components/common/CommonFooter.vue";
import CommonHeader from "./components/common/CommonHeader.vue";
import FollowModal from "./components/common/FollowModal.vue";

export default {
  components: { CommonFooter, CommonHeader, FollowModal },
  name: "APP",
};
</script>

<style>
.a-for-follow {
  cursor: pointer;
  text-decoration: none;
  color: black;
}
#APP {
  /* background-color: rgba(255, 255, 255, 0.5); */
  background-image: url("./static/img/athlete.jpg");
  /* background-position: center; */
  /* background-size: cover; */
  min-height: 1000px;
  min-width: 1300px;
}
</style>
