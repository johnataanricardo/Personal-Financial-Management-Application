<template>
  <div id="account">
    <Menu/>
    <FormProfile ref="form" :account="true" @click="onSave" :emailDisabled="true" :cleanButton="false" :titleSaveButton="saveButton"/>
  </div>
</template>

<script>
import Menu from '@/components/Menu'
import FormProfile from '@/components/FormProfile'
import axios from 'axios'

const api = process.env.API_URL

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
    const token = 'Bearer ' + localStorage.getItem('token')
    const user = this.$refs.form.user
    axios.get(api + '/user/', {
      headers: {        
        'Content-Type': 'application/json',
        'Authorization': token
      }
    }).then(response => (      
      user.nome = response.data.data.nome,
      user.email = response.data.data.email      
    )).catch(function (error) {
      console.log(error);
    })
  },
  methods: {
    onSave: function (){
      const token = 'Bearer ' + localStorage.getItem('token');
      const form = this.$refs.form
      const user = form.user
      axios.put(api + '/user/', JSON.stringify(user), {
        headers: {        
          'Content-Type': 'application/json',
          'Authorization': token
        }
      }).then(response => (      
        form.text = 'Salvo com sucesso!',
        form.snackbar = true    
      )).catch(function (error) {
        console.log(error);
      })
    }
  }
}
</script>