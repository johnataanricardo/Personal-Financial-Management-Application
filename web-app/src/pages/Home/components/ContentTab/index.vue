<template>
  <div>
    <v-container fluid grid-list-md>
      <v-data-iterator :items="items" :rows-per-page-items="rowsPerPageItems"
        :pagination.sync="pagination" content-tag="v-layout" hide-actions row wrap>
        <v-flex slot="item" slot-scope="props" xs12 sm12 md12 lg12>
          <v-card>
            <v-card-title class="subheading font-weight-bold">{{ props.item.name }}</v-card-title>
            <v-divider></v-divider>
            <v-list dense>
              <v-list-tile v-for="register in props.item.registers" :key="register.description">
                <v-list-tile-content>{{register.description}}</v-list-tile-content>
                <v-list-tile-content class="align-end">{{ register.value }}</v-list-tile-content>
              </v-list-tile>              
            </v-list>
          </v-card>
        </v-flex>
        <v-toolbar slot="footer" class="mt-2" color="teal" dark dense flat>
          <v-toolbar-title class="subheading">Fluxo de Caixa: R$: 1829,66</v-toolbar-title>
        </v-toolbar>
      </v-data-iterator>
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
    items: [
      {
        value: false,
        name: 'Entradas: R$: 3120,00',
        registers: [
          { description: 'Salário', value: 'R$: 2000,00' },
          { description: 'Rendimentos', value: 'R$: 120,00' },
          { description: 'Freelancer', value: 'R$: 1000,00' }
        ]
      },
      {
        value: false,
        name: 'Saídas: R$: 1290,34',
        registers: [
          { description: 'Inglês', value: 'R$: 300,00' },
          { description: 'Faculdade', value: 'R$: 650,00' },
          { description: 'Almoço', value: 'R$: 20,34' },
          { description: 'Tênis', value: 'R$: 200,00' },
          { description: 'Calça', value: 'R$: 120,00' },
        ]
      }    
    ]
  })
}
</script>