<template>
  <v-card>
    <v-card-title>
      Items
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="sellItem"
    ></v-data-table>
    <SellDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></SellDialog>
  </v-card>
</template>

<script>
import api from "../api";
import SellDialog from "../components/SellDialog";

export default {
  name: "ItemListEmployee",
  components: { SellDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
        { text: "Description", value: "description" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    sellItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.getAllItemz();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
