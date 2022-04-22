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
      @click:row="editItem">
        <template v-slot:item="row">
          <tr>
            <td>{{row.item.title}}</td>
            <td>{{row.item.author}}</td>
            <td>{{row.item.genre}}</td>
            <td>{{row.item.quantity}}</td>
            <td>{{row.item.price}}</td>
            <td>
              <v-btn  color="red"
                     depressed @click="deleteItem(row.item)">
                      Delete
              </v-btn>
            </td>
          </tr>
        </template>

    </v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
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
        {
          text: "Author",
          align: "start",
          sortable: false,
          value: "author",
        },
        {
          text: "Genre",
          align: "start",
          sortable: false,
          value: "genre",
        },
        {
          text: "Quantity",
          align: "start",
          sortable: false,
          value: "quantity",
        },
        {
          text: "Price",
          align: "start",
          sortable: false,
          value: "price",
        },
        {
          text: "",
          align: "start",
          sortable: false,
          value: "",
        },
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
      this.dialogVisible = true;
    },
    deleteItem(item) {
      api.items
          .delete({
            id: item.id,
          })
          .then(() => this.refreshList());
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
  },
  created() {
    this.refreshList();
  },

};
</script>

<style scoped></style>
