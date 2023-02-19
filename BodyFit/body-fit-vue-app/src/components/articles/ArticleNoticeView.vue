<template>
  <div class="container">
    <h3 class="text-center" style="font-family: 'Gowun Dodum', sans-serif;">공 지 사 항</h3>
    <hr />

    <table class="table table-striped" style="text-align: center;">
      <tr>
        <th>제 목</th>
        <th>작성자</th>
        <th>조회수</th>
        <th>작성시간</th>
      </tr>
      <tr v-for="(article, index) in articles.searchedArticleList" :key="index" style="font-family: 'Gowun Dodum', sans-serif;">
        <td style="height: 25px;"> 
          <router-link :to="`/articles/${article.articleId}`" class="no-more-a-tag" style="font-size: 16px ;">
            {{ article.title }}
          </router-link>
        </td>
        <td style="font-size: 16px ;">{{ article.nickName }}</td>
        <td style="font-size: 16px ;">{{ article.viewCount }}</td>
        <td style="font-size: 16px ;">{{ changeDate(article.updateTime) }}</td>
      </tr>
    </table><br>

    <pagination-box @page-change="pageChange" :nav="articles.pageNavigation" />
    <search-box
      :mod-list="searchMod"
      :orderList="searchOrder"
      @search-event="searchArticles"
    />
  </div>
</template>

<script>
import SearchBox from "../common/SearchBox.vue";
import PaginationBox from "../common/PaginationBox.vue";
import { mapState } from "vuex";
export default {
  name: "ArticleNoticeView",
  components: { SearchBox, PaginationBox },
  data() {
    return {
      searchMod: [
        {
          key: "title",
          word: "제목",
        },
        {
          key: "content",
          word: "내용",
        },
        {
          key: "userId",
          word: "작성자",
        },
      ],
      searchOrder: [
        {
          key: "updateTime",
          word: "최신 순",
        },
        {
          key: "viewCount",
          word: "조회수 순",
        },
      ],
    };
  },
  computed: {
    ...mapState(["articles"]),
  },
  methods: {
    searchArticles(params) {
      params.boardType = 1;
      this.$store.dispatch("searchArticles", params);
    },
    pageChange(num) {
      let params = this.articles.searchedParams;
      params.currentPage = num;
      this.$store.dispatch("searchArticles", params);
    },
    changeDate(num) {
      let time = new Date(num - 1000*60*60*9).toLocaleString();
      return time;
    },
  },

  created() {
    if (this.$route.name === "ArticleNoticeView")
      this.$store.dispatch("searchArticles", { boardType: 1 });
  },
};
</script>

<style scoped>
.container {
  padding-top: 30px;
  width: 1100px;
  margin: 0 auto;
  align-items: center;
}

.no-more-a-tag{
  text-decoration: none;
  color: #333;
}
.no-more-a-tag:hover{
  color: rgb(99, 105, 215);
}

ul,
li {
  list-style: none;
}

table {
  width: 1100px;
  border-collapse: collapse;
}

table th {
  padding: 14px 21px;
  background: #f5f3f3;
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

.subject {
  width: 45%;
  text-align: left;
}

.paging {
  padding: 20px 0;
  width: 800px;
}

.paging > ul {
  display: flex;
  justify-content: center;
}

.paging > ul > li {
  padding: 8px;
  background: #efefef;
  border: 1px solid #999;
  font-size: 14px;
  margin-left: 10px;
}

.paging > ul > li:hover {
  cursor: pointer;
  background: #333;
  color: #fff;
}

.active {
  background: #333 !important;
  color: #fff;
}
</style>
