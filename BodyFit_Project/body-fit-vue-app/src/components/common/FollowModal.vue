<template>
  <div
    class="modal fade"
    id="exampleModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-xl" id="MO">
      <div class="modal-content">
        <div class="modal-header">
          <div style="margin-top: 2px;">"{{ users.ClickedUserNickName }}" 님</div>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body" v-show="checkPossibleFollow" style="text-align: center;">
        <button
          class="btn btn-outline-danger"
          v-if="users.isFollowed"
          @click="doUnfollow"
        >
          언팔로우
        </button>
          <button
            class="btn btn-outline-primary"
            v-else
            @click="doFollow"
          >
            팔로우
          </button>
        </div>
        <div class="modal-footer" style="text-align: center;">
          <div style="margin: 0 auto;">
          <button @click="callReview" class="btn btn-outline-info" >작성한 리뷰보기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Modal } from "bootstrap";
import { mapState, mapGetters } from "vuex";

export default {
  name: "FollowModal",
  data() {
    return {
      title: "",
      content: "",
    };
  },
  methods: {
    doFollow() {
      let params = {
        followedSeq: this.users.ClickedUser,
        followingSeq: this.loginUser.userSeq,
      };
      this.$store.dispatch("toggleIsFollowed", params);
    },
    doUnfollow() {
      let params = {
        followedSeq: this.users.ClickedUser,
        followingSeq: this.loginUser.userSeq,
      };
      this.$store.dispatch("toggleIsUnFollowed", params);
    },
    callReview(){
      this.$router.push(`/users/myreview/${this.users.ClickedUser}`);
      Modal.getInstance(document.querySelector("#exampleModal")).hide();
    }
  },
  computed: {
    ...mapState(["users"]),
    ...mapGetters(["loginUser"]),
    checkPossibleFollow(){
      if(!this.users.LoginNickname){
        return false;
      }
      if(this.users.LoginNickname===this.users.ClickedUserNickName){
        return false;
      }
      return true;
    }
  },
};
</script>

<style>
#MO {
  width: 200px;
  height: 300px;
  position: relative;
  top: 200px;
}
</style>
