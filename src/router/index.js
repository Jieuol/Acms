import Vue from 'vue'
import VueRouter from 'vue-router'
import index from '../views/index.vue'
import reg from '../views/reg.vue'
import send from '../views/send.vue'
import search from '../views/search.vue'
Vue.use(VueRouter)
// 获取原型对象push函数
const originalPush = VueRouter.prototype.push

// 获取原型对象replace函数
const originalReplace = VueRouter.prototype.replace

// 修改原型对象中的push函数
VueRouter.prototype.push = function push(location){
return originalPush.call(this , location).catch(err=>err)
}

// 修改原型对象中的replace函数
VueRouter.prototype.replace = function replace(location){
return originalReplace.call(this , location).catch(err=>err)
}

const routes = [
  {
    path: '/',
    name: 'index',
    component: index
  },
 

]

const router = new VueRouter({
  routes
})

export default router
