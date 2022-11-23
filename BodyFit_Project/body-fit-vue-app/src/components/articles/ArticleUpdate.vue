<template>
  <div class="container">
    <h3 class="text-center" style="font-family: 'Gowun Dodum', sans-serif;">게시글 수정하기</h3>
    <hr />
    <h5 style="font-family: 'Gowun Dodum', sans-serif;">제목</h5>
    <input type="text" class="form-control" v-model="article.title" />
    <h5 class="contentbox" style="font-family: 'Gowun Dodum', sans-serif;">내용</h5>
    <textarea
      type="textarea"
      class="form-control"
      style="height: 300px"
      v-model="article.content"
    />
    <div style="text-align: center; margin: 20px 0px 0px 0px">
      <button
        type="submit"
        @click.prevent="modifyArticle"
        class="btn btn-outline-info"
        style="width: 120px"
      >
        수정완료
      </button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ArticleUpdate",

  methods: {
    modifyArticle() {
      this.$store.dispatch("modifyArticle", this.article);
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
        articleId: "",
      },
    };
  },

  created() {
    if (this.$route.name === "ArticleUpdate")
      this.$store.dispatch(
        "getArticleByArticleId",
        this.$route.params.articleId
      );
      this.article.title = this.articles.article.title;
      this.article.content = this.articles.article.content;
      this.article.articleId = this.articles.article.articleId;
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
