<template>
  <div id="login">
    <v-layout id="card" row wrap>
      <v-flex 
        xs10 offset-xs1 
        lg2 offset-lg5
        sm4 offset-sm4
        md4 offset-md4 >
        <v-card color="blue-grey lighten-5">
          <v-card-title primary-title class="center">
            <div>
              <h3 class="headline mb-0">Finança Pessoal</h3>
            </div>            
          </v-card-title>
          <v-form id="form" ref="form" v-model="valid" @keyup.native.enter="submit" lazy-validation>
            <v-text-field
              v-model="user.email"
              :rules="userRules.emailRules"
              color="red"
              label="E-mail"
              required>
            </v-text-field>
            <v-text-field
              v-model="user.password"
              :append-icon="invisibility ? 'visibility' : 'visibility_off'"
              @click:append="() => (invisibility = !invisibility)"
              :type="invisibility ? 'password' : 'text'"
              :rules="userRules.passwordRule"
              color="red"
              label="Senha"
              required>
            </v-text-field>
          </v-form>
          <v-card-actions class="center">
            <v-btn color="blue-grey lighten-5" @click="submit" class="black--text">Entrar</v-btn>
            <v-btn color="blue-grey lighten-5" :to="signUpAction" class="black--text">Inscrever-se</v-btn>
          </v-card-actions>
          <v-snackbar
            :timeout="6000"
            :bottom="true"
            v-model="snackbar">
              Usuário ou senha incorretos!
            <v-btn flat color="red" @click.native="snackbar = false">Close</v-btn>
          </v-snackbar>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      valid: true,
      invisibility: true,      
      snackbar: false,
      user: {
        email: '',
        password: '',
      },
      userRules: {
        emailRules: [
          v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail inválido!'
        ],
        passwordRule: [
          v => !!v || 'Senha é obrigatória!'
        ]
      },
      signUpAction: '/signup'
    }
  },
  methods: {
    submit () {
      if (this.$refs.form.validate()) {
        this.$auth.login({
          body: JSON.stringify(this.user),
          success: function (response) {
            localStorage.token = response.body.token
          },
          error: function () {
            delete localStorage.token
            this.snackbar = true
          },
          rememberMe: true,
          redirect: '/home'
        })
      }
    },
  }
}
</script>

<style scoped>
  #login { 
    height: 100%;
    display: flex;
    align-items: center;        
    background-color: rgb(246, 246, 246);  
    background-image: url('../../../static/icon.png');    
    background-position: 50% 85%;
    background-size: 15%;
  }

  #card {
    margin-top: -10%;
  }

  #form {
    padding: 0px 20px 0px 20px;
  }

  .center {
    justify-content: center;
  } 

  @media screen and (max-width: 600px)  {
    #login { 
      background-position: 50% 95%;
      background-size: 50%;
    }
  }

</style>


