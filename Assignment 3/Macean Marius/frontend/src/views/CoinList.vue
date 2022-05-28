<template>
  <v-card>
    <v-card-title>
      Coins
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <div v-if="this.$store.getters['auth/isAdmin']">
        <v-btn @click="addCoin">Add Coin</v-btn>
      </div>
    </v-card-title>
    <div v-if="this.$store.getters['auth/isAdmin']">
      <v-data-table
        :headers="headers"
        :items="coins"
        :search="search"
        @click:row="editCoin"
      ></v-data-table>
      <CoinDialog
        :opened="dialogVisible"
        :coin="selectedCoin"
        @refresh="refreshList"
      ></CoinDialog>
    </div>
    <div v-else>
      <v-btn @click="exportReport">Export</v-btn>
      <v-data-table
        :headers="headers"
        :items="coins"
        :search="search"
      ></v-data-table>
      <v-btn @click="connect">Connect</v-btn>
      <Notification
        :opened="notificationVisible"
        :notification="selectedNotification"
        @refresh="refreshList"
      ></Notification>
    </div>
  </v-card>
</template>

<script>
import api from "../api";
import CoinDialog from "../components/CoinDialog";
import Notification from "../components/Notification";

const reportType = {
  PDF: 0,
  CSV: 1,
};

export default {
  name: "CoinList",
  props: {
    reportType: Object,
  },
  components: { CoinDialog, Notification },
  data() {
    return {
      coins: [],
      search: "",
      headers: [
        {
          text: "Pair",
          align: "start",
          sortable: false,
          value: "pair",
        },
        { text: "Price", value: "price" },
        { text: "Top", value: "top" },
      ],
      dialogVisible: false,
      notificationVisible: false,
      selectedCoin: {},
      selectedNotification: {},
      messageContent: "",
      notifications: [],
    };
  },
  methods: {
    editCoin(coin) {
      this.selectedCoin = coin;
      this.dialogVisible = true;
    },
    addCoin() {
      this.dialogVisible = true;
    },
    showNotification() {
      this.selectedNotification = {
        message: "APPEARED",
        type: "Success",
      };
      this.notificationVisible = true;
      console.log("should appear: " + this.selectedNotification.message);
    },
    async refreshList() {
      console.log("refreshed");
      this.dialogVisible = false;
      this.notificationVisible = false;
      this.selectedCoin = {};
      this.coins = await api.coins.allCoins();
    },
    connect() {
      api.notification.connect();
    },
    async exportReport() {
      let fileToDownload = await api.coins.exportReports(reportType.CSV);
      this.download(fileToDownload);
    },
    download(data) {
      const blob = new Blob([data], { type: "text/csv" });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.setAttribute("hidden", "");
      a.setAttribute("href", url);
      a.setAttribute("download", "coins.csv");
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
