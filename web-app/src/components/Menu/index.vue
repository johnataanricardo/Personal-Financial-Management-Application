<template>
  <div id="menu">
    <v-toolbar dense>
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-toolbar-title>{{ company }}</v-toolbar-title>
      <v-spacer></v-spacer>      
      <v-menu transition="slide-y-transition" bottom offset-y>
        <v-avatar open-on-hover slot="activator" size="35" color="grey lighten-4">
          <img src="../../../static/Perfil.jpg" >
        </v-avatar>
        <v-list>
          <v-list-tile>
            <v-list-tile-title @click="logOut">Sair</v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-menu>
    </v-toolbar>
    <v-navigation-drawer v-model="drawer" temporary absolute >
      <v-list class="pa-1">
        <v-list-tile avatar>
          <v-list-tile-avatar size="35">
            <img src="../../../static/Perfil.jpg" >
          </v-list-tile-avatar>
          <v-list-tile-content>
            <v-list-tile-title>{{ user.name }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
      <v-list class="pt-0" dense>
        <v-divider></v-divider>
        <v-list-tile v-for="item in itemsMenu" :to="item.page" :key="item.title" >
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import router from '@/router'
import axios from 'axios'

const api = process.env.API_URL

export default {
  name: 'Menu',
  data () {
    return {
      drawer: null,
      company: "Finança Pessoal",
      user :{
        name: ''
      },
      itemsMenu: [
        { title: 'Início', page: '/home', icon: 'dashboard' },
        { title: 'Conta', page: '/account', icon: 'account_box' }
      ]
    }
  },
  mounted () {
    const token = 'Bearer ' + localStorage.getItem('token');
    const fields = {fields: {'firstName': 1, 'lastName': 1, '_id': 0}}
    axios.get(api + '/user/' + JSON.stringify(fields), { 
      headers: {        
        'Content-Type': 'application/json',
        'Authorization': token
      }
    }).then(response => (      
      this.user.name = response.data.firstName + " " + response.data.lastName
    )).catch(function (error) {
      console.log(error);
    })
  },
  methods: {
    logOut () {    
      delete localStorage.token
      router.push('/')
    }
  }
}
</script>

<style scoped>

  @media screen and (max-width: 600px)  {

  }

</style>