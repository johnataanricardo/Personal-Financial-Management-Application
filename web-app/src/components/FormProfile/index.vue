<template>
  <div class="form">    
    <v-layout row wrap>
      <v-flex>
        <h3 class="headline mb-0">{{ title }}</h3>
      </v-flex>
    </v-layout>
    <v-form ref="form" v-model="valid" @keyup.native.enter="submit" lazy-validation>
      <v-layout>
        <v-flex xs6 class="text-field-right">
          <v-text-field  v-model="user.firstName" :rules="textFieldRule" color="red" label="Nome" required ></v-text-field>
        </v-flex>
        <v-flex xs6 class="text-field-left">
          <v-text-field v-model="user.lastName" :rules="textFieldRule" color="red" label="Sobre Nome" required></v-text-field>
        </v-flex>
      </v-layout>
      <v-layout>
        <v-flex xs6 class="text-field-right">
          <v-text-field v-model="user.zip" :rules="textFieldRule" color="red" label="CEP" mask="#####-###" @change="fillAddress()" required></v-text-field>
        </v-flex>
        <v-flex xs6 class="text-field-left">
          <v-text-field v-model="user.state" :rules="textFieldRule" color="red" label="Estado" required></v-text-field>
        </v-flex>
      </v-layout>
      <v-text-field v-model="user.city" :rules="textFieldRule" color="red" label="Cidade" required></v-text-field>
      <v-text-field v-model="user.address" :rules="textFieldRule" color="red" label="Endereço" required></v-text-field>
      <v-text-field v-model="user.number" type="number" :rules="textFieldRule" color="red" label="Número" required></v-text-field>            
      <v-text-field v-model="user.email" :rules="emailRules" color="red" label="E-mail" required :disabled="emailDisabled"></v-text-field>    
      <v-text-field
        v-model="user.password"
        :append-icon="invisibility1 ? 'visibility' : 'visibility_off'"
        @click:append="() => (invisibility1 = !invisibility1)"
        :type="invisibility1 ? 'password' : 'text'"
        :rules="passwordRules" 
        color="red"
        label="Senha"
        required>
      </v-text-field>
      <v-text-field
        v-model="user.repeatPassword"
        :append-icon="invisibility2 ? 'visibility' : 'visibility_off'"
        @click:append="() => (invisibility2 = !invisibility2)"
        :type="invisibility2 ? 'password' : 'text'"
        :rules="textFieldRule" 
        color="red"
        label="Repita a Senha"
        required>
      </v-text-field>
      <v-btn :disabled="!valid" @click="submit">Salvar</v-btn>
      <v-btn @click="clear" v-if="cleanButton">Limpar</v-btn>
      <v-snackbar
        :timeout="6000"
        :bottom="true"
        v-model="snackbar">
          {{ text }}        
        <v-btn flat color="red" @click.native="snackbar = false">Close</v-btn>
      </v-snackbar>
    </v-form>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'FormProfile',
  props: ['title', 'emailDisabled', 'cleanButton'],
  data: () => ({
    valid: true,
    invisibility1: true,
    invisibility2: true,
    snackbar: false,
    text: '',
    user: {
      firstName: '',
      lastName: '',
      zip: '',
      state: '',
      city: '',
      address: '',
      number: '',
      email: '',
      password: '',
      repeatPassword: ''
    },
    emailRules: [
      v => !!v || 'Campo obrigatório!',
      v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail inválido'
    ],
    passwordRules: [
      v => !!v || 'Campo obrigatório!',
      v =>  /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/.test(v) || 'A senha precisa ter uma no mínimo um número, uma letra maíscula e uma mínuscula.'
    ],
    textFieldRule: [
      v => !!v || 'Campo obrigatório!'
    ],
  }),
  methods: {
    fillAddress() {            
      const data = this
      if (data.user.zip) {
        axios.get('https://api.postmon.com.br/v1/cep/' + data.user.zip).then((response) => {
          const address = response.data
          this.user.state = address.estado,
          this.user.city = address.cidade,
          this.user.address = address.logradouro
        }).catch(function (error) {
            data.user.zip = ''
            data.text = 'CEP inválido!'
            data.snackbar = true
        })
      }
    },
    submit () {
      if (this.$refs.form.validate()) {
        if (this.user.password == this.user.repeatPassword) {
          this.$emit('click')
        } else {
          this.text = 'As senhas não estão iguais.'
          this.snackbar = true
        }
      }
    },
    clear () {
      this.$refs.form.reset()
    }  
  }  
}
</script>

<style scoped>

  .title {
    padding: 1%;
  }

  .form {
    padding: 2% 30% 2% 30%;
  }

  .text-field-left {
    padding-left: 1%;
  }

  .text-field-right {
    padding-right: 1%;
  }

  @media screen and (max-width: 600px)  {

  .form {
    padding: 2% 20% 2% 20%;
  }

  }

</style>