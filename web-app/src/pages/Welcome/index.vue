<template>
  <div>
    <v-toolbar class="toolbar">
      <img src="static/icon.png" alt="Seu Financeiro" height="50">
      <v-toolbar-title class="primary-color bold-text" v-text="title"></v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn class="primary-color bold-text" flat :to="signInAction">Login</v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-content>
      <section>
        <v-parallax src="static/start.png" height="600">
          <v-layout column align-center justify-center class="white--text">
            <img src="static/icon.png" alt="Vuetify.js" height="200">
            <h1 class="mb-2 display-1 text-xs-center bold-text"> Seu Financeiro </h1>
            <div class="subheading mb-3 text-xs-center bold-text"> Desenvolvido por Seu Financeiro </div>
            <v-btn class="primary-color bold-text mt-5" large :to="signUpAction"> 
              Cadastrar-se 
            </v-btn>
          </v-layout>
        </v-parallax>
      </section>
      <section>
        <v-layout column wrap class="my-5" align-center>
          <v-flex xs12 sm4 class="my-3">
            <div class="text-xs-center">
              <h2 class="headline">Sua aplicação de controle financeiro</h2>
            </div>
          </v-flex>
          <v-flex xs12>
            <v-container grid-list-xl>
              <v-layout row wrap align-center>
                <v-flex xs12 md6>
                  <v-card class="elevation-0 transparent">
                    <v-card-text class="text-xs-center">
                      <v-icon x-large class="teal--text text--lighten-2">create</v-icon>
                    </v-card-text>
                    <v-card-title primary-title class="layout justify-center">
                      <div class="headline text-xs-center">Registros de Entradas e Saídas</div>
                    </v-card-title>
                    <v-card-text>
                      O Seu Financeiro funciona com registros de entradas e saídas mensal, possibilitando o usuário
                      fazer seus próprios registros mensais, categorizando estes registros e colocando uma descrição,
                      dando uma maior liberdade ao usuário na sua gestão financeira pessoal, de forma simples e objetiva.
                    </v-card-text>
                  </v-card>
                </v-flex>
                <v-flex xs12 md6>
                  <v-card class="elevation-0 transparent">
                    <v-card-text class="text-xs-center">
                      <v-icon x-large class="teal--text text--lighten-2">bar_chart</v-icon>
                    </v-card-text>
                    <v-card-title primary-title class="layout justify-center">
                      <div class="headline text-xs-center">Fluxo de Caixa Mensal</div>
                    </v-card-title>
                    <v-card-text>
                      O Seu Financeiro apresenta um fluxo de caixa mensal de seus registros de entradas e saídas,
                      e você ainda tem a possibilidade de comparar o fluxo de caixa de todos os meses em um gráfico de barras.
                    </v-card-text>
                  </v-card>
                </v-flex>
              </v-layout>
            </v-container>
          </v-flex>
        </v-layout>
      </section>
      <section>
        <v-container grid-list-xl>
          <v-layout row wrap justify-center class="my-5">
            <v-flex xs12 md3>
              <v-card class="elevation-0 transparent">
                <v-card-title primary-title class="layout justify-center">
                  <div class="headline">Download Aplicativos Android</div>
                </v-card-title>
                <v-card-text>
                  <a href="../../../static/apps/webview.apk" download>Android WebView</a>
                  <br/>
                  <a href="../../../static/apps/hybrid.apk" download>Android Híbrido</a>
                  <br/>
                  <a href="../../../static/apps/native.apk" download>Android Nativo</a>
                </v-card-text>
              </v-card>
            </v-flex>
            <v-flex xs12 md3>
              <v-card class="elevation-0 transparent">
                <v-card-title primary-title class="layout justify-center">
                  <div class="headline">Sobre</div>
                </v-card-title>
                <v-card-text>
                  O Seu Financeiro é um software simples e objetivo de gestão financeira pessoal, desenvolvido por
                  universitários de Sistemas de Informações, todo o código fonte do projeto está no 
                  <a target="_blank" href="https://github.com/johnataanricardo/Personal-Financial-Management-Application">GitHub</a>.
                </v-card-text>
              </v-card>
            </v-flex>
            <v-flex xs12 md3>
              <v-card class="elevation-0 transparent">
                <v-card-title primary-title class="layout justify-center">
                  <div class="headline">Linkedin</div>
                </v-card-title>
                <v-list class="transparent">
                  <v-list-tile>
                    <v-list-tile-action>
                      <v-avatar open-on-hover slot="activator" size="45" color="grey lighten-4">
                        <img src="../../../static/team/alana.png" >
                      </v-avatar>
                    </v-list-tile-action>
                    <v-list-tile-content>
                      <a target="_blank" href="https://www.linkedin.com/in/alanadomitbittar/">Alana Domit Bittar</a>
                    </v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-action>
                      <v-avatar open-on-hover slot="activator" size="45" color="grey lighten-4">
                        <img src="../../../static/team/eduardo.png" >
                      </v-avatar>
                    </v-list-tile-action>
                    <v-list-tile-content>
                      <a target="_blank" href="https://www.linkedin.com/in/eduardo-pereira-56281368/">Eduardo Pereira</a>
                    </v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-action>
                      <v-avatar open-on-hover slot="activator" size="45" color="grey lighten-4">
                        <img src="../../../static/team/johnatan.png" >
                      </v-avatar>
                    </v-list-tile-action>
                    <v-list-tile-content>
                      <a target="_blank" href="https://www.linkedin.com/in/johnataanmartins">Johnatan Ricardo Martins</A>
                    </v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-action>
                      <v-avatar open-on-hover slot="activator" size="45" color="grey lighten-4">
                        <img src="../../../static/team/lucas.png" >
                      </v-avatar>
                    </v-list-tile-action>
                    <v-list-tile-content>
                      <a target="_blank" href="https://www.linkedin.com/in/lucas-ribeiro691/">Lucas Ribeiro</a>
                    </v-list-tile-content>
                  </v-list-tile>
                </v-list>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
      </section>            
      <a target="_blank" href="http://www.freepik.com">Imagens Designed by Freepik</a>
      <v-footer class="footer-background darken-2">
        <v-layout row wrap align-center>
          <v-flex xs12>
            <div class="white--text ml-3">
              <v-icon small class="white--text">copyright</v-icon>
              2018 Todos os direitos reservados Seu Financeiro
            </div>
          </v-flex>
        </v-layout>
      </v-footer>
    </v-content>
  </div>
</template>

<script>
export default {
  name: 'Welcome',
  data () {
    return {
      title: 'Seu Financeiro',
      signInAction: '/login',
      signUpAction: '/signup'
    }
  }
}
</script>

<style scoped>

  .primary-color {
    color: #009688 !important;
  }

  .bold-text {
    font-weight: bold;
  }

  .v-footer {
    background: #009688 !important;    
  }

</style>
