import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import axios from 'axios'
Vue.prototype.$axios = axios


router.beforeEach((to, from, next) => {
  //路由发生改变时,触发
  window.document.title = to.meta.title == undefined ? '默认标题' : to.meta.title
  
  next();
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
