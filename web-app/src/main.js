import Vue from 'vue';
import Vuetify from 'vuetify';
import VueResource from 'vue-resource';
import router from './router';
import App from './App';
import 'vuetify/dist/vuetify.min.css';

const api = process.env.API_URL;

Vue.config.productionTip = false;
Vue.use(Vuetify);
Vue.use(VueResource);
Vue.http.options.root = process.env.API;

Vue.router = router;
Vue.use(require('@websanova/vue-auth'), {
  auth: require('@websanova/vue-auth/drivers/auth/bearer.js'),
  http: require('@websanova/vue-auth/drivers/http/vue-resource.1.x.js'),
  router: require('@websanova/vue-auth/drivers/router/vue-router.2.x.js'),
  rolesVar: 'type',
  loginData: {url: api + '/token', method: 'POST', redirect: '/home', fetchUser: false},
});

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
