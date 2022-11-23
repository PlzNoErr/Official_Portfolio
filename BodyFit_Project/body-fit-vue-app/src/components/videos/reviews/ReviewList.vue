<template>
  <div>
    <table class="table col-12" style="border-top: black solid 1px; margin-top: 10px;">
      <thead>
        <tr>
          <th scope="col" class="col-title">제목</th>
          <th scope="col">작성자</th>
          <th scope="col">작성 시간</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(review, index) in reviewList" :key="index">
          <td>
            <router-link
              :to="{
                name: 'ReviewDetail',
                params: { reviewId: review.reviewId },
              }"
              style="font-family: 'Gowun Dodum', sans-serif"
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
              style="font-family: 'Gowun Dodum', sans-serif"
              >{{ review.nickName }}</a
            >
          </td>
          <td style="font-family: 'Gowun Dodum', sans-serif">{{ review.createTimeStr }}</td>
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
  name: "ReviewList",
  components: { PaginationBox },
  created() {
    let id = this.$route.params.videoId;
    this.$store.dispatch("getReviewListByVideoId", { videoId: id, pageNum: 1 });
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
      this.$store.dispatch("getReviewListByVideoId", {
        videoId: this.$route.params.videoId,
        pageNum: num,
      });
    },
    clickedFollow(userSeqnum, nickNamenum, ClickedUserSeqnum) {
        let info = {userSeq:userSeqnum, nickName:nickNamenum, ClickedUserSeq: ClickedUserSeqnum}
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
.no-more-a-tag{
  text-decoration: none;
  color: #333;
}
.no-more-a-tag:hover{
  color: rgb(99, 105, 215);
}
</style>
