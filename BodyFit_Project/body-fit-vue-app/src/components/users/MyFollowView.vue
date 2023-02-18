<template>
  <div class="container">
    <h3 class="text-center" style="font-family: 'Gowun Dodum', sans-serif;">My 팔로우 관리</h3>
    <hr />
    <div style="display: flex; justify-content: space-between; flex-wrap: warp">
      <div>
        <table class="table" style="text-align: center">
          <tr>
            <th style="width: 300px">내가 팔로우한 사람들</th>
            <th style="width: 80px">{{ users.MyFollowingList.length }}명</th>
          </tr>
          <tr v-for="(following, index) in users.MyFollowingList" :key="index">
            <td colspan="2">
              <a
                class="a-for-follow"
                @click="clickedFollow(following.userSeq, following.nickName)"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal"
              >
                {{ following.nickName }}</a
              >
            </td>
          </tr>
        </table>
      </div>
      <div>
        <table class="table" style="text-align: center">
          <tr>
            <th style="width: 300px">나를 팔로우한 사람들</th>
            <th style="width: 80px">{{ users.MyFollowedList.length }}명</th>
          </tr>
          <tr v-for="(following, index) in users.MyFollowedList" :key="index">
            <td colspan="2">
              <a
                class="a-for-follow"
                @click="clickedFollow(following.userSeq, following.nickName)"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal"
              >
                {{ following.nickName }}</a
              >
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
  name: "MyFollowView",
  methods: {
    clickedFollow(userSeqnum, nickNamenum, ClickedUserSeqnum) {
      let info = {
        userSeq: userSeqnum,
        nickName: nickNamenum,
        ClickedUserSeq: ClickedUserSeqnum,
      };
      this.$store.commit("UPDATE_FOLLOW", info);
      let params = {
        followedSeq: this.users.ClickedUser,
        followingSeq: this.loginUser.userSeq,
      };
      this.$store.dispatch("getIsFollowed", params);
    },
  },
  computed: {
    ...mapState(["users"]),
    ...mapGetters(["loginUser"]),
  },
  created() {
    if (this.$route.name === "MyFollowView") {
      this.$store.dispatch("getMyFollowInfo");
    }
  },
};
</script>

<style scoped>
.container {
  padding-top: 30px;
  text-align: center;
  width: 900px;
  margin: 0 auto;
  align-items: center;
}

table th {
  padding: 14px 21px;
  background: rgba(255, 255, 255, 0.4) ;
  color: #666;
  font-size: 14px;
  border-top: 2px solid #333;
  border-bottom: 2px solid #ddd;
}

table td {
  padding: 14px 21px;
  color: #666;
  font-size: 14px;
  border-bottom: 1px solid #ddd;
  text-align: center;
}

table tr:hover {
  background: rgba(168, 164, 164, 0.25) ;
}
</style>
