<template>
  <div id="signUp">
    <FormProfile ref='form' title="Inscrever-se" @click="onSave" :emailDisabled="false" :cleanButton="true" :titleSaveButton="saveButton"/>
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
      
      axios.post(api + '/user/new/' + user, { 
        headers: {        
          'Content-Type': 'application/json',
        }
      }).then(response => (
        localStorage.token = response.data.token == null ? '' : response.data.token
      )).catch(function (error) {
        console.log(error);
      })

      setTimeout(function() {
        if (localStorage.token) {
          router.push('/home')
        } else {
          form.text = 'Email informado já está em uso.'
          form.snackbar = true
        }
      }, 100);
    },
  }
}
</script>

<style scoped>

  @media screen and (max-width: 600px)  {

  }

</style>