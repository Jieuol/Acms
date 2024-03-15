<!--  -->
<template>
  <div class="page-container">
	<div class="forgot-password">
		<button type="text" v-show="active>='1'" class="return-page-wr" @click="$router.go(-1)"> <i class="el-icon-arrow-left"></i> 上一步 </button>
		<button type="text" v-show="active=='0'" class="return-page-wr" @click="$router.push({path:'/login'})"> <i class="el-icon-arrow-left"></i> 返回 </button>
		<div class="content">
		<el-steps :active="active" finish-status="success">
			<el-step title="账号验证"></el-step>
			<el-step title="重置密码"></el-step>
			<el-step title="完成设置"></el-step>
		</el-steps>

		<div v-if="active=='0'">
			<el-form :model="verificationForm" :rules="verificationFormRules" ref="verificationForm"  class="demo-ruleForm" >
				<el-form-item prop="username" label="用户名">
				<el-input v-model="verificationForm.username" type="text" placeholder="用户名">
				</el-input>
				</el-form-item>

				<el-form-item prop="email" label="邮箱">
				<el-input v-model="verificationForm.email" type="text" placeholder="请输入您绑定的邮箱">
				</el-input>
				</el-form-item>

        <el-form-item prop="verifyCode" label="验证码">
						<el-col :span="16">
							<el-input
							v-model="verificationForm.verifyCode"
							prefix-icon="el-icon-message"
							placeholder="验证码"
							class="verifyCode"
						></el-input>
						</el-col>
						<el-col :span="8">
							<img class="verifyCodeImg" :src="imgUrl" @click="resetImg">
						</el-col>
						
						</el-form-item>
			</el-form>
			<el-button style="margin-top: 12px;" @click="checkEmail('form')">验证</el-button>
		</div>

		<div v-if="active=='1'">
			<el-form ref="forgotPasswordForm" :model="forgotPasswordForm" :rules="forgotPasswordFormRules"  class="demo-ruleForm">
				<el-form-item prop="password" label ="新的密码:">
				<el-input v-model="forgotPasswordForm.password" show-password type="password" placeholder="6-16位大小写字母+数字组合" auto-complete="off" >
				</el-input>
				</el-form-item>
				<el-form-item prop="confirmPwd" label ="确认密码:">
				<el-input v-model="forgotPasswordForm.confirmPwd" show-password placeholder="重复密码" type="password">
				</el-input>
				</el-form-item>
			</el-form>
			<el-button style="margin-top: 12px;" @click="changePsw('verificationForm')">确认修改</el-button>
		</div>
	

		</div>
	</div>
  </div>
</template>

<script>
  export default {
	data(){
      //自定义的邮箱和手机验证规则
      var checkEmail = (rule, value, callback) => {
      const regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      if (regEmail.test(value)) {
        return callback();
      }
      callback(new Error("请输入合法的邮箱"));
    };
    var checkMobile = (rule, value, callback) => {
      const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$/;
      if (regMobile.test(value)) {
        return callback();
      }
      callback(new Error("请输入合法的手机号"));
    };
    let validateAccount = (rules,value,callback) =>{
      if (/[\u4E00-\u9FA5]/g.test(value)) {
        callback(new Error('账户名称不能输入汉字!'));
      } else if (/^[0-9]+$/g.test(value)) {
        callback(new Error('账户名称不能输入纯数字!'));
      } else {
        callback();
      }

    }
    let validateConfirmPass = (rule, value, callback) => {
      if (value !== this.forgotPasswordForm.password) {
        callback(new Error('两次输入密码不一致,请重新输入'));
      } else {
        callback();
      }
    };
  return{
    imgUrl:"http://localhost:8080/verifyCode?time="+new Date(),
    active: 0,
    verificationForm:{
      email:'',
      username:'',
    },
    forgotPasswordForm:{
      password:'',
      confirmPwd:'',
      username:''
    },
    options: [],
    verificationFormRules:{
      email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { validator: checkEmail, trigger: "blur" },
        ],

      username:[
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 1, max: 15, message: '用户名不超过15个字符,请重新输入',trigger: "blur" },
          { validator: validateAccount, trigger: 'blur' }
        ],
    },
    forgotPasswordFormRules: {
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 16, message: '长度在6-16位之间',trigger: "blur" },
        {pattern: new RegExp("^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,16}$") , message: '密码为6-16位大小写字母+数字组合，请重新输入',trigger: "blur" }
      ],
      confirmPwd: [
        { required: true, message: '请输入确认密码', trigger: 'blur' },
        { validator: validateConfirmPass, trigger: 'blur' },
        { min: 6, max: 16, message: '长度在6-16位之间',trigger: "blur" },
        {pattern: new RegExp("^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,16}$") , message: '密码为6-16位大小写字母+数字组合，请重新输入',trigger: "blur" }
      ],
    }

  }
  },

    methods: {
      resetImg(){
          this.imgUrl = "http://localhost:8080/verifyCode?time="+new Date();
      },
		checkEmail(verificationForm) {
			this.$refs.verificationForm.validate((res1)=>{
				if(!res1){
					return;
				}
				this.$axios.post("/forgot/checkEmail",this.verificationForm).then(resp=>{
          console.log("-------------------");
          console.log(this.verificationForm);
					let result = resp.data;
					console.log(resp);
					if(result.code==='0'){
						sessionStorage.setItem('username',result.data.username);
						console.log(sessionStorage.getItem('username'));
						if (this.active++ > 2) this.active = 0;
						return this.$message({
							message:result.msg,
							type:'success'
						});
					}
          this.verificationForm.verifyCode='';
          this.resetImg();
					this.$message.error(result.msg);
				})
			});
        
      },
	  changePsw(forgotPasswordForm) {
			this.$refs.forgotPasswordForm.validate((res1)=>{
				this.forgotPasswordForm.username=sessionStorage.getItem('username')
				
				console.log("this.forgotPasswordForm.username:"+this.forgotPasswordForm.username);
				if(!res1){
					return;
				}
				this.$axios.post("/forgot/changePsw",this.forgotPasswordForm).then(resp=>{
          console.log("-------------------");
          console.log(this.forgotPasswordForm);
					let result = resp.data;
					console.log(resp);
					if(result.code==='0'){
						this.$router.push('/login')
						if (this.active++ > 2) this.active = 0;
						return this.$message({
							message:result.msg,
							type:'success'
						});
					}
					this.$message.error(result.msg);
				})
			});
        
      },
    }
  }
</script>

<style scoped>
.page-container {
  width: 100%;
  height: 100vh;
  min-height: 600px;
  overflow-y: auto;
  /* background: url("../../assets/images/login/login-bag.jpg") no-repeat center; */
  background-size: cover;
  display: flex;
  align-items: center;
  justify-content: center;
  .wid{
    width: 100%;
  }
  .title-img-wr {
    text-align: center;
  }

  .forgot-password{
    .return-page-wr{
      position: relative;
      top: 30px;
      left: 30px;
      color: #e5525b;
      font-size: 16px;
      margin-bottom: 15px;
      i{
        font-size: 20px;
        vertical-align: middle;
      }
    }
    width:580px;
    height:540px;
    background: #FFFFFF;
    border-radius: 6px;
   .content{
     padding: 30px 90px;
     box-sizing: border-box;
   }

    /deep/.el-step.is-horizontal{
      .el-step__line{
        height: 2px;
        top: 20px;
        left: 48px;
        right: 10px;
      }
    }
    /deep/.el-step__icon{
      width: 42px;
      height: 42px;
      color: #e6545a;
      font-size: 18px;
      border: 2px solid #e6545a;
    }
    /deep/.el-step__line{
      background: #e6545a;
    }
    /deep/.is-process{
      .el-step__icon{
        background: #e6545a;
        color: #FFFFFF;
        border: 0px;

      }
    }
    /deep/.el-step__head{
      margin-bottom: 20px;
    }
    /deep/.el-step__title.is-process{
      color: #e6545a;
    }


    /deep/.el-form-item__label{
      width: 100%;
      text-align: left;
    }
    /deep/.el-form-item__content{
      display: inline-block;
      width: 100%;
      margin-left: 0px;
    }
  }
  .btn-wr{
    text-align: center;
  }
  .reset-success{
    text-align: center;
    margin: 60px 0;
  }
  .set-success{
    margin-top: 20px;
    font-weight: 600;
  }
  .login-btn{
    width: 200px;
    height:50px;
    text-align: center;
    line-height: 50px;
    margin:0 auto;
    background: #e6545a;
    font-weight: 600;
    font-size: 16px;
    color: #FFFFFF;
    border-radius: 4px;
    i{
      right: -50px;
      font-size: 27px;
      position: relative;
      vertical-align: middle;
    }
  }
}

</style>