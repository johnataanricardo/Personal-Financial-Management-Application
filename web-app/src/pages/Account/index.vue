<template>
  <div id="account">
    <Menu/>
    <FormProfile ref="form" :account="true" @click="onSave" :emailDisabled="true" :cleanButton="false" :titleSaveButton="saveButton"/>
  </div>
</template>

<script>
import { getUserLogged, put } from '@/services/user'
import Menu from '@/components/Menu'
import FormProfile from '@/components/FormProfile'

export default {
  name: 'Account',
  components: { 
    Menu,
    FormProfile
  },  
  data() {
    return {
      saveButton: 'Salvar'
    }
  },
  mounted () {
    const user = this.$refs.form.user
    getUserLogged().then(response => { user.name = response.name, user.email = response.email })
  },
  methods: {
    onSave: function (){
      const form = this.$refs.form
      const user = JSON.stringify(form.user)
      put(user).then(response => 
        form.text = 'Salvo com sucesso!',
        form.snackbar = true  
      )
    }
  }
}
</script>