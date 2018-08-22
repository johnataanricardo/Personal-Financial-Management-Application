<template>
  <div>
    <v-toolbar color="teal" tabs>
      <v-select :items="years" label="Ano" v-model="selected" solo></v-select>
      <v-tabs slot="extension" v-model="tabs" centered color="transparent"
        slider-color="white" dark>
        <v-tab v-for="item in itemsMenu" :key="item.month">{{item.description}}</v-tab>
      </v-tabs>
    </v-toolbar>
    <v-tabs-items v-model="tabs">
      <v-tab-item v-for="item in itemsMenu" :key="item.month">
        <ContentTab/>
      </v-tab-item>
    </v-tabs-items>
    <v-btn color="pink" v-model="fab" dark fab fixed bottom right>
      <v-icon>add</v-icon>
    </v-btn>
  </div>
</template>

<script>
import ContentTab from '../ContentTab'

export default {
  name: 'MonthTab', 
  components: { ContentTab },
  data () {
    return {
      tabs: null,
      fab: false,
      itemsMenu : [
        { month: 0, description: 'Janeiro' },
        { month: 1, description: 'Fevereiro' },
        { month: 2, description: 'Março' },
        { month: 3, description: 'Abril' },
        { month: 4, description: 'Maio' },
        { month: 5, description: 'Junho' },
        { month: 6, description: 'Julho' },
        { month: 7, description: 'Agosto' },
        { month: 8, description: 'Setembro' },
        { month: 9, description: 'Outubro' },
        { month: 10, description: 'Novembro' },
        { month: 11, description: 'Dezembro' },
      ],
      years: [2018, 2019, 2020],
      selected: 2018,
      text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'
    }
  },
  methods: {
  }
}
</script>

<style scoped>

</style>