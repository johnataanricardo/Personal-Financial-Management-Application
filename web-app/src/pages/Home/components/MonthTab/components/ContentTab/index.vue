<template>
  <div>
    <v-container ref="contentTab" fluid grid-list-md>
      <v-data-iterator v-if="items.length > 0" :items="items" :rows-per-page-items="rowsPerPageItems"
        :pagination.sync="pagination" content-tag="v-layout" hide-actions row wrap>
        <v-flex slot="item" slot-scope="props" xs12 sm12 md12 lg12>
          <v-card v-if="props.item.registers.length !== 0 ">
            <v-card-title class="subheading font-weight-bold">{{ props.item.name }}</v-card-title>
            <v-divider></v-divider>
            <v-list dense>
              <v-list-tile v-for="register in props.item.registers" :key="register.id" xs12>
                <v-list-tile-content>
                  {{register.nomeCategoria ? 
                    register.nomeCategoria + ': ' + (register.descricao ? register.descricao : 'sem descrição') : 
                    'Sem Categoria' + ': ' + (register.descricao ? register.descricao : 'sem descrição')}}
                </v-list-tile-content>
                <v-list-tile-content class="align-end">R$: {{ register.valor }}</v-list-tile-content>
              </v-list-tile>              
            </v-list>
          </v-card>
        </v-flex>
        <v-toolbar slot="footer" class="mt-2" color="teal" dark dense flat>
          <v-toolbar-title class="subheading">{{ flow }}</v-toolbar-title>
        </v-toolbar>
      </v-data-iterator>
      <div v-else>
        <img class="image" src="../../../../../../../static/empty.png" >
      </div>
    </v-container>
  </div>
</template>

<script>
export default {
  name: 'ContentTab',
  data: () => ({
    rowsPerPageItems: [2],
    pagination: {
      rowsPerPage: 4
    },
    items: [],
    flow: ''
  })
}
</script>

<style scoped>

  .image {
    width: 20%;
    margin-top: 5%;
  }

  @media screen and (max-width: 800px)  {
    .image { 
      width: 65%;
      margin-top: 10%;
    }
  }

</style>
