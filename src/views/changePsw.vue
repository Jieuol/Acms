<!--  -->
<template>
  <div class="page-container">
	<div class="forgot-password">
		<div class="content">
		<el-steps :active="active" finish-status="success">
			<el-step title="重置密码"></el-step>

		</el-steps>

		


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
			<el-button style="margin-top: 12px;" @click="changePsw('form')">确认修改</el-button>
	

		</div>
	</div>
  </div>
</template>

<script>
  export default {
	data(){
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
		checkEmail(verificationForm) {
			this.$refs.verificationForm.validate((res1)=>{
				if(!res1){
					return;
				}
				this.$axios.post("/forgot/checkEmail",this.verificationForm).then(resp=>{
					let result = resp.data;
					console.log(resp);
					if(result.code==='0'){
						sessionStorage.setItem('username',result.data.username);
						console.log(sessionStorage.getItem('username'));
						if (this.active++ > 2) this.active = 0;
						return this.$notify({
              title: '成功',
							message:result.msg,
							type:'success'
						});
					}
          this.$notify({
          title: '失败',
          message: result.msg,
          type: 'error'
          });
					
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
					let result = resp.data;
					console.log(resp);
					if(result.code==='0'){
						this.$router.push('/login')
						if (this.active++ > 2) this.active = 0;
						return this.$notify({
              title: '密码修改成功',
							message:result.msg,
							type:'success'
						});
					}
          this.$notify({
          title: '失败',
          message: result.msg,
          type: 'error'
          });
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