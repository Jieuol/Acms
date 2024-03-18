<template>
    <div>
      <el-card>
        <el-descriptions class="margin-top" title="简介" :column="2" border>
          <template slot="extra">
            <el-button type="primary" size="small" @click="dialogFormVisible = true">修改个人信息</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-picture-outline"></i>
              头像
            </template>
            <div class="avater">  
                 <el-avatar shape="square" :size="100" :src="user.avatar"></el-avatar>
            </div>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              账户名
            </template>
            {{ user.username }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-custom"></i>
              姓名
            </template>
            {{ user.realname }}
          </el-descriptions-item>
          <el-descriptions-item v-if="userGroup=='学生'">
            <template slot="label">
              <i class="el-icon-odometer"></i>
              年级
            </template>
            {{ student.grade }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-male"></i>
              <i class="el-icon-female"></i>
              性别
            </template>
            <el-tag size="small">{{ user.gender }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-message"></i>
              邮箱
            </template>
            {{ user.email }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              手机号码
            </template>
            {{ user.phone }}
          </el-descriptions-item>
          <el-descriptions-item v-if="userGroup=='学生'">
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              学院
            </template>
            {{ student.academy }}
          </el-descriptions-item>
          <el-descriptions-item v-if="userGroup=='学生'">
            <template slot="label">
              <i class="el-icon-office-building"></i>
              专业
            </template>
            {{ student.major }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              注册日期
            </template>
            {{ user.createTime }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>





      <el-dialog
  title="修改个人信息"
  :visible.sync="dialogFormVisible"
  :before-close="handleClose"
  width="60%"
  >
  <el-form :model="form" :rules="formRule" ref="form" label-width="150px">
      <div class="updateinfo">
  <div class="left">
    <el-upload
          class="avatar-uploader"
          action="#"
          accept=".jpg,.jpeg,.png,.JPG,.JPEG"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img style="width:150px;height:110px" :src="form.avatar"></img>
         
        </el-upload>


        <!-- <el-form-item label="头像" prop="avatar">
            <img style="width:150px;height:110px" :src="form.avatar"></img>
        </el-form-item> -->


          <el-form-item label="年级" prop="grade"  v-if="userGroup=='学生'">
            <el-input v-model="form.grade" disabled></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-switch
              v-model="form.gender"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="男"
              inactive-text="女"
              :active-value= "1"
               :inactive-value= "0"
            >
            </el-switch>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email"></el-input>
          </el-form-item>
          
  </div>
  <div class="right">
      <el-form-item label="账号" prop="username">
            <el-input v-model="form.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="realname">
            <el-input v-model="form.realname" disabled></el-input>
          </el-form-item>
          <el-form-item label="学院" prop="academy"  v-if="userGroup=='学生'">
            <el-input v-model="form.academy" disabled></el-input>
          </el-form-item>

          <el-form-item label="专业" prop="major"  v-if="userGroup=='学生'">
            <el-input v-model="form.major" disabled></el-input>
          </el-form-item>

          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
  </div>
  </div>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="cancle()">取 消</el-button>
    <el-button type="primary" @click="submit">提 交</el-button>
  </span>
</el-dialog>

    </div>
  </template>
  
  <script>
  //这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
    
    //import引入的组件需要注入到对象中才能使用
    components: {},
    data() {
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
    
      //这里存放数据
      return {
        userGroup:"",
        formLabelWidth: '120px',
        user:{
            
        }, 
        student:{
            
        }, 
        dialogFormVisible: false,
        form:{
        
        },
      formRule:{
        email: [
            { required: true, message: "请输入邮箱", trigger: "blur" },
            { validator: checkEmail, trigger: "blur" },
          ],

        phone:[
            { required: true, message: '请输入手机号', trigger: 'blur' },
            { min: 6, max: 11, message: '手机号不超过11个字符,请重新输入',trigger: "blur" },
            { validator: checkMobile, trigger: 'blur' }
          ],
    },
      }
      
    },
    //监听属性 类似于data概念
    computed: {},
    //监控data中的数据变化
    watch: {},
    //方法集合
    methods: {
      //上传头像
      handleAvatarSuccess(file) {
      //addImageFile  addImageUrl 自己在data中定义 file指的就是选择的文件对象
      this.addImageFile = file;
      this.form.avatar=require('../assets/images/avatar/'+this.addImageFile.name);
      console.log(this.addImageFile)
      // this.form.avatar = URL.createObjectURL(file.raw);
   
      console.log(this.form.avatar)
    },
      //上传头像
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 5MB!');
      }
      return  isLt2M;
    },

        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              this.getUserInfo();
              done();
            })
            .catch(_ => {
  
            });
        },
      cancle(){
        this.dialogFormVisible = false;
        this.getUserInfo();
      },
      submit(form){
        if(this.form.gender==0){
            this.form.gender='女'
        }else{
            this.form.gender='男'
        }
        
        this.$refs.form.validate((res1)=>{
          if(!res1){
					return;
				  }
          console.log("________________")
          console.log(this.form)
          this.$axios.post("/updateByUserName",this.form).then(resp=>{
          let result =resp.data;
          if(result.code==='0'){
              this.dialogFormVisible = false;
							return this.$message({
								message:result.msg,
								type:'success'
							});
						}
						this.$message.error(result.msg);
        })
      })

    },
      errorHandler() {
        return true
      },
      getUserInfo(){
        this.userGroup=sessionStorage.getItem('userGroup');
        this.$axios.get("/getUseInfo?username="+sessionStorage.getItem('username')).then(resp=>{
						let result = JSON.stringify(resp.data);
            result = eval("("+result+")");
            console.log("result");
            console.log(result);
            console.log("result.data");
            console.log(result.data);
            console.log("result.data.user");
            console.log(result.data.user);
            this.user = result.data.user;
            this.student = result.data.student;
            this.form = this.user;
            
            if(this.userGroup=="学生"){
            this.form.major=this.student.major;
            this.form.grade=this.student.grade;
            this.form.academy=this.student.academy;
            }
            

            console.log("赋值以后的user");
            console.log(this.user);
            
						if(result.code==='0'){
              console.log(result.code);
						}else{
              this.$message.error(result.msg);
            }
						
					})
      }
    },
    //生命周期 - 创建完成（可以访问当前this实例）
    created() {},
    //生命周期 - 挂载完成（可以访问DOM元素）
    mounted() {
      this.getUserInfo();
    },
    beforeCreate() {}, //生命周期 - 创建之前
    beforeMount() {}, //生命周期 - 挂载之前
    beforeUpdate() {}, //生命周期 - 更新之前
    updated() {}, //生命周期 - 更新之后
    beforeDestroy() {}, //生命周期 - 销毁之前
    destroyed() {}, //生命周期 - 销毁完成
    activated() {}, //如果页面有keep-alive缓存功能，这个函数会触发
  }
  </script>
  
  <style scoped>
  .img {
    width: 80px;
    height: 80px;
  }
  .updateinfo {
  height: 350px;
  overflow: auto;
}
.left {
  /* width: 330px; */
  float: left;
}
.right {
  overflow: hidden;
}
.avatar-uploader{
  display: flex;
  justify-content: center;
  margin-bottom: 5px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  align-items: center;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 130px;
  height: 130px;
  line-height: 130px;
  text-align: center;
}
.avatar {
  width: 130px;
  height: 130px;
  display: block;
}

  </style>
  