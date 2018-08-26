<template>
  <div id="reportDialog">
    <v-dialog class="dialog" v-model="showDialog" ref="reportDialog" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">{{title}}</span>
        </v-card-title>
        <v-form ref="recordForm" v-model="valid" @keyup.native.enter="saveRecord" lazy-validation>
          <div class="form">
            <v-text-field color="teal" v-model.lazy="value" v-money="money" label="Valor"></v-text-field>
            <v-select color="teal" v-model="selected" :items="items" label="Categoria"></v-select>
            <v-text-field color="teal" v-model="note" label="Observação"></v-text-field>
            <v-dialog ref="dateDialog" v-model="modal" :return-value.sync="date" persistent lazy full-width width="290px">
              <v-text-field slot="activator" v-model="dateString" label="Data" prepend-icon="event" readonly></v-text-field>
              <v-date-picker v-model="date" locale="pt-br" color="teal" scrollable>
                <v-spacer></v-spacer>
                <v-btn flat color="teal" @click="modal = false">Cancelar</v-btn>
                <v-btn flat color="teal" @click="$refs.dateDialog.save(date)">OK</v-btn>
              </v-date-picker>
            </v-dialog>            
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
      value: '',
      money: {
        decimal: ',',
        thousands: '.',
        prefix: 'R$ ',
        suffix: '',
        precision: 2,
        masked: false
      },
      items: ['Alimentação', 'Salário', 'Combustível'],
      selected: '',
      note: '',
      date: null,
      dateString: '',
      modal: false
    }
  },
  directives: { money: VMoney },
  methods: {
    saveRecord() {      
    }    
  },
  watch: {
    showDialog() {
      this.$refs.recordForm.reset()
    },
    date() {
      let date = this.date      
      this.dateString = date.substring(8, 10)+'/'+date.substring(5, 7)+'/'+date.substring(0, 4);
    }
  }
}
</script>

<style scoped>

  .form {
    padding: 0% 5% 0% 5%
  }

  @media screen and (max-width: 600px)  {

  }

</style>