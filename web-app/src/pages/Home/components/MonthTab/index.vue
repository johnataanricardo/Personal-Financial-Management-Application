<template>
  <div class="text-xs-center">
    <!-- MonthTabs -->
    <v-toolbar color="teal" tabs>
      <v-select :items="years" label="Ano" v-model="selected" solo></v-select>
      <v-tabs slot="extension" v-model="tabs" centered color="transparent"
        slider-color="white" dark>
        <v-tab v-for="item in itemsMenu" :key="item.month">{{item.description}}</v-tab>
      </v-tabs>
    </v-toolbar>
    <!-- ContentTab -->
    <v-tabs-items v-model="tabs">
      <v-tab-item v-for="item in itemsMenu" :key="item.month">
        <ContentTab/>
      </v-tab-item>
    </v-tabs-items>
    <!-- Record Dialog -->
    <RecordDialog ref="recordDialog"/>
    <!-- Graphic Dialog -->
    <GraphicDialog ref="graphicDialog"/>
    <!-- Action Buttons -->
    <v-speed-dial v-model="fab" :bottom="bottom" :right="right" 
      :direction="direction" :open-on-hover="hover" :transition="transition">
      <v-btn slot="activator" v-model="fab" fab dark color="pink">
        <v-icon>add</v-icon>
        <v-icon>close</v-icon>
      </v-btn>
      <v-tooltip disabled left :value="true">
        <v-btn @click="openIncomeRecordDialog" fab dark small color="green" slot="activator">
          <v-icon>attach_money</v-icon>
        </v-btn>
        <span>Entradas</span>
      </v-tooltip>
      <v-tooltip disabled left :value="true">
        <v-btn @click="openExpenseRecordDialog" fab dark small color="indigo" slot="activator">
          <v-icon>money_off</v-icon>
        </v-btn>
        <span>Saídas</span>
      </v-tooltip>
      <v-tooltip disabled left :value="true">
        <v-btn @click="openGraphicDialog" fab dark small color="red" slot="activator">
          <v-icon>bar_chart</v-icon>
        </v-btn>
        <span>Fluxo de Caixa</span>
      </v-tooltip>
    </v-speed-dial>
  </div>
</template>

<script>
import ContentTab from '../ContentTab'
import RecordDialog from '../RecordDialog'
import GraphicDialog from '../GraphicDialog'

export default {
  name: 'MonthTab', 
  components: { ContentTab, RecordDialog, GraphicDialog },
  data () {
    return {
      direction: 'top',
      fab: false,      
      hover: false,            
      right: true,
      bottom: true,
      transition: 'slide-y-reverse-transition',
      tabs: null,
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
    }
  },
  methods: {
    openIncomeRecordDialog() {

      const data = this
      const recordDialog = this.$refs.recordDialog
      recordDialog.showDialog = true
      recordDialog.title = "Entradas"
      recordDialog.saveRecord = data.saveRecordDialog

    },
    openExpenseRecordDialog() {
      
      const data = this
      const recordDialog = this.$refs.recordDialog
      recordDialog.showDialog = true
      recordDialog.title = "Saídas"
      recordDialog.saveRecord = data.saveRecordDialog

    },
    openGraphicDialog() {

      const data = this
      const graphicDialog = this.$refs.graphicDialog
      graphicDialog.showDialog = true

    },
    saveRecordDialog() {      
            
      const data = this
      const recordDialog = this.$refs.recordDialog
      recordDialog.showDialog = false
      
    }
  }
}
</script>

<style scoped>

  .v-speed-dial {
    position: absolute;
  }

  .v-btn--floating {
    position: relative;
  }

</style>