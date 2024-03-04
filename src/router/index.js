import Vue from 'vue'
import VueRouter from 'vue-router'
import index from '../views/index.vue'
import login from '../views/login.vue'
import forgot from '@/views/forgot.vue'
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
	// 主页
	{
		path: '/',
		name: 'index',
		component: index,
		beforeEnter: (to,from,next) =>{
			let isLogin = sessionStorage.getItem('is_login');
			if(isLogin!=1){
				return next('/login')
			}
			next();
		},
		meta: {
			index: 0,
			title: '首页'
		},
		children: [
			{
			    path: '/changePsw',
			    name: 'changePsw', 
			    component: () => import("@/views/changePsw.vue"),
				meta: {
					index: 0,
					title: '修改密码'
				},
			},
			{
			    path: '/userInfo',
			    name: 'userInfo', 
			    component: () => import("@/views/userInfo.vue"),
				meta: {
					index: 0,
					title: '修改个人信息'
				},
			},
		]

	},

	// 登录
	{
		path: '/login',
		name: 'login',
		component: login,
		meta: {
			index: 0,
			title: '登录'
		}
	},

	// 忘记密码
	{
		path: '/forgot',
		name: 'forgot',
		component: forgot,
		meta: {
			index: 0,
			title: '忘记密码'
		}
	},
 

]

const router = new VueRouter({
  routes
})

export default router
