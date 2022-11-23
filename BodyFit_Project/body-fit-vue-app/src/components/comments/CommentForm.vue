<template>
  <form @submit.prevent="createComment" class="comment-form">
    <h5 style="font-family: 'Hahmlet', serif;">댓글 쓰기</h5>
    <div class="d-flex justify-content-between">
      <div class="col-9">
        <input type="text" class="form-control" v-model="content" rows="2" />
      </div>

      <button class="btn btn-primary col-2 btn-color" style="font-family: 'Hahmlet', serif;">등록</button>
    </div>
  </form>
</template>

<script>
export default {
  name: "CommentForm",
  data() {
    return {
      content: "",
    };
  },
  methods: {
    createComment() {
      if (!this.content.trim()) {
        alert("내용을 입력해 주세요!");
        return;
      }
      this.$emit("create-comment", this.content);
      this.content = "";
    },
  },
};
</script>

<style scoped>
.comment-form {
  padding: 5px 10px;
}

.btn-color {
  --bs-btn-color: #fff;
  --bs-btn-bg: #00327e;
  --bs-btn-border-color: #00327e;
  --bs-btn-hover-color: #fff;
  --bs-btn-hover-bg: #0b5ed7;
  --bs-btn-hover-border-color: #00327e;
  --bs-btn-focus-shadow-rgb: 49, 132, 253;
  --bs-btn-active-color: #fff;
  --bs-btn-active-bg: #0a58ca;
  --bs-btn-active-border-color: #0a53be;
  --bs-btn-active-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
  --bs-btn-disabled-color: #fff;
  --bs-btn-disabled-bg: #0d6efd;
  --bs-btn-disabled-border-color: #0d6efd;
}
</style>
