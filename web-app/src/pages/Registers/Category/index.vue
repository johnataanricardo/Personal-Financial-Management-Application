<template>
  <div id="category">
    <Menu/>  
    <!-- Table -->
    <div style="padding: 1%">
      <h2>Categorias</h2>
      <v-data-table :headers="headers" :items="categories" class="elevation-1"
        :rows-per-page-items="rowsPerPage" :rows-per-page-text="rowsPerPageText" :no-data-text="noDataText">
        <template slot="items" slot-scope="props">
          <td class="text-xs-left">{{ props.item.description }}</td>
          <td class="justify-center layout px-0">
            <v-icon small class="mr-2" @click="editItem(props.item)">
              edit
            </v-icon>
            <v-icon small @click="deleteItem(props.item)">
              delete
            </v-icon>
          </td>
        </template>        
      </v-data-table>
    </div>
    <!-- Dialog -->
    <v-dialog v-model="dialog" max-width="300">
      <v-btn slot="activator" color="pink" v-model="fab" dark fab fixed bottom right>
        <v-icon>add</v-icon>
      </v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">{{ formTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex v-if=deleteMode>
                <v-card-text>Tem certeza de que deseja deletar?</v-card-text>
              </v-flex>
              <v-flex v-else>
                <v-text-field color="teal" v-model="category.description" label="Nome"></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="teal darken-1" flat @click.native="close">Cancelar</v-btn>
          <v-btn v-if=deleteMode color="teal darken-1" flat @click.native="confirm">Confirmar</v-btn>
          <v-btn v-else color="teal darken-1" flat @click.native="save">Salvar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
import { getAllCategories, remove, post, put } from '@/services/category'
import Menu from '@/components/Menu'

export default {
  name: 'Category', 
  components: { Menu },
    data: () => ({
      fab: false,
      dialog: false,
      deleteMode: false,
      headers: [
        {
          text: 'Nome',
          align: 'left',
          sortable: true,
          value: 'name'
        }
      ],
      categories: [],
      editedIndex: -1,
      category: {
        id: '',
        description: ''
      },
      defaultItem: {
        description: '',
      },
      rowsPerPage: [5,10],
      rowsPerPageText: 'Items por página',
      noDataText: 'Desculpa, nenhum registro para ser apresentado!',
      snackbar: '',
      snackbarText: ''
    }),
    computed: {
      formTitle () {
        if (this.deleteMode === true) {
          return 'Deletar Categoria'
        }
        return this.editedIndex === -1 ? 'Nova Categoria' : 'Editar Categoria'
      }
    },
    watch: {
      dialog (val) {
        val || this.close()
      }
    },
    mounted () {
      this.initialize()
    },
    methods: {
      initialize () {
        const data = this
        getAllCategories().then(response => { data.categories = response })
      },
      editItem (item) {
        this.editedIndex = this.categories.indexOf(item)
        this.category = Object.assign({}, item)
        this.dialog = true
      },
      deleteItem (item) {
        this.deleteMode = !this.deleteMode
        this.category = item
        this.editedIndex = this.categories.indexOf(item)
        this.dialog = true
      },
      close () {
        this.dialog = false
        setTimeout(() => {
          this.deleteMode = this.deleteMode === true? !this.deleteMode : this.deleteMode
          this.category = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },
      confirm () {
        const data = this
        remove(data.category.id).then(response => {
          if (response) {
            data.categories.splice(data.editedIndex, 1)
            data.initSnackbar('Categoria deletada com sucesso!')  
          } else {
            data.initSnackbar('Categoria relacionada a transação!')  
          }
        })
        data.close()
      },
      initSnackbar(text) {
        this.snackbarText = text
        this.snackbar = true
      },
      save () {
        const data = this
        const category = data.category
        if (data.editedIndex > -1) {
          put(JSON.stringify(category)).then(response => {
            if (response) {
              Object.assign(data.categories[data.editedIndex], category),
              data.initSnackbar('Categoria salva com sucesso!')
            } else {
              data.initSnackbar('Problema ao salvar!')
            }
          })
        } else {
          post(JSON.stringify(data.category)).then(response => {
            if (response) {
              data.categories.push(response),
              data.initSnackbar('Categoria salva com sucesso!')
            } else {
              data.initSnackbar('Problema ao salvar!')
            }
          })
        }
        data.close()
      }
    }
  }
</script>

<style scoped>

  h2 {
    color: #009688;
    align-items: flex-end;
    margin: 10px;
    font-size: 20pt;
  }

</style>