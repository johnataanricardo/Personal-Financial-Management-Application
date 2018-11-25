<template>
  <div>
    <v-dialog class="dialog" v-model="showDialog" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">{{title}}</span>
        </v-card-title>
        <v-form ref="recordDialog" v-model="valid" @keyup.native.enter="saveRecord" lazy-validation>
          <div class="form">
            <v-text-field color="teal" ref="valor" v-model.lazy="valor" v-money="money" label="Valor"></v-text-field>
            <v-select no-data-text="Nenhuma categoria cadastrada" color="teal" v-model="idCategoria" 
              :items="items" item-value="id" item-text="descricao" label="Categoria"></v-select>
            <v-text-field color="teal" v-model="descricao" label="Descrição"></v-text-field>
          </div>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="teal" flat @click.native="showDialog = false">Fechar</v-btn>
            <v-btn color="teal" flat @click.native="saveRecord">Inserir</v-btn>
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
      valor: '',
      idCategoria: '',
      descricao: '',
      tipoDespesa: '',
      ano: '',
      mes: '',
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
    saveRecord() {      
    }    
  },
  watch: {
    showDialog: function() {
       // the only way to reset de value of valor, v-money has a bug in v-text-field of vuetify
      this.$refs.valor.$el.getElementsByTagName('input')[0].value = 0;
      this.$refs.recordDialog.reset()
    }
  }
}
</script>

<style scoped>

  .form {
    padding: 0% 5% 0% 5%
  }
  
</style>