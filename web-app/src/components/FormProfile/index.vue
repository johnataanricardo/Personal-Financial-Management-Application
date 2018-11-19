<template>
  <div class="form">   
    <div v-if="!account">
      <div style="padding: 10px">
        <img src="static/icon.png" alt="BillBox" height="80">
      </div>
      <div>
        <h1>Seu Financeiro</h1>
      </div>              
    </div>    
    <v-layout row wrap>
      <v-flex>
        <h3 class="headline mb-0" v-if="account">Conta</h3>
        <h3 class="headline mb-0" v-else>Inscrever-se</h3>
      </v-flex>
    </v-layout>
    <v-form ref="form" v-model="valid" @keyup.native.enter="submit" lazy-validation>
      <v-text-field  v-model="user.nome" :rules="textFieldRule" color="red" label="Nome" required ></v-text-field>
      <v-text-field v-model="user.email" :rules="emailRules" color="red" label="E-mail" required :disabled="emailDisabled"></v-text-field>    
      <v-text-field
        v-model="user.senha"
        :append-icon="invisibility1 ? 'visibility' : 'visibility_off'"
        @click:append="() => (invisibility1 = !invisibility1)"
        :type="invisibility1 ? 'password' : 'text'"
        :rules="account ? simplePasswordRules : passwordRules"
        color="red"
        label="Senha"
        required>
      </v-text-field>
      <v-text-field
        v-model="senhaRepetida"
        :append-icon="invisibility2 ? 'visibility' : 'visibility_off'"
        @click:append="() => (invisibility2 = !invisibility2)"
        :type="invisibility2 ? 'password' : 'text'"
        :rules="account ? simplePasswordRules : passwordRules"
        color="red"
        label="Repita a Senha"
        required>
      </v-text-field>
      <v-btn class="bold-text" :disabled="!valid" @click="submit">{{titleSaveButton}}</v-btn>
      <v-btn class="bold-text" @click="clear" v-if="cleanButton">Limpar</v-btn>
      <div style="margin-top: 10px;">
        <span v-if="!account">Já possui cadastro ? <a class="button-text" @click="changeRoute('/')">Login</a></span>
      </div>
      <v-snackbar
        :timeout="6000"
        :bottom="true"
        v-model="snackbar">
          {{ text }}        
        <v-btn flat color="teal" @click.native="snackbar = false">Fechar</v-btn>
      </v-snackbar>
    </v-form>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'FormProfile',
  props: ['account', 'emailDisabled', 'cleanButton', 'titleSaveButton'],
  data: () => ({
    valid: true,
    invisibility1: true,
    invisibility2: true,
    snackbar: false,
    text: '',
    user: {
      nome: '',
      email: '',
      senha: '',
    },
    senhaRepetida: '',
    emailRules: [
      v => !!v || 'Campo obrigatório!',
      v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail inválido'
    ],
    passwordRules: [
      v => !!v || 'Campo obrigatório!',
      v =>  /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/.test(v) || 'A senha precisa ter uma no mínimo um número, uma letra maíscula e uma mínuscula.'
    ],
    simplePasswordRules:[
       v => {
        const pattern =  /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/
        if (v.length) {
          return pattern.test(v) || 'A senha precisa ter uma no mínimo um número, uma letra maíscula e uma mínuscula.'
        } else {
          return true;
        }
      }
    ],
    textFieldRule: [
      v => !!v || 'Campo obrigatório!'
    ],
  }),
  methods: {
    submit () {
      if (this.$refs.form.validate()) {
        if (this.user.senha == this.senhaRepetida) {
          this.$emit('click')
        } else {
          this.text = 'As senhas não estão iguais.'
          this.snackbar = true
        }
      }
    },
    clear () {
      this.$refs.form.reset()
    },  
    changeRoute (router) {
      this.$router.push(router)
    }
  }  
}
</script>

<style scoped>

  h1 {
    color: #009688;
    margin-bottom: 10px
  }

  .title {
    padding: 1%;
  }

  .form {
    padding: 5% 30% 5% 30%;
  }

  .text-field-left {
    padding-left: 1%;
  }

  .text-field-right {
    padding-right: 1%;
  }

  .button-text {    
    color: #009688;
    cursor: pointer;
  }

  .bold-text {
    font-weight: bold;
  }

  @media screen and (max-width: 600px)  {

    .form {
      padding: 10% 20% 10% 20%;
    }

  }

</style>