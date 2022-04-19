<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create item" : "Edit item" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="item.name" label="Name" />
          <v-text-field v-model="item.description" label="Description" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "BookList"
}
</script>

<style scoped>

</style>