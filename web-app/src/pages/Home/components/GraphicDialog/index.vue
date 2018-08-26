<template>
  <div id="graphicDialog">
    <v-dialog class="dialog" v-model="showDialog" ref="graphicDialog" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Fluxo de Caixa</span>
        </v-card-title>
        <GraphicBar :label="label" :labels="labels" :data="data"/>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="teal" flat @click.native="showDialog = false">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import GraphicBar from '@/components/Graphics/GraphicBar'

const api = process.env.API_URL

export default {
  name: 'Graphic',
  components: { GraphicBar },  
  data() {
    return {
      showDialog: false,
      label: null,
      labels: null,
      data: null
    }
  },
  created() {    
    this.label = 'Fluxo de Caixa',
    this.labels = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
    this.data = [1829.66, 1829.66, 1829.66, 1829.66, 1829.66, 1829.66, 1829.66, 1829.66, 1829.66, 1829.66, 1829.66, 1829.66]
  }
}
</script>

<style scoped>

  #graphicDialog {
    padding: 5%;
  }

  @media screen and (max-width: 600px)  {

  }

</style>