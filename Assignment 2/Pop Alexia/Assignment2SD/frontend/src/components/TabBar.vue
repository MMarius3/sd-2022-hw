<template>
  <v-tabs background-color="black accent-4 elevation" center-active dark>
    <v-tab>Books</v-tab>
    <v-tab>Users</v-tab>
    <v-tab>Reports</v-tab>

    <v-tab-item>
      <v-card flat>
        <BookList></BookList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <UsersList></UsersList>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <ReportList></ReportList>
      </v-card>
    </v-tab-item>
  </v-tabs>
</template>

<script>
import UsersList from "../components/UserList";
import BookList from "@/components/BookList";
import ReportList from "@/components/ReportList";

export default {
  name: "TabBar",
  components: { UsersList, BookList, ReportList },
};
</script>

<style scoped></style>
