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
    <!-- Transaction Dialog -->
    <TransactionDialog ref="transactionDialog"/>
    <!-- Chart Dialog -->
    <ChartDialog ref="chartDialog"/>
    <!-- Delete Transaction Dialog -->
    <v-dialog v-model="dialog" persistent max-width="290">
      <v-card>
        <v-card-title class="headline">Deletar registro?</v-card-title>
        <v-card-text>Tem certeza de que deseja deletar?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="teal darken-1" flat @click.native="closeRemoveTransactionDialog()">cancelar</v-btn>
          <v-btn color="teal darken-1" flat @click.native="confirmRemoveTransaction()">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <!-- Action Buttons -->
    <v-speed-dial v-model="fab" :bottom="bottom" :right="right" 
      :direction="direction" :open-on-hover="hover" transition="slide-y-reverse-transition">
      <v-btn slot="activator" v-model="fab" fab dark color="pink">
        <v-icon>add</v-icon>
        <v-icon>close</v-icon>
      </v-btn>
      <v-tooltip disabled left :value="true">
        <v-btn @click="openTransactionDialog(null, 'Entrada')" fab dark small color="green" slot="activator">
          <v-icon>attach_money</v-icon>
        </v-btn>
        <span>Entradas</span>
      </v-tooltip>
      <v-tooltip disabled left :value="true">
        <v-btn @click="openTransactionDialog(null, 'Saída')" fab dark small color="indigo" slot="activator">
          <v-icon>money_off</v-icon>
        </v-btn>
        <span>Saídas</span>
      </v-tooltip>
      <v-tooltip disabled left :value="true">
        <v-btn @click="openChartDialog" fab dark small color="red" slot="activator">
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
import { save as saveTransaction, remove as removeTransaction } from '@/services/transaction'
import ContentTab from './components/ContentTab'
import ChartDialog from './components/ChartDialog'
import TransactionDialog from './components/TransactionDialog/'

export default {
  name: 'MonthTab', 
  components: { ContentTab, TransactionDialog, ChartDialog },
  data () {
    return {
      direction: 'top',
      fab: false,      
      hover: false,            
      right: true,
      bottom: true,
      dialog: false,
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
      idTransaction: null
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
        contentTab.openTransactionDialog = this.openTransactionDialog
        contentTab.openRemoveTransactionDialog = this.openRemoveTransactionDialog
      }      
    },
    openTransactionDialog(item, dialogTitle) {
      const data = this
      const transactionDialog = data.$refs.transactionDialog
      transactionDialog.saveTransaction = data.saveTransaction
      getAllCategories().then(response => {  transactionDialog.items = response })
      if (item) {
        transactionDialog.title = 'Registrar ' + (item.typeTransaction == 'INPUT' ? 'Entrada' : 'Saída')
        transactionDialog.transaction = Object.assign({}, item)
      } else {
        transactionDialog.transaction.id = 0
        transactionDialog.title = 'Registrar ' + dialogTitle
        transactionDialog.transaction.typeTransaction = dialogTitle == 'Entrada' ? 'INPUT' : 'OUTPUT'  
        transactionDialog.transaction.year = data.year
        transactionDialog.transaction.month = data.month     
      }       
      transactionDialog.showDialog = true
    },
    openChartDialog() {
      const data = this
      const chartDialog = data.$refs.chartDialog      
      getChartByYear(data.year).then(response => { chartDialog.fillData(data.prepareChartData(response)), chartDialog.showDialog = true })
    },
    prepareChartData(data) {
      const monthData = data.cashFlowMonthlyDto
      let cashFlowMonthly = []
      for (let i = 0; i < monthData.length; i++) {
        cashFlowMonthly.push(monthData[i].cashFlow)
      }
      return cashFlowMonthly
    },
    saveTransaction() {      
      const data = this
      const transactionDialog = data.$refs.transactionDialog
      transactionDialog.showDialog = false
      saveTransaction(transactionDialog.transaction).then(response => {
        if (response) {
          data.initSnackbar('Registro salvo com sucesso!')
          data.fillContentTab()
        } else {
          data.initSnackbar('Problema ao salvar registro!')
        }
      })
    },
    openRemoveTransactionDialog(transaction) {
      this.dialog = true
      this.idTransaction = transaction.id
    },
    closeRemoveTransactionDialog() {
      this.dialog = false
      this.idTransaction = null
    },
    confirmRemoveTransaction() {
      const data = this
      removeTransaction(data.idTransaction).then(response => {
        if (response) {
          data.initSnackbar('Registro deletado com sucesso!')
          data.fillContentTab()
        } else {
          data.initSnackbar('Problema ao deletar!')
        }
        data.dialog = false
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