<template>
  <nav class="nav justify-content-center">
    <ul class="pagination page-nav">
      <li class="page-item" :class="{ disabled: nav.startRange }">
        <a
          class="page-link"
          href="#"
          @click.prevent="pageChange(1, nav.startRange)"
          >최신</a
        >
      </li>
      <li class="page-item" :class="{ disabled: nav.startRange }">
        <a
          class="page-link"
          href="#"
          @click.prevent="pageChange(nav.startPage - 1, nav.startRange)"
          >이전</a
        >
      </li>
      <li
        class="page-item"
        v-for="(page, index) in pages"
        :key="index"
        :class="{ active: page === nav.currentPage }"
      >
        <a class="page-link" href="#" @click.prevent="pageChange(page, false)">
          {{ page }}
        </a>
      </li>
      <li class="page-item" :class="{ disabled: nav.endRange }">
        <a
          class="page-link"
          href="#"
          @click.prevent="pageChange(nav.endPage + 1, nav.endRange)"
          >다음</a
        >
      </li>
      <li class="page-item" :class="{ disabled: nav.endRange }">
        <a
          class="page-link"
          href="#"
          @click.prevent="pageChange(nav.totalPageCount, nav.endRange)"
          >마지막</a
        >
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "PaginationBox",
  props: {
    nav: { type: Object },
  },
  computed: {
    pages() {
      let result = [];
      if (!this.nav.startPage) {
        return [1];
      }
      let start = this.nav.startPage;
      let end = this.nav.endPage;
      for (let i = start; i <= end; i++) {
        result.push(i);
      }

      return result;
    },
  },
  methods: {
    pageChange(num, range) {
      const cur = this.nav.currentPage;
      if (range) {
        return;
      }
      if (num == cur) {
        return;
      }
      this.$emit("page-change", num);
    },
  },
};
</script>

<style scoped>
.page-nav {
  --bs-pagination-padding-x: 0.75rem;
  --bs-pagination-padding-y: 0.375rem;
  --bs-pagination-font-size: 1rem;
  --bs-pagination-color: black;
  --bs-pagination-bg: #fff;
  --bs-pagination-border-width: 1px;
  --bs-pagination-border-color: #dee2e6;
  --bs-pagination-border-radius: 0.375rem;
  --bs-pagination-hover-color: rgba(88, 88, 88, 0.25);
  --bs-pagination-hover-bg: #d8d8d8;
  --bs-pagination-hover-border-color: #dee2e6;
  --bs-pagination-focus-color: rgba(88, 88, 88, 0.25);
  --bs-pagination-focus-bg: #e9ecef;
  --bs-pagination-focus-box-shadow: 0 0 0 0.25rem rgba(88, 88, 88, 0.25);
  --bs-pagination-active-color: #fff;
  --bs-pagination-active-bg: rgba(0, 0, 0, 0.5);
  --bs-pagination-active-border-color: rgba(0, 0, 0, 0.5);
  --bs-pagination-disabled-color: #6c757d;
  --bs-pagination-disabled-bg: #fff;
  --bs-pagination-disabled-border-color: #dee2e6;
}
</style>
