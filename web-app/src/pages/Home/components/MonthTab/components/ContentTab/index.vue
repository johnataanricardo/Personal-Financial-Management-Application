<template>
  <div>
    <v-container ref="contentTab" fluid grid-list-md>
      <v-data-iterator v-if="items.length > 0" :items="items" :rows-per-page-items="rowsPerPageItems"
        :pagination.sync="pagination" content-tag="v-layout" hide-actions row wrap>
        <v-flex slot="item" slot-scope="props" xs12 sm12 md12 lg12>
          <v-card>
            <v-card-title class="subheading font-weight-bold">{{ props.item.name }}</v-card-title>
            <v-divider></v-divider>
            <v-list dense>
              <v-list-tile v-for="register in props.item.registers" :key="register.id">
                <v-list-tile-content>{{register.nomeCategoria}}</v-list-tile-content>
                <v-list-tile-content>{{register.descricao}}</v-list-tile-content>
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
        Sem registros
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
    flow: '' //Fluxo de Caixa: R$: 1829,66
  })
}
</script>