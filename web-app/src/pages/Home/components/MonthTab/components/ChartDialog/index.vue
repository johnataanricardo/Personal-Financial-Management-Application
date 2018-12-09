<template>
  <div id="chartDialog">
    <v-dialog class="dialog" v-model="showDialog" ref="chartDialog" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Fluxo de Caixa</span>
        </v-card-title>
        <ChartBar ref="chart" :label="label" :labels="labels"/>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="teal" flat @click.native="showDialog = false">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import ChartBar from '@/components/Charts/ChartBar'

export default {
  name: 'ChartDialog',
  components: { ChartBar },  
  data() {
    return {
      showDialog: false,
      label: null,
      labels: null,
    }
  },
  mounted() {    
    this.fillData([])
  },
  methods: {
    fillData(data) {
      this.label = 'Fluxo de Caixa'
      this.labels = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']
      this.$refs.chart.fillData(data)
    }
  }
}
</script>

<style scoped>

  #chartDialog {
    padding: 5%;
  }

</style>