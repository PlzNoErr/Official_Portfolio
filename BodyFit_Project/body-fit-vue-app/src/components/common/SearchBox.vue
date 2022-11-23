<template>
  <div>
    <form
      @submit.prevent="searchEvent"
      class="d-flex justify-content-center align-items-center px-2"
    >
      <div class="mx-1">
        <select class="form-select" id="search-mod" v-model="modding">
          <option
            v-for="(mod, index) in modList"
            :key="index"
            :value="mod.key"
            :selected="index === 0 ? true : false"
          >
            {{ mod.word }}
          </option>
        </select>
      </div>
      <div class="mx-1 col">
        <input
          class="form-control"
          type="text"
          id="search-keyword"
          placeholder="검색어를 입력하세요."
          v-model="searchKeyword"
        />
      </div>
      <div class="mx-1">
        <select class="form-select" id="search-order" v-model="orderBy">
          <option
            v-for="(order, index) in orderList"
            :key="index"
            :value="order.key"
            :selected="index === 0 ? true : false"
          >
            {{ order.word }}
          </option>
        </select>
      </div>
      <div class="mx-1">
        <button class="btn btn-primary btn-color">검색</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "SearchBox",
  data() {
    return {
      modding: "",
      searchKeyword: "",
      orderBy: "",
    };
  },
  props: {
    modList: { type: Array, required: true },
    orderList: { type: Array, required: true },
  },
  methods: {
    searchEvent() {
      let params = {
        mod: this.modding,
        keyword: this.searchKeyword,
        orderBy: this.orderBy,
        currentPage: 1,
      };
      this.$emit("search-event", params);
    },
  },
  created() {
    this.modding = this.modList[0].key;
    this.orderBy = this.orderList[0].key;
  },
};
</script>

<style scoped>
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
