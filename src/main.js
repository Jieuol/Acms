import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import JsonExcel from 'vue-json-excel';
Vue.component('downloadExcel', JsonExcel)

import FileSaver from 'file-saver';
import XLSX from 'xlsx';
  
Vue.prototype.$FileSaver = FileSaver; //设置全局
Vue.prototype.$XLSX = XLSX; //设置全局
import axios from 'axios';
Vue.prototype.$axios = axios


router.beforeEach((to, from, next) => {
  //路由发生改变时,触发
  window.document.title = to.meta.title == undefined ? '默认标题' : to.meta.title
  
  next();
})
//路由全局前置守卫
router.beforeEach((to,from,next) => {
  if(to.path === '/forgot' || to.path === '/login' ){ //若是进入登录与注册页面 ==> pass
    next()
  }else{ 
    let userToken = sessionStorage.getItem('token');
    console.log("Token为:"+userToken); 
    if(userToken == null || userToken == ''){
      alert("无权限，请先登录!");
      return next('/login');
    }else{
      next();
    }
  }
}),



//请求拦截器 在请求头中加token
axios.interceptors.request.use(
  config => {
      if(sessionStorage.getItem('token')){
          config.headers.token = sessionStorage.getItem('token');
          config.headers.username = sessionStorage.getItem('username');
      }
      return config;
  },
  error => {
      return Promise.reject(error);
  }
)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
