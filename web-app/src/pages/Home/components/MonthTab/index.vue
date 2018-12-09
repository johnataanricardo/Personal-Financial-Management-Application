<template>
  <div class="text-xs-center">
    <!-- MonthTabs -->
    <v-toolbar color="teal" tabs>
      <v-select :items="years" color="teal" label="Ano" v-model="year" solo @change="fillContentTab"></v-select>
      <v-tabs slot="extension" v-model="month" centered color="transparent"
        slider-color="white" dark show-arrows @change="fillContentTab">
        <v-tab v-for="item in itemsMenu" :key="item.month">{{item.description}}</v-tab>
      </v-tabs>
    </v-toolbar>
    <!-- ContentTab -->
    <v-tabs-items v-model="month">
      <v-tab-item v-for="item in itemsMenu" :key="item.month">
        <ContentTab ref="contentTab"/>
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
    <!-- SnackBar -->
    <v-snackbar
      :timeout="6000"
      :bottom="true"
      v-model="snackbar">
        {{ snackbarText }}        
      <v-btn flat color="teal" @click.native="snackbar = false">Fechar</v-btn>
    </v-snackbar>
  </div>
</template>

<script>
import { getChartByYear } from '@/services/chart'
import { getAllCategories } from '@/services/category'
import { getTransactionsByMonthAndYear } from '@/services/home'
import { post as saveTransaction } from '@/services/transaction'
import ContentTab from './components/ContentTab'
import RecordDialog from './components/RecordDialog/'
import GraphicDialog from './components/GraphicDialog'

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
      year: null,
      month: null,
      snackbar: false,
      snackbarText: '',
    }
  },
  mounted () {
    const date = new Date()
    this.month = date.getMonth()
    this.year = date.getFullYear()
    this.fillContentTab()
  },
  methods: {
    fillContentTab() {   
      const data = this
      const tab = data.$refs.contentTab
      if (tab) {
        const token = 'Bearer ' + localStorage.getItem('token')
        const contentTab = tab[data.month]
        contentTab.items = []
        contentTab.flow = ''
        getTransactionsByMonthAndYear(data.year, data.month).then(response => { data.prepareContentTab(response, contentTab) })
      }
    },
    prepareContentTab(data, contentTab) {      
      if (data != null) {
        const items = []
        const inputs = {
          name: 'Entradas R$: ' + data.totalInput,
          transactions: data.input
        }
        const ouputs = {
          name: 'Saídas R$:' + data.totalOutput,
          transactions: data.ouput
        }
        items.push(inputs)
        items.push(ouputs)
        contentTab.items = items
        contentTab.flow = 'Fluxo de Caixa R$: ' + data.cashFlow
      }      
    },
    openIncomeRecordDialog() {
      this.openDialog('Entradas')
    },
    openExpenseRecordDialog() {
      this.openDialog('Saídas')
    },
    openDialog(dialogTitle) {
      const data = this
      const recordDialog = data.$refs.recordDialog      
      recordDialog.title = dialogTitle
      recordDialog.saveRecord = data.saveRecordDialog
      recordDialog.transaction.typeTransaction = dialogTitle == 'Entradas' ? 'INPUT' : 'OUTPUT'
      recordDialog.transaction.year = data.year
      recordDialog.transaction.month = data.month
      getAllCategories().then(response => {  recordDialog.items = response, recordDialog.showDialog = true})
    },
    openGraphicDialog() {
      const data = this
      const graphicDialog = data.$refs.graphicDialog      
      getChartByYear(data.year).then(response => { graphicDialog.fillData(data.prepareChartData(response)), graphicDialog.showDialog = true })
    },
    prepareChartData(data) {
      const monthData = data.cashFlowMonthlyDto
      let cashFlowMonthly = []
      for (let i = 0; i < monthData.length; i++) {
        cashFlowMonthly.push(monthData[i].cashFlow)
      }
      return cashFlowMonthly
    },
    saveRecordDialog() {      
      const data = this
      const recordDialog = data.$refs.recordDialog
      recordDialog.showDialog = false
      saveTransaction(recordDialog.transaction).then(response => { 
        data.initSnackbar('Registro salvo com sucesso!'), data.fillContentTab() 
      }).catch(function (error) {
        this.initSnackbar('Problema ao salvar registro!')
      })
    },
    initSnackbar(text) {
      this.snackbarText = text
      this.snackbar = true
    },
  }
}
</script>

<style scoped>

  .v-speed-dial {
    position: fixed;
  }

  .v-btn--floating {
    position: relative;
  }

</style>