/*
 * @Author: jack.Lou 
 * @Date: 2024-03-04 11:38:13 
 * @Last Modified by: jack.Lou
 * @Last Modified time: 2024-03-21 15:54:18
 */

<template>
    <div class="sign_in">
	    <div class="warp">
			<el-row>
				<el-col>
					<el-form @keyup.native.enter :model="loginForm" :rules="rules" ref="loginForm" label-width="60px" class="form">
						<h1>学科竞赛管理系统</h1>
						
						<el-form-item label="账号" prop="username">
							<el-input type="text" v-model="loginForm.username" placeholder="用户名">
							</el-input>
						</el-form-item>
						
						<el-form-item label="密码" prop="password">
							<el-input type="password" v-model="loginForm.password" show-password placeholder="5 到 16 个字符"
								autocomplete="off">
							</el-input>
						</el-form-item>

						<el-form-item prop="verifyCode">
						<el-col :span="16">
							<el-input
							@keyup.enter="sign_in('loginForm')"
							v-model="loginForm.verifyCode"
							prefix-icon="el-icon-message"
							placeholder="验证码"
							class="verifyCode"
						></el-input>
						</el-col>
						<el-col :span="8">
							<img class="verifyCodeImg" :src="imgUrl" @click="resetImg">
						</el-col>
						
						</el-form-item>

						

						<div class="btns">
							<el-button type="primary"  @click="sign_in('loginForm')">登录</el-button>
						</div>
						<div class="btns">
							<router-link to="/forgot" >忘记密码?</router-link>
						</div>
					</el-form>
				</el-col>
			</el-row>
		</div>
    </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

  export default {
    //import引入的组件需要注入到对象中才能使用
    components: {},
    data() {
      
      return {
		imgUrl:"http://localhost:8080/verifyCode?time="+new Date(),
        loginForm: {
					username: "",
					password: "",
				},
                rules: {
					username: [{
							required: true,
							message: '请输入账号',
							trigger: 'blur'
						},
						{
							min: 5,
							max: 16,
							message: '长度在 5 到 16 个字符',
							trigger: 'blur'
						}
					],
					password: [{
							required: true,
							message: '请输入密码',
							trigger: 'blur'
						},
						{
							min: 5,
							max: 16,
							message: '长度在 5 到 16 个字符',
							trigger: 'blur'
						}
					]
				}
            }
    },
    //监听属性 类似于data概念
    computed: {},
    //监控data中的数据变化
    watch: {},
    //方法集合
    methods: {

      resetImg(){
          this.imgUrl = "http://localhost:8080/verifyCode?time="+new Date();
      },
		sign_in(form1){
				this.$refs[form1].validate((res1)=>{
					if(!res1){
						return;
					}
					this.$axios.post("/login",this.loginForm).then(resp=>{
						let result = resp.data;
						console.log(resp);
						if(result.code==='0'){
							sessionStorage.setItem('username',result.data.user.username)
							sessionStorage.setItem('userGroup',result.data.user.userGroup)
							sessionStorage.setItem('realname',result.data.user.realname)
							sessionStorage.setItem('userId',result.data.user.userId)
							sessionStorage.setItem('setUser',result.data.user)
							sessionStorage.setItem('token',result.data.token)
							localStorage.setItem('token',result.data.token)
							console.log('token:')
							console.log(localStorage.getItem('token'))
							console.log(sessionStorage.getItem('userGroup'))
							console.log(sessionStorage.getItem('realname'))
							console.log(sessionStorage.getItem('userId'))
							console.log(result.data.user)
							// sessionStorage.setItem('is_login',1); //存储登录状态
							this.$router.push("/")
							return this.$message({
								message:result.msg,
								type:'success'
							});
						}
						this.loginForm.verifyCode='';
						this.resetImg();
						this.$message.error(result.msg);
					})
				});
			},

        
    },
    //生命周期 - 创建完成（可以访问当前this实例）
    created() {},
    //生命周期 - 挂载完成（可以访问DOM元素）
    mounted() {},
    beforeCreate() {}, //生命周期 - 创建之前
    beforeMount() {}, //生命周期 - 挂载之前
    beforeUpdate() {}, //生命周期 - 更新之前
    updated() {}, //生命周期 - 更新之后
    beforeDestroy() {}, //生命周期 - 销毁之前
    destroyed() {}, //生命周期 - 销毁完成
    activated() {}, //如果页面有keep-alive缓存功能，这个函数会触发
  }
</script>
<style>
.sign_in {
		box-sizing: border-box;
		height: 100vh;
		padding: .5rem;
		width: 100%;
		background-image: url(../assets/images/bg.jpg);
        
		background-size: 100% 100%;
		color: #758fa7
	}
	.warp {
		width: 100%;
	}
    .sign_in h1,
	.sign_in .btns {
		text-align: center;
	}

	.sign_in .btns .el-button {
		width: 40%;
	}

	.sign_in .el-form-item__label {
		color: #fff;
	}

	.sign_in .form {
        background-color: rgba(209, 242, 249, 0.5);
		box-sizing: border-box;
		padding: 1rem 3rem;
		margin: auto;
		margin-top: 17rem;
		width: 100%;
		max-width: 25rem;
		box-shadow: 0 0 0 1px hsla(240, 0%, 100%, .3) inset,
			0 .5em 1em rgba(50, 50, 50, 0.6);
		text-shadow: 0 1px 1px hsla(240, 0%, 100%, .5);
		overflow: hidden;
		border-radius: 0.5rem;
	}

	.sign_in .form:after {
		content: '';
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(255, 255, 255, 0.8);
		z-index: -1;

		background-image: url(../assets/images/bg.jpg);
        
        /* background-color: aqua; */
		background-position: center top;
		background-size: cover;
		background-attachment: fixed;
		-webkit-filter: blur(20px);
		-moz-filter: blur(20px);
		-ms-filter: blur(20px);
		-o-filter: blur(20px);
		filter: blur(20px);
	}

</style>