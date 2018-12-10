<template>
  <div id="menu">
    <v-toolbar dense dark color="teal">
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-toolbar-title>{{ company }}</v-toolbar-title>
      <v-spacer></v-spacer>      
      <v-menu transition="slide-y-transition" bottom offset-y>
        <v-avatar open-on-hover slot="activator" size="35" color="grey lighten-4">
          <img src="../../../static/profile.jpg" >
        </v-avatar>
        <v-list>
          <v-list-tile>
            <v-list-tile-title @click="logOut" style="cursor: pointer;">Sair</v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-menu>
    </v-toolbar>
    <v-navigation-drawer v-model="drawer" temporary absolute >
      <v-list class="pa-1">
        <v-list-tile avatar>
          <v-list-tile-avatar size="35">
            <img src="../../../static/profile.jpg" >
          </v-list-tile-avatar>
          <v-list-tile-content>
            <v-list-tile-title>{{ user.name }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
      <v-list>
        <v-list-tile v-for="item in itemsMenu" :to="item.page" :key="item.title" >
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
        <v-list-group prepend-icon="description" no-action>
          <v-list-tile slot="activator">
            <v-list-tile-title>Cadastros</v-list-tile-title>
          </v-list-tile>
          <v-list-tile v-for="register in registers" :to="register.page" :key="register.title" >
            <v-list-tile-action>
              <v-icon v-text="register.icon"></v-icon>
            </v-list-tile-action>
            <v-list-tile-title v-text="register.title"></v-list-tile-title>
          </v-list-tile>
        </v-list-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import { getUserLogged } from '@/services/user'
import router from '@/router'

export default {
  name: 'Menu',
  data () {
    return {
      drawer: null,
      company: "Seu Financeiro",
      user :{
        name: ''
      },
      itemsMenu: [
        { title: 'Início', page: '/home', icon: 'dashboard' },
        { title: 'Conta', page: '/account', icon: 'account_box' }
      ],
      registers: [
        { title: 'Categorias', page:'/categories', icon: 'label'}
      ]
    }
  },
  mounted () {
    const data = this
    getUserLogged().then(response => { data.user.name = response.name })
  },
  methods: {
    logOut () {    
      delete localStorage.token
      router.push('/login')
    }
  }
}
</script>

<style scoped>

  .v-list__group:after, .v-list__group:before {
    height: 0px;
  }

</style>