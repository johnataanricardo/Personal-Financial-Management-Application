<template>
  <div class="text-xs-center">
    <!-- MonthTabs -->
    <v-toolbar color="teal" tabs>
      <v-select :items="years" color="teal" label="Ano" v-model="selected" solo @change="fillContentTab"></v-select>
      <v-tabs slot="extension" v-model="tabs" centered color="transparent"
        slider-color="white" dark show-arrows @change="fillContentTab">
        <v-tab v-for="item in itemsMenu" :key="item.month">{{item.description}}</v-tab>
      </v-tabs>
    </v-toolbar>
    <!-- ContentTab -->
    <v-tabs-items v-model="tabs">
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
import axios from 'axios'
import ContentTab from './components/ContentTab'
import RecordDialog from './components/RecordDialog/'
import GraphicDialog from './components/GraphicDialog'

const api = process.env.API_URL

export default {
  name: 'MonthTab', 
  components: { ContentTab, RecordDialog, GraphicDialog },
  data () {
    return {
      direction: 'top',
      fab: false,      
      hover: true,            
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
      selected: null,
      snackbar: false,
      snackbarText: '',
    }
  },
  mounted () {
    const date = new Date()
    this.tabs = date.getMonth()
    this.selected = date.getFullYear()
    this.fillContentTab()
  },
  methods: {
    fillContentTab() {   
      const tab = this.$refs.contentTab
      if (tab) {
        const token = 'Bearer ' + localStorage.getItem('token')
        const contentTab = tab[this.tabs]
        contentTab.items = []
        contentTab.flow = ''
        axios.get(api + '/home/' + this.selected + '/' + this.tabs, {
          headers: {       
            'Content-Type': 'application/json',
            'Authorization': token
          }
        }).then(response =>( 
          this.prepareContentTab(response.data.data, contentTab)
        )).catch(function (error) {
          console.log(error)
        })
      }
    },
    prepareContentTab(data, contentTab) {      
      if (data != null) {
        const items = []
        const itemsEntrada = {
          name: 'Entradas R$: ' + data.totalEntrada,
          registers: data.entrada
        }
        const itemsSaida = {
          name: 'Saídas R$:' + data.totalSaida,
          registers: data.saida
        }
        items.push(itemsEntrada)
        items.push(itemsSaida)
        contentTab.items = items
        contentTab.flow = 'Fluxo de Caixa R$: ' + data.fluxoCaixa
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
      recordDialog.tipoDespesa = dialogTitle == 'Entradas' ? 'ENTRADA' : 'SAIDA'
      recordDialog.ano = data.selected
      recordDialog.mes = data.tabs
      const token = 'Bearer ' + localStorage.getItem('token')
      axios.get(api + '/categoria/', {
        headers: {       
          'Content-Type': 'application/json',
          'Authorization': token
        }
      }).then(response => (      
        recordDialog.items = response.data.data,
        recordDialog.showDialog = true
      )).catch(function (error) {
        console.log(error)
      })
    },
    openGraphicDialog() {
      const data = this
      const graphicDialog = this.$refs.graphicDialog      
      const token = 'Bearer ' + localStorage.getItem('token')
      axios.get(api + '/chart/' + this.selected, {
        headers: {       
          'Content-Type': 'application/json',
          'Authorization': token
        }
      }).then(response => (                    
        graphicDialog.fillData(this.prepareChartData(response.data.data)),
        graphicDialog.showDialog = true
      )).catch(function (error) {
        console.log(error)
      })
    },
    prepareChartData(data) {
      const monthData = data.fluxoDeCaixaMensalDtos
      let monthDataFinal = []
      for (let i = 0; i < monthData.length; i++) {
        monthDataFinal.push(monthData[i].fluxoDeCaixa)
      }
      return monthDataFinal
    },
    saveRecordDialog() {      
      const data = this
      const recordDialog = data.$refs.recordDialog
      recordDialog.showDialog = false
      const movimentacao = {
        valor: recordDialog.valor,
        idCategoria: recordDialog.idCategoria,
        descricao: recordDialog.descricao,
        tipoDespesa: recordDialog.tipoDespesa,
        ano: recordDialog.ano,
        mes: recordDialog.mes
      }
      const token = 'Bearer ' + localStorage.getItem('token')
      axios.post(api + '/movimentacoes/', movimentacao, {
        headers: {        
          'Content-Type': 'application/json',
          'Authorization': token
        }
      }).then(response => ( 
        this.initSnackbar('Registro salvo com sucesso!'),
        this.fillContentTab()
      )).catch(function (error) {
        console.log(error)
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
    position: absolute;
  }

  .v-btn--floating {
    position: relative;
  }

</style>