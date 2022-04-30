<template>
  <div>
    <div id="hero-bar">
      <div>
        <v-btn class="mx-2" color="primary"
               @click="$router.push({ name : 'customer' })"
        > Customer </v-btn>
      </div>
      <div>
        <v-btn class="mx-2" color="primary"
               @click="$router.push({ name : 'admin' })"
        > Admin </v-btn>
      </div>
      <div>
        <v-btn class="mx-2" color="primary"
               @click="$router.push({ name: 'signUp' })"
        > Sign-Up </v-btn>
      </div>
      <div>
        <v-btn class="mx-2" color="primary"
               @click="$router.push({ name : 'signIn' })"
        > Sign-In </v-btn>
      </div>
    </div>
    <div id="hero">
      <h1>
        The NFT Book Store
      </h1>
      <h2>
        By Tuddi
      </h2>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
  name: 'HomeView',


});
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

#hero {
  width: 100vw;
  height: 95vh;
  background: url("https://www.oecd-nea.org/upload/docs/image/jpeg/2020-11/book_stack.jpg");
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#hero-bar {
  width: 100vw;
  height: 5vh;
  display: flex;
  flex-direction: row;
  justify-content: right;
  align-items: center;
  padding-left: 2rem;
  padding-right: 2rem;
}

h1 {
  font-size: 5rem;
  font-weight: 700;
  color: aliceblue;
  display: block;
}

h2 {
  font-size: 3rem;
  font-weight: 700;
  color: aliceblue;
  display: block;
}

</style>
