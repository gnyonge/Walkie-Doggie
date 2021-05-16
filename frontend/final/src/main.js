import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import axios from 'axios';
import router from './router'
import store from './store'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import '@babel/polyfill' //이거 추가 안하면 아래 에러남 
import VueHtml2Canvas from 'vue-html2canvas';

Vue.prototype.$axios = axios;
Vue.use(BootstrapVue)
Vue.use(VueHtml2Canvas);
Vue.config.productionTip = false
window.Kakao.init("5ff8ab5b5a612a7420974029c652a757");


new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
