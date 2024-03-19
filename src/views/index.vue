/*
 * @Author: jack.Lou 
 * @Date: 2024-03-04 11:38:25 
 * @Last Modified by: jack.Lou
 * @Last Modified time: 2024-03-19 10:49:11
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
				<el-button @click="createSseConnect(userId)" type="primary" style="margin-left: 16px;">
				消息
				</el-button>
			</div>
            <el-button type="info" @click="logout()">退出</el-button>
        </el-header>
        <!-- 页面主体区域 -->
        <el-container>
            <!-- 侧边栏 -->
            <el-aside style="background: rgba(255, 255, 255, 0.7);" width="200px">
				<el-menu
					style="background: rgba(255, 255, 255, 0.7);"
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
							
							<el-menu-item index="/myInformation">查看个人信息</el-menu-item>
							<!-- <el-menu-item index="/userInfo">查看个人信息</el-menu-item> -->
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

	//消息
    </el-container>
		
		<el-drawer
		title="我是标题"
		:visible.sync="drawer"
		:with-header="false">
		<span v-if="messageData==null">暂无消息</span>
		<el-collapse accordion>
		<el-collapse-item v-for="(list,index) in messageData" :key="index" >
		<template slot="title">
		<span v-if="list.state == '0'">[未读]</span>
		<span v-if="list.state == '1'" class="actived">[已读]</span>
		<b @click="messageActive(list.messageId,list.state)">{{list.messageName}}</b><i class="el-icon-alarm-clock time">{{list.createTime.substring(0,16)}}</i></template>
		<div>{{list.messageInformation}}</div>
		</el-collapse-item>
		</el-collapse>









		</el-drawer>
	</div>
</template>


<style scoped>
.nav{
	height: 100vh;
		padding: .5rem;
		width: 100%;
		background-image: url(../assets/images/bg.jpg);
        
		background-size: 100% 100%;
		color: #000000
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
.el-collapse{border:none;}
    .el-collapse div:last-child > div div{border:none;}
    .el-collapse-item__header{position:relative;font-size:16px;justify-content:space-between;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;}
    .el-collapse-item__header b{flex:1;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;font-weight:normal;}
    .is-active b{padding-left:20px;transition:all .5s;}
    .el-collapse-item__header .time{position:absolute;right:30px;font-size:14px;}
    .el-icon-alarm-clock:before{margin-right:10px;}
    .el-collapse-item__header span{font-size:12px;color:brown;margin-right:10px;}
    .el-collapse-item__header span.actived{color:#666;}
    .el-collapse-item__content{font-size:14px;line-height:26px;padding:30px 20px;color:#666;background:#f8f8f8;}
</style>

<script>


export default{
	data(){
		
		return{
			messageData:[],
			userId:sessionStorage.getItem("userId"),
			userGroup:sessionStorage.getItem('userGroup'),
			userName:sessionStorage.getItem('username'),
			drawer:false,
		}
	},
	components:{

	},
	methods:{
        getMessage(){
         this.$axios.get("/getMessage?userId="+this.userId).then(resp=>{
			let result = JSON.stringify(resp.data);
        	 result = eval("("+result+")");
			 console.log("--------------");
			 console.log(result);
			 if(result.code=='0'){
				this.messageData=result.data.messageData;
				console.log("--------------");
			 	console.log(this.messageData);
			 }

		 })
	  
		 },
	messageActive(eventId,eventState) {
      console.log(eventId,eventState);
      if(eventState == '0'){ //阅读状态是未读提交已读申请
        this.$axios.get("/updateMessage?messageId="+eventId).then(resp=>{
			let result = resp.data;
			if (result.data.messageData.state=='1') {
            //提交成功后更新阅读状态为已读不用重新申请数据也不用刷新页面
            this.getMessage();
          }
		})


		}
	},
		// 建立连接
		createSseConnect(userId){
			this.drawer = true;
			this.getMessage();
		},
		logout(){
			// sessionStorage.setItem('token','');
			sessionStorage.clear();
			this.$router.push('/login');
		},

	},
};
</script>

