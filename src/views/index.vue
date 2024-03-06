/*
 * @Author: jack.Lou 
 * @Date: 2024-03-04 11:38:25 
 * @Last Modified by: jack.Lou
 * @Last Modified time: 2024-03-06 16:37:53
 */

<template>
	<div class="nav">
		<el-container class="home-container">
        <el-header>
            <div>
                <img src="../assets/logo.png" alt="" height="50px"/>
                <span>高校学科竞赛管理系统</span>
            </div>
			<div class="userName">
				<span >欢迎您：{{userName}}</span>
			</div>
            <el-button type="info" @click="logout()">退出</el-button>
        </el-header>
        <!-- 页面主体区域 -->
        <el-container>
            <!-- 侧边栏 -->
            <el-aside width="200px">
				<el-menu
					background-color="#b0d9ee"
					text-color="black"
					active-text-color="red"
					router
					:default-active="$route.path">
					<el-submenu index="1">
						<template slot="title">
						<i class="el-icon-setting"></i>
						<span>个人信息管理</span>
						</template>
						<el-menu-item-group>
							<el-menu-item index="/userInfo">查看个人信息</el-menu-item>
							<el-menu-item index="/changePsw">修改密码</el-menu-item>
						</el-menu-item-group>
					</el-submenu>

					<el-menu-item index="/notice">
						<i class="el-icon-warning"></i>
						<span slot="title">公告栏</span>
					</el-menu-item>

					


					<el-submenu index="/1" v-if="userGroup=='学生'">
						<template slot="title">
						<i class="el-icon-setting"></i>
						<span>竞赛项目报名</span>
						</template>
						<el-menu-item-group>
							<el-menu-item index="/participate">查看竞赛项目</el-menu-item>
							<el-menu-item index="/myContest">我的竞赛项目</el-menu-item>
						</el-menu-item-group>
					</el-submenu>

					<el-submenu index="/1" v-if="userGroup=='老师'">
						<template slot="title">
						<i class="el-icon-setting"></i>
						<span>竞赛项目申报</span>
						</template>
						<el-menu-item-group>
							<el-menu-item index="/contestRegistration">申报竞赛项目</el-menu-item>
							<el-menu-item index="/myApplication">我的竞赛项目</el-menu-item>
						</el-menu-item-group>
					</el-submenu>



					<el-menu-item index="/enrollmentManagement" v-if="userGroup=='老师'||userGroup=='管理员'">
						<i class="el-icon-menu"></i>
						<span slot="title">报名人员管理</span>
					</el-menu-item>


					<el-menu-item index="/contestApproval" v-if="userGroup=='管理员'">
						<i class="el-icon-menu"></i>
						<span slot="title">竞赛项目审批</span>
					</el-menu-item>

					
					<el-menu-item index="/gradesManagement" v-if="userGroup=='老师'||userGroup=='管理员'">
						<i class="el-icon-menu"></i>
						<span slot="title">竞赛成绩管理</span>
					</el-menu-item>
					

					<el-menu-item index="/studenrtResultSearch" v-if="userGroup=='学生'">
						<i class="el-icon-menu"></i>
						<span slot="title">个人成绩查询</span>
					</el-menu-item>
					
					<el-menu-item index="/awardManagement" v-if="userGroup=='老师'">
						<i class="el-icon-menu"></i>
						<span slot="title">获奖信息管理</span>
					</el-menu-item>
					
					<el-menu-item index="/awardManagement" v-if="userGroup=='管理员'">
						<i class="el-icon-menu"></i>
						<span slot="title">获奖信息管理</span>
					</el-menu-item>

					<el-menu-item index="/userManagement" v-if="userGroup=='管理员'">
						<i class="el-icon-menu"></i>
						<span slot="title">用户管理</span>
					</el-menu-item>

				</el-menu>	

			</el-aside>
            <!-- 右侧内容主体 -->
            <el-main> 
				<!-- 路由页面 -->
				<transition name="fade" mode="out-in">
					<router-view></router-view>
				</transition>

			 
			</el-main>
        </el-container>
    </el-container>
	</div>
</template>


<style scoped>
.nav{
	height: 100vh;
		padding: .5rem;
		width: 100%;
		background-image: url(../assets/images/bg.jpg);
        
		background-size: 100% 100%;
		color: #758fa7
}

.home-container{
	height: 100%;
}
 .el-header{
     background-color: rgba(209, 242, 249, 0.5);
     display: flex; 
     justify-content: space-between;
     padding-left: 0;
     align-items: center;
     color: #fff;
     font-size: 30px;
     > div {
         display: flex;
         align-items: center;
         span {
             margin-left: 15px;
         }
     }
 }
  .userName{
	margin-left: 280px;
	font-size: 20px;
 }
 
  .el-main{
    background-color: rgba(209, 242, 249, 0.5);
}
</style>

<script>


export default{
	data(){
		
		return{
			userGroup:sessionStorage.getItem('userGroup'),
			userName:sessionStorage.getItem('username'),
		}
	},
	components:{

	},
	methods:{
		logout(){
			sessionStorage.setItem('is_login',0);
			this.$router.push('/login');
		},

	},
};
</script>

