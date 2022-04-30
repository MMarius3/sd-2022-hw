<template>
  <div>
    <v-container class="justify-center my-10">
      <v-row justify="center">
        <v-expand-transition>
          <v-card width="30%">
            <v-card-title> Sign In</v-card-title>
            <div class="px-5">
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                        color="primary"
                        label="Username"
                        variant="underlined"
                        v-model="submitForm.username"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field
                        color="primary"
                        label="Password"
                        variant="underlined"
                        type="password"
                        v-model="submitForm.password"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
              <v-container>
                <v-row justify="right">
                  <v-col>
                    <v-btn class="ma-2"
                           color="primary"
                           @click="submit"
                    >
                      Sign In
                    </v-btn>
                    <v-btn class="ma-2"
                           @click="cancel"
                    >
                      Cancel
                    </v-btn>
                  </v-col>
                </v-row>
              </v-container>
            </div>
          </v-card>
        </v-expand-transition>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import {mapActions} from "vuex";

export default {
  name: "SignIn",

  data() {
    return {
      submitForm: {
        username: "",
        password: "",
      }
    }
  },

  methods: {

    cancel() {
      this.$router.push("/");
    },

    submit() {
      this.signIn(this.submitForm)
          .then(() => {
            this.$router.replace("/");
          })
    },

    ...mapActions(['signIn'])

  },

  computed: {
    logInModel() {
      return {
        username: this.submitForm.username,
        password: this.submitForm.password,
      }
    }
  }

}
</script>

<style scoped>

</style>
