<!--  -->
<template>
 <div class=" container" style="margin-top:25px;margin-left:30px;">

  <el-row :gutter="10">
    <el-col :span="4">
      <div class="avater">  
        <el-avatar shape="square" :size="100" :src="user.avatar"></el-avatar>
      </div>
    </el-col>
  </el-row>

  <el-row :gutter="10">
    <el-col :span="4"><div >真实姓名:</div></el-col>
    <el-col :span="4"><div>{{ user.realname }}</div></el-col>
  </el-row>

    
  <el-row :gutter="10">
    <el-col :span="4"><div >性别:</div></el-col>
    <el-col :span="4"><div >{{ user.gender }}</div></el-col>
  </el-row>

    
  <el-row :gutter="10">
    <el-col :span="4"><div>邮箱:</div></el-col>
    <el-col :span="4"><div>{{ user.email }}</div></el-col>
    <el-col :span="4"><div>邮箱状态:</div></el-col>
    <el-col :span="4"><div v-if="user.emailState=='0'">未认证</div></el-col>
  </el-row>
  

  <el-row :gutter="10">
    <el-col :span="4"><div>手机号码:</div></el-col>
    <el-col :span="4"><div>{{ user.phone }}</div></el-col>
    <el-col :span="4"><div>手机状态:</div></el-col>

    <el-col :span="4"><div v-if="user.phoneState=='0'">未认证</div></el-col>
  </el-row>

  <el-row :gutter="10">
    <el-col :span="4"><div>上次登陆时间:</div></el-col>
    <el-col :span="4"><div>{{ user.loginTime }}</div></el-col>
  </el-row>

<!-- Form -->
<el-button  @click="dialogFormVisible = true">修改个人资料</el-button>

<el-dialog title="收货地址" :visible.sync="dialogFormVisible">
  <el-form :model="form" :rules="formRule" ref="form" >
    <el-form-item label="用户名" :label-width="formLabelWidth">
      <el-input v-model="form.username" :disabled="true" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="性别" :label-width="formLabelWidth">
    <el-radio v-model="form.gender" label="男">男</el-radio>
  <el-radio v-model="form.gender" label="女">女</el-radio>
    </el-form-item>
    <el-form-item label="手机号" prop="phone" :label-width="formLabelWidth">
      <el-input v-model="form.phone"  autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">
      <el-input v-model="form.email" autocomplete="off"></el-input>
    </el-form-item>

  </el-form>


  <div slot="footer" class="dialog-footer">
    <el-button @click="cancle()">取 消</el-button>
    <el-button type="primary" @click="submit(form)">确 定</el-button>
  </div>
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
        formLabelWidth: '120px',
        user:{
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
      cancle(){
        this.dialogFormVisible = false;
        this.getUserInfo();
      },
      submit(form){
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
            this.form = this.user;
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
.el-row {
    margin-top: 10px;
    box-sizing: border-box;
}
.container{
  color: black;
}
</style>