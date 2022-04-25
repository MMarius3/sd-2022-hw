<template>
  <div>
    <v-container class="justify-center my-10">
      <v-row justify="center">
        <v-expand-transition>
          <v-card width="50%">
            <v-card-title> Sign Up</v-card-title>
            <div class="px-5">
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                        v-model="signUpForm.firstName"
                        color="primary"
                        label="First Name"
                        variant="underlined"
                        counter="60"
                        maxlength="60"
                        :rules="rules.firstNameRules"
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field
                        v-model="signUpForm.lastName"
                        color="primary"
                        label="Last Name"
                        variant="underlined"
                        counter="30"
                        maxlength="30"
                        :rules="rules.lastNameRules"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                        v-model="signUpForm.email"
                        color="primary"
                        label="e-mail address"
                        variant="underlined"
                        type="email"
                        counter="60"
                        maxlength="60"
                        :rules="rules.emailRules"
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field
                        v-model="signUpForm.repeatingEmail"
                        color="primary"
                        label="repeat e-mail address"
                        type="email"
                        variant="underlined"
                        counter="60"
                        maxlength="60"
                        :rules="rules.emailRules"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                        v-model="signUpForm.username"
                        color="primary"
                        label="username"
                        variant="underlined"
                        counter="16"
                        maxlength="16"
                        :rules="rules.usernameRules"
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field
                        v-model="signUpForm.password"
                        color="primary"
                        label="password"
                        type="password"
                        variant="underlined"
                        counter="32"
                        maxlength="32"
                        :rules="rules.passwordRules"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
              <v-container>
                <v-row justify="right">
                  <v-col>
                    <v-btn :disabled="!signInOk"
                           class="ma-2"
                           color="primary"
                           @click="signUp"
                    >
                      Sign Up
                    </v-btn>
                    <v-btn class="ma-2"
                           @click="close"
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
      <v-snackbar
          v-model="errorEncountered"
          color="error"
          timeout="10000"
      >
        Error: {{ errorMessage }}
      </v-snackbar>
    </v-container>
  </div>
</template>

<script>
import HTTP from "../service/HTTP";

export default {
  name: "SignUp",

  data() {
    return {
      signUpForm: {
        firstName: "",
        lastName: "",
        email: "",
        repeatingEmail: "",
        username: "",
        password: "",
      },
      rules: {
        firstNameRules: [v => v.length >= 3 && v.length <= 60 || 'First name should be between 3 and 60 characters'],
        lastNameRules: [v => v.length >= 3 && v.length <= 30 || 'First name should be between 3 and 30 characters'],
        usernameRules: [v => v.length >= 4 && v.length <= 16 || 'First name should be between 4 and 16 characters'],
        emailRules: [v => v.length >= 5 && v.length <= 64 || 'Email should be between 5 and 64 characters'],
        passwordRules: [v => v.length >= 8 && v.length <= 32 || 'Password should be between 8 and 32 characters'],
      },
      errorEncountered: false,
      errorMessage: "",
      err: {}
    }
  },

  computed: {

    emailMatching() {
      return this.signUpForm.email !== "" && this.signUpForm.email === this.signUpForm.repeatingEmail;
    },

    signInOk() {
      return this.emailMatching && this.signUpForm.firstName !== "" && this.signUpForm.lastName !== "" &&
          this.signUpForm.password !== "" && this.signUpForm.username !== "";
    },

    signUpFormBody() {
      return {
        firstName: this.signUpForm.firstName,
        lastName: this.signUpForm.lastName,
        username: this.signUpForm.username,
        email: this.signUpForm.email,
        password: this.signUpForm.password
      }
    }

  },

  methods: {

    signUp() {
      HTTP.post("/auth/sign-up", this.signUpFormBody)
          .then(() => {
            this.$router.replace("/");
          })
          .catch(err => {
            this.errorEncountered = true;
            this.errorMessage = err.message;
          })
    },

    close() {
      this.$router.push("/");
    }

  }

}
</script>

<style scoped>

</style>
