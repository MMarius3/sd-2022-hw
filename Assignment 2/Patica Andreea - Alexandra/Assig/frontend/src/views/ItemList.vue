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
      <v-btn @click="addItem">Add Item</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
      @close="dialogVisible = !dialogVisible"
    ></ItemDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";

export default {
  name: "ItemList",
  components: { ItemDialog },
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
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.selectedItem = {};
      this.dialogVisible = true;
    },
    async refreshList() {
      console.log("aaaaaaaaaaaaa");
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.getAllItemz();
      console.log("bbbbbbbbbbbbbbb");
      console.log(this.items);
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
