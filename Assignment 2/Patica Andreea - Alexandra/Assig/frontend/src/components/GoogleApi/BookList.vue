<template>
  <div class="book-list">
    <BookItem v-for="book in books" :key="book.id" :book="book" />
  </div>
</template>

<script>
import BookItem from "./BookItem";

export default {
  props: {
    books: {
      type: Array,
      required: true,
    },
  },
  components: {
    BookItem,
  },
};
</script>

<style></style>
