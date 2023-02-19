<template>
  <div class="container">
    <h3 class="text-center" style="font-family: 'Gowun Dodum', sans-serif">
      새로운 글 작성하기
    </h3>
    <hr />
    <h5>제목</h5>
    <input type="text" class="form-control" v-model="article.title" />
    <h5 class="contentbox">내용</h5>
    <textarea
      type="textarea"
      class="form-control"
      style="height: 300px"
      v-model="article.content"
    />
    <div style="text-align: center; margin: 20px 0px 0px 0px">
      <button
        type="submit"
        @click.prevent="createArticle"
        class="btn btn-outline-success"
        style="width: 120px"
      >
        새글작성
      </button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ArticleUpdate",

  methods: {
    createArticle() {
      this.$store.dispatch("createArticle", this.article);
    },
  },

  computed: {
    ...mapState(["articles"]),
  },

  data() {
    return {
      article: {
        title: "",
        content: "",
      },
    };
  },
};
</script>

<style scoped>
.contentbox {
  margin: 35px 0px 10px 0px;
}
.container {
  padding-top: 30px;
  width: 960px;
  margin: 0 auto;
  align-items: center;
}
</style>
