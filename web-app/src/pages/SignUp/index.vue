<template>
  <div id="signUp">
    <FormProfile ref='form' :account="false" @click="onSave" :emailDisabled="false" :cleanButton="true" :titleSaveButton="saveButton"/>
  </div>
</template>

<script>
import { post } from '@/services/user'
import FormProfile from '@/components/FormProfile'
import router from '@/router'

export default {
  name: 'SignUp',
  components: { 
    FormProfile
  },  
  data() {
    return {
      saveButton: 'Cadastrar-se'
    }
  },
  methods: {
    onSave: function (){
      const form = this.$refs.form
      const user = JSON.stringify(form.user)
      post(user).then(response => {
        if (response) {
          localStorage.token = response  
          router.push('/home')
        } else {
          form.text = 'Email informado já está em uso.'
          form.snackbar = true
        }
      })
    },
  }
}
</script>