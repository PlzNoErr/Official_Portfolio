<template>
  <div>
    <div class="table col-12" style="margin-top: 13px; font-family: 'Hahmlet', serif;">
      <div class="d-flex table-col table-title">
        <div class="col-title">댓글</div>
        <div class="col-nickname">작성자</div>
        <div class="col-time">작성 시간</div>
        <div class="col-btns"></div>
      </div>

      <div style="font-family: 'Hahmlet', serif;">
        <div v-if="comments.length < 1">
          아직 댓글이 없습니다. 댓글을 작성해보세요.
        </div>
        <comment-row
          v-for="(comment, index) in comments"
          :key="index"
          :comment="comment"
          @create-recomment="createReComment"
          @delete-comment="deleteComment"
        />
      </div>
    </div>
    <hr />
    <comment-form id="comment-from" @create-comment="createComment" />
    <pagination-box @page-change="pageChange" :nav="nav" class="my-3" />
  </div>
</template>

<script>
import PaginationBox from "@/components/common/PaginationBox.vue";
import CommentForm from "./CommentForm.vue";
import { mapState, mapGetters } from "vuex";
import CommentRow from "./CommentRow.vue";

export default {
  name: "CommentList",
  components: { PaginationBox, CommentForm, CommentRow },
  props: {
    commentList: { type: Array },
    nav: { type: Object },
  },
  data() {
    return {
      reCommentFormBooleans: [],
    };
  },
  created() {},
  computed: {
    ...mapState(["reviews", "articles", "users"]),
    ...mapGetters(["loginUser"]),
    comments() {
      let list = this.commentList.map((e) => {
        e.createTimeStr = new Date(
          e.createTime - 1000 * 60 * 60 * 9
        ).toLocaleString();
        e.formOpen = false;
        return e;
      });
      return list;
    },
  },
  methods: {
    pageChange(num) {
      this.$emit("page-change", num);
    },
    createComment(content) {
      const data = {
        content: content,
        userSeq: this.loginUser.userSeq,
      };
      this.$emit("create-comment", data);
    },
    createReComment(data) {
      this.$emit("create-recomment", data);
    },
    deleteComment(commentId) {
      this.$emit("delete-comment", commentId);
    },
  },
  watch: {
    // comments() {
    //   this.reCommentFormBooleans = new Array(this.comments.length).fill(false);
    // },
  },
};
</script>

<style scoped>
.table-title {
  font-size: 1.2rem;
  font-weight: bold;
  border-bottom: solid 1px rgb(138, 138, 138);
  background: rgba(0, 0, 0, 0.1);
}
.table-col {
  border-top: solid 1px rgb(221, 221, 221);
  padding: 5px;
}
.deleted {
  color: gray;
}
.col-title {
  width: 700px;
}
.col-nickname {
  width: 120px;
}
.col-time {
  width: 250px;
}
.col-btns {
  width: 140px;
}
</style>
