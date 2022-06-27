import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueViewer from "_v-viewer@1.6.4@v-viewer";
import 'viewerjs/dist/viewer.css'
// import { Menu,Button, Select, Radio, Container, Aside, Header, Main } from 'element-ui';

Vue.config.productionTip = false;
Vue.config.silent = true

Vue.use(ElementUI);
Vue.use(VueViewer)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
