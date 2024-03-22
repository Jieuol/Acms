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
		// beforeEnter: (to,from,next) =>{
		// 	let isLogin = sessionStorage.getItem('is_login');
		// 	if(isLogin!=1){
		// 		return next('/login')
		// 	}
		// 	next();
		// },
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
			    path: '/myInformation',
			    name: 'myInformation', 
			    component: () => import("@/views/myInformation.vue"),
				meta: {
					index: 0,
					title: '修改个人信息'
				},
			},
			{
			    path: '/notice',
			    name: 'notice', 
			    component: () => import("@/views/notice.vue"),
				meta: {
					index: 0,
					title: '公告栏'
				},
			},
			{
			    path: '/participate',
			    name: 'participate', 
			    component: () => import("@/views/student/participate.vue"),
				meta: {
					index: 0,
					title: '竞赛报名'
				},
			},
			{
			    path: '/contestRegistration',
			    name: 'contestRegistration', 
			    component: () => import("@/views/teacher/contestRegistration.vue"),
				meta: {
					index: 0,
					title: '竞赛项目申报'
				},
			},

			{
			    path: '/myContest',
			    name: 'myContest', 
			    component: () => import("@/views/student/myContest.vue"),
				meta: {
					index: 0,
					title: '我的已报名项目'
				},
			},

			{
			    path: '/myApplication',
			    name: 'myApplication', 
			    component: () => import("@/views/teacher/myApplication.vue"),
				meta: {
					index: 0,
					title: '我的申报项目'
				},
			},

			

			{
			    path: '/enrollmentManagement',
			    name: 'enrollmentManagement', 
			    component: () => import("@/views/teacher/enrollmentManagement.vue"),
				meta: {
					index: 0,
					title: '报名人员管理'
				},
			},

			{
			    path: '/contestApproval',
			    name: 'contestApproval', 
			    component: () => import("@/views/admin/contestApproval.vue"),
				meta: {
					index: 0,
					title: '竞赛项目审批'
				},
			},
			{
			    path: '/gradesManagement',
			    name: 'gradesManagement', 
			    component: () => import("@/views/teacher/gradesManagement.vue"),
				meta: {
					index: 0,
					title: '竞赛成绩管理'
				},
			},

			{
			    path: '/studenrtResultSearch',
			    name: 'studenrtResultSearch', 
			    component: () => import("@/views/student/studenrtResultSearch.vue"),
				meta: {
					index: 0,
					title: '个人成绩查询'
				},
			},
			{
			    path: '/awardManagement',
			    name: 'awardManagement', 
			    component: () => import("@/views/teacher/awardManagement.vue"),
				meta: {
					index: 0,
					title: '获奖信息管理'
				},
			},
			{
			    path: '/userManagement',
			    name: 'userManagement', 
			    component: () => import("@/views/admin/userManagement.vue"),
				meta: {
					index: 0,
					title: '用户管理'
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
	// 错误处理404
	{
		path: '/404',
		name: 'error_404',
		component:  () => import("@/views/error_404/404.vue"),
		meta: {
			index: 0,
			title: '404'
		}
	},	
 	// 错误处理500
	 {
		path: '/500',
		name: 'error_500',
		component:  () => import("@/views/error_500/500.vue"),
		meta: {
			index: 0,
			title: '500'
		}
	},
	{
        path: '*',//匹配未定义的路由
        redirect: '/404'//重定向
    }	

]

const router = new VueRouter({
  routes
})

export default router
