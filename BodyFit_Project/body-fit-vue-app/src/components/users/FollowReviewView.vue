<template>
  <div class="container">
    <h3 class="text-center" style="font-family: 'Gowun Dodum', sans-serif;">"{{ users.ClickedUserNickName }}" 님이 작성한 리뷰</h3>
    <hr />
    <table class="table col-12" style="margin-top: 30px">
      <thead>
        <tr style="font-family: 'Gowun Batang'">
          <th scope="col" class="col-title">제목</th>
          <th scope="col">작성자</th>
          <th scope="col">작성 시간</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(review, index) in reviewList" :key="index" style="font-family: 'Gowun Dodum', sans-serif;">
          <td>
            <router-link
              :to="{
                name: 'ReviewDetail',
                params: { reviewId: review.reviewId },
              }"
              class="no-more-a-tag"
            >
              {{ review.title }}
            </router-link>
          </td>
          <td>
            <a
              class="a-for-follow"
              @click="clickedFollow(review.userSeq, review.nickName)"
              data-bs-toggle="modal"
              data-bs-target="#exampleModal"
            >
              {{ review.nickName }}</a
            >
          </td>
          <td>{{ review.createTimeStr }}</td>
        </tr>
      </tbody>
    </table>
    <pagination-box @page-change="pageChange" :nav="reviews.pageNavigation" />
  </div>
</template>

<script>
import PaginationBox from "@/components/common/PaginationBox.vue";
import { mapState, mapGetters } from "vuex";
export default {
  name: "FollowReviewView",
  components: { PaginationBox },
  created() {
    console.log(this.$route.params.userSeq);
    this.$store.dispatch("getReviewListByuserSeq", {
      userSeq: this.$route.params.userSeq,
      pageNum: 1,
    });
  },
  computed: {
    ...mapState(["reviews", "users"]),
    ...mapGetters(["loginUser"]),
    reviewList() {
      let list = this.reviews.reviewList.map((e) => {
        e.createTimeStr = new Date(
          e.createTime - 1000 * 60 * 60 * 9
        ).toLocaleString();
        return e;
      });

      return list;
    },
  },
  methods: {
    pageChange(num) {
      this.$store.dispatch("getReviewListByuserSeq", {
        userSeq: this.loginUser.userSeq,
        pageNum: num,
      });
    },
    clickedFollow(userSeqnum, nickNamenum) {
      let info = { userSeq: userSeqnum, nickName: nickNamenum };
      this.$store.commit("UPDATE_FOLLOW", info);
      let params = {
        followedSeq: this.users.ClickedUser,
        followingSeq: this.loginUser.userSeq,
      };
      this.$store.dispatch("getIsFollowed", params);
    },
  },
};
</script>

<style scoped>
.col-title {
  width: 600px;
}
.container {
  padding-top: 30px;
  text-align: center;
  width: 960px;
  margin: 0 auto;
  align-items: center;
}

.no-more-a-tag {
  text-decoration: none;
  color: #333;
}
.no-more-a-tag:hover {
  color: rgb(99, 105, 215);
}
</style>
