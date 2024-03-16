<!-- PaginationView.vue -->
<template>
  <nav aria-label="Page navigation">
    <ul class="pagination">
      <li class="page-item" :class="{ disabled: currentPage === 1 }">
        <button class="btn btn-primary" @click="previousPage">Previous</button>&nbsp;
      </li>
      <li class="page-item" v-for="pageNumber in totalPages" :key="pageNumber" :class="{ active: pageNumber === currentPage }">
        <button class="btn btn-primary" @click="goToPage(pageNumber)">{{ pageNumber }}</button>&nbsp;
      </li>
      <li class="page-item" :class="{ disabled: currentPage === totalPages }">
        &nbsp;<button class="btn btn-primary" @click="nextPage">Next</button>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  props: {
    currentPage: {
      type: Number,
      required: true
    },
    totalPages: {
      type: Number,
      required: true
    }
  },
  methods: {
    goToPage(pageNumber) {
      this.$emit('update:currentPage', pageNumber);
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.$emit('update:currentPage', this.currentPage - 1);
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.$emit('update:currentPage', this.currentPage + 1);
      }
    }
  }
};
</script>