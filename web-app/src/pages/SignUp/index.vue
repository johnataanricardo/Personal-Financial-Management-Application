<template>
  <div id="signUp">
    <FormProfile ref='form' :account="false" @click="onSave" :emailDisabled="false" :cleanButton="true" :titleSaveButton="saveButton"/>
  </div>
</template>

<script>
import FormProfile from '@/components/FormProfile'
import router from '@/router'
import axios from 'axios'

const api = process.env.API_URL

export default {
  name: 'SignUp',
  components: { 
    FormProfile
  },  
  data() {
    return {
      saveButton: 'Inscrever-se'
    }
  },
  methods: {
    onSave: function (){
      const form = this.$refs.form
      const user = JSON.stringify(form.user)
      
      axios.post(api + '/user/sign-up/', user, { 
        headers: {        
          'Content-Type': 'application/json',
        },
      }).then(response => (
        localStorage.token = response.data.data.token == null ? '' : response.data.data.token,
        setTimeout(function() {
          if (localStorage.token) {
            router.push('/home')
          }
        }, 100)
      )).catch(function (error) {
          form.text = 'Email informado já está em uso.'
          form.snackbar = true
      })      
    },
  }
}
</script>