<template>
  <div>
    <v-dialog class="dialog" v-model="showDialog" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">{{title}}</span>
        </v-card-title>
        <v-form ref="transactionDialog" v-model="valid" @keyup.native.enter="saveTransaction" lazy-validation>
          <div class="form">
            <v-text-field color="teal" ref="value" v-model.lazy="transaction.value" v-money="money" label="Valor"></v-text-field>
            <v-select no-data-text="Nenhuma categoria cadastrada" color="teal" v-model="transaction.categoryId" 
              :items="items" item-value="id" item-text="description" label="Categoria"></v-select>
            <v-text-field color="teal" v-model="transaction.description" label="Descrição"></v-text-field>
          </div>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="teal" flat @click.native="showDialog = false">Fechar</v-btn>
            <v-btn color="teal" flat @click.native="saveTransaction">Salvar</v-btn>
          </v-card-actions>
        </v-form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { VMoney } from 'v-money'

export default {
  name: 'Dialog', 
  data () {
    return {
      valid: true,
      title: '',
      showDialog: false,
      transaction: {
        id: null,
        categoryId: '',
        description: '',
        value: '',
        typeTransaction: '',
        year: '',
        month: ''
      },
      money: {
        decimal: '.',
        thousands: '',
        suffix: '',
        precision: 2,
      },
      items: [],
      modal: false
    }
  },
  directives: { money: VMoney },
  methods: {
    saveTransaction() {      
    }    
  },
  watch: {
    showDialog: function() {
      const transaction = this.transaction
      if (transaction.id == 0) {
        // v-money has a bug in v-text-field of vuetify, so it isn't possible on reset form
        this.$refs.value.$el.getElementsByTagName('input')[0].value = 0
        this.$refs.transactionDialog.reset()        
      } else {
        this.$refs.value.$el.getElementsByTagName('input')[0].value = transaction.value
      }
    }
  }
}
</script>

<style scoped>

  .form {
    padding: 0% 5% 0% 5%
  }
  
</style>