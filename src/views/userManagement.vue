<!--  -->
<template>
	<div class="main">
	  <el-form label-position="right" :model="query" ref="query"  class="form p_4" label-width="120">
		
		
		<el-row>
			<el-col :xs="24" :sm="24" :lg="8">
			<el-form-item label="用户名">
			<el-input v-model="query.username"></el-input>
			</el-form-item>
			</el-col>

		  <el-col :xs="24" :sm="24" :lg="8">
			<el-form-item label="角色">
            <el-select v-model="query.userGroup" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
		  </el-col>
  
		  <el-col :xs="24" :sm="10" :lg="8">
			<el-form-item>
			  <el-button type="primary" @click="queryInfo('query')">查询</el-button>
			  <el-button @click="reset()" style="margin-right: 74px;">重置</el-button>
			</el-form-item>
		  </el-col>
			  </el-row>
		  </el-form>
	<!-- 添加新用户按钮 -->
		  <el-button type="success" style="float: left;" @click="AddcenterDialogVisible=true">添加新用户</el-button>
  	<!-- 导出按钮 -->
	  <download-excel class = "export-excel-wrapper":data = "userInfo"
		 :fields = "json_fields" name = "用户信息.xls">
			  <el-button style="float: right;" round type="success" icon="download" > 导出 </el-button>
	  </download-excel>
	 <el-table  ref="multipleTable"
	 :data="userInfo"
	 tooltip-effect="dark"
	 style="width: 100%"
	 max-height="450">
	   <el-table-column fixed tooltip-effect="dark" width="55">
	   </el-table-column>
	   <el-table-column prop="username" label="用户名" min-width="100">
	   </el-table-column>
	   <el-table-column prop="realname" label="姓名" min-width="50">
	   </el-table-column>
	   <el-table-column prop="state" label="状态" min-width="50">
		<template slot-scope="scope">
		<span v-if="scope.row.state==1">正常</span>	
		<span v-if="scope.row.state==0">已禁用</span>	
		</template>
	   </el-table-column>
	   <el-table-column prop="userGroup" label="角色" min-width="50">
	  </el-table-column>
	  <el-table-column prop="avatar" label="头像" min-width="50">
		<template slot-scope="scope">
			<el-avatar shape="square" :size="100" :src="scope.row.avatar"></el-avatar>
		</template>
		
	  </el-table-column>
  
	   <el-table-column fixed="right" label="操作" min-width="120">
		 <template slot-scope="scope">
		   <el-button v-if="scope.row.state==1" type="warning" class="el-button el-button--small is-plain el-button--default" 
		   style="margin: 5px !important;" size="small" @click="forbid(scope.row)">
			 <span>禁用</span>
		   </el-button>
		   <el-button v-if="scope.row.state==1"  class="el-button el-button--small is-plain el-button--default" 
		   style="margin: 5px !important;" size="small" @click="detail(scope.row)">
			 <span>详细信息</span>
		   </el-button>
		   <el-button v-if="scope.row.state==0" type="success" class="el-button el-button--small is-plain el-button--default" 
		   style="margin: 5px !important;" size="small" @click="open(scope.row)">
			 <span>启用</span>
		   </el-button>
		 </template>
	   </el-table-column>
	 </el-table>
	 
	   <!-- 分页器 -->
	   <div class="block">
		<el-pagination
		 @size-change="handleSizeChange"
		 @current-change="handleCurrentChange"
		 :current-page="currentPage" 
		 :page-sizes="[50,100,150]"
		 :page-size = "query.pageSize" 
		 layout="total, sizes, prev, pager, next, jumper"
		 :total="totalRecords"
	   ></el-pagination>
	 </div>
	 <!-- /分页器 -->
	 
	 <!-- 详细信息遮罩 -->
	 <el-dialog title="用户详细信息" :visible.sync="centerDialogVisible" width="1000px">
   <div class=" container" style="margin-top:25px;margin-left:30px;">

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


	</div>
   <div slot="footer" class="dialog-footer">
	 <el-button @click="back()">返 回</el-button>
   </div>
  
     </el-dialog>

    <!-- 创建用户遮罩 -->
	<el-dialog title="创建用户" :visible.sync="AddcenterDialogVisible" :close-on-click-modal ="false" width="1000px" 
  :before-close="handleCloseAdd">
  <el-form :model="form" :rules="addformRule" ref="form" label-width="150px">
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
            <el-input v-model="form.grade"></el-input>
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
            <el-input v-model="form.username" ></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="realname">
            <el-input v-model="form.realname" ></el-input>
          </el-form-item>

		  <el-form-item label="角色">
            <el-select v-model="form.userGroup" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="学院" prop="academy"  v-if="userGroup=='学生'">
            <el-input v-model="form.academy" ></el-input>
          </el-form-item>

          <el-form-item label="专业" prop="major"  v-if="userGroup=='学生'">
            <el-input v-model="form.major" ></el-input>
          </el-form-item>

          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
  </div>
  </div>
  </el-form>
    <div slot="footer" class="dialog-footer">
    <el-button @click="add('form')">确认添加</el-button>
    <el-button @click="resetForm()">取 消</el-button>
    </div>
  </el-dialog>

	 <!-- 修改信息 -->
  <el-dialog
  title="修改信息"
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
            <el-input v-model="form.grade"></el-input>
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
            <el-input v-model="form.username" ></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="realname">
            <el-input v-model="form.realname" ></el-input>
          </el-form-item>

		  <el-form-item label="角色">
            <el-select v-model="form.userGroup" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学院" prop="academy"  v-if="userGroup=='学生'">
            <el-input v-model="form.academy" ></el-input>
          </el-form-item>

          <el-form-item label="专业" prop="major"  v-if="userGroup=='学生'">
            <el-input v-model="form.major" ></el-input>
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
   import mixin from "@/mixins/page.js";
  
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
			student:{
            
			}, 
			formLabelWidth: '120px',
			user:{

			},
			addForm:{
         	},
		  json_fields: {
		  用户id: "userId",    //常规字段
		  用户名:"username",
		  姓名:"realname",
		  手机号: "phone", 
		  邮箱号: "email",
		  角色: "userGroup",

		},
		dialogFormVisible: false,
		AddcenterDialogVisible:false,
        form:{
        
        },
		addformRule:{
        email: [
            { required: false, message: "请输入邮箱", trigger: "blur" },
            { validator: checkEmail, trigger: "blur" },
          ],

        phone:[
            { required: false, message: '请输入手机号', trigger: 'blur' },
            { min: 6, max: 11, message: '手机号不超过11个字符,请重新输入',trigger: "blur" },
            { validator: checkMobile, trigger: 'blur' }
          ],
		  username:[
            { required: true, message: '请输入用户账号', trigger: 'blur' },
            { min: 4, max: 11, message: '用户账号因为6-20个字符,请重新输入',trigger: "blur" },
          ],
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
		  username:[
            { required: true, message: '请输入用户账号', trigger: 'blur' },
            { min: 4, max: 11, message: '用户账号因为4-11个字符,请重新输入',trigger: "blur" },
          ],
    	},

		  userGroup:sessionStorage.getItem("userGroup"),
		  options: [{
			value: '老师',
			label: '老师'
		  }, {
			value: '学生',
			label: '学生'
		  },
		  ],
		  //导出excel设置
		  json_meta: [
		  [
			{
			  " key ": " charset ",
			  " value ": " utf- 8 "
			}
		  ]
		],
		   deletecenterDialogVisible:false,
		   centerDialogVisible:false,
		   currentPage:1,
		   //分页
		   totalRecords: 0,
			//总条数，总共有多少条数据,
			pageInfo:{
			 pageSize: 50,
			 pageIndex: 0,
			},
		   // 弹框
		 showModal: false,
		 // 获取数据地址
		 url_get_list: "",
		 url_del: "",
		 
		 // 字段ID
		 field: "",
  
		 // 查询
		 query: {
		  pageSize: 50,
		  pageIndex: 0,
		  userGroup:'',
		  username:'',
		 },
		 // 数据
		 userInfo: [],
		 multipleSelection: [],
		 participantInfo:{},
		 updateForm:{},
		 
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
			this.getUserInformation();
              done();
            })
            .catch(_ => {
  
            });
        },

		forbid(row){
		   console.log("row");
		   console.log(row);
		   row.state=0
		   console.log("row");
		   console.log(row);
		   this.$axios.post("/updateUserInformation",row).then(resp=>{
			
			let resutlt  = resp.data;
			if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
			if (resutlt.code==='0'){
				return this.$message({
					type:"success",
					message:resutlt.msg
				})
			}
			return this.$message.error(resutlt.msg)
		   })

		},
		open(row){
		   console.log("row");
		   console.log(row);
		   row.state=1
		   console.log("row");
		   console.log(row);
		   this.$axios.post("/updateUserInformation",row).then(resp=>{
			let resutlt  = resp.data;
			if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
			if (resutlt.code==='0'){
				return this.$message({
					type:"success",
					message:resutlt.msg
				})
			}
			return this.$message.error(resutlt.msg)
		   })

		},
		//导出Excel
		exportExcelHeader() {
		this.json_fields = {};
		this.tableFilterData.forEach(e => {
		this.json_fields[e.label] = e.prop;
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
		  if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
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

	resetForm(){
        this.form={};
        this.AddcenterDialogVisible=false;
      },
	  add(form){
        this.$axios.post("/addUser",this.form).then(resp=>{
          console.log("form")
          console.log(this.form)
          let result = resp.data;
          if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
          if(result.code=='0'){
            this.AddcenterDialogVisible=false;
            this.form={};
			this.getUserInformation();
            return this.$message({
              message:result.msg,
              type:'success'
            });
          }
		  if(result.code=='203'){
            this.AddcenterDialogVisible=false;
            this.form={};
            return this.$message({
              message:result.msg,
              type:'warning'
            });
          }
          this.$message.error(result.msg);
        })
       },
		back(){
		  this.centerDialogVisible = false;
		  this.form.contestResult="";
		},
		updateParticipant(index){
		  if(index=='pass'){
			console.log("pass_declaration");
			console.log(this.form.contestDeclarationId);
			this.updateForm.userInformationId=this.form.userInformationId;
			this.updateForm.contestParticipantId=this.form.contestParticipantId;
			this.updateForm.applicantId=this.form.applicantId;
			this.updateForm.examineReply=this.form.examineReply;
			this.updateForm.examineState="已通过";
			this.$axios.post("/updateParticipant", this.updateForm).then(resp=>{
			let result = resp.data;
			if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
			if(result.code==='0'){
			  this.centerDialogVisible=false;
			  this.getUserInformation();
			  this.$message({
				message:result.msg,
				type:'success'
			  });
			}
			else{
			 return this.$message.error(result.msg);
			}
		  })
		  }
		  if(index=='fail'){
		  console.log("fail_declaration");
		  this.updateForm.userInformationId=this.form.userInformationId;
			this.updateForm.contestParticipantId=this.form.contestParticipantId;
			this.updateForm.applicantId=this.form.applicantId;
			this.updateForm.examineReply=this.form.examineReply;
		  this.updateForm.examineState="未通过";
		  this.$axios.post("/updateParticipant", this.updateForm).then(resp=>{
			let result = resp.data;
			if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
			if(result.code==='0'){
			  this.getUserInformation();
			  this.centerDialogVisible=false;
			  this.$message({
				message:result.msg,
				type:'success'
			  });
			}
			else{
			  return this.$message.error(result.msg);
			}
		  })
		}
		},

		reset(){
			this.query.userGroup="";
			this.query.username="";
			this. getUserInformation();
		  },
		  handleCloseDelete(done) {
		  this.$confirm('确认关闭？')
			.then(_ => {
			  
			  done();
			})
			.catch(_ => {
  
			});
		},
  
		 //查看详细信息
		 detail(row){
		   console.log("row");
		   console.log(row);
		   this.user=row;
		   console.log("form");
		   console.log(this.form);
		   this.centerDialogVisible=true;
		   this.form = row;
		 },
		 //分页器 第X页
		 handleCurrentChange(newPage) {
		 newPage=newPage-1
		 console.log("newPage:")
		 console.log(newPage)
		 this.query.pageIndex = newPage*this.query.pageSize
		 // this.pageInfo.pageIndex = newPage+this.pageInfo.pageSize
		 console.log("after：")
		 this.getUserInformation()
		},
		//选择每页信息个数
	   handleSizeChange(val) {
		 this.query.pageSize = val
		 this.getUserInformation()
		 },
	   getUserInformation(){
		 console.log("this.query");
		 console.log(this.query);
		  this.$axios({
		   url: "/getUserInformation",
		   method: 'GET',
		   params: this.query
		 }).then(resp=>{
  
		   let result = JSON.stringify(resp.data);
		   result = eval("("+result+")");
		   if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
		   this.userInfo=result.data.userInformation;
		   this.totalRecords=result.data.totalRecords;
			
		   })
		
		},
		 toggleSelection(rows) {
		   if (rows) {
			 rows.forEach(row => {
			   this.$refs.multipleTable.toggleRowSelection(row);
			 });
		   } else {
			 this.$refs.multipleTable.clearSelection();
		   }
	   },
	   queryInfo(){
		this.query.pageIndex=0;
		this.query.pageSize=50;
		this.getUserInformation();
	   }
	 },
	   //生命周期 - 创建完成（可以访问当前this实例）
	   created() {},
	   //生命周期 - 挂载完成（可以访问DOM元素）
	   mounted() {
		 this.getUserInformation();
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

   .el-dialog__body {
	 padding: 50px 20px;
	 color: #606266;
	 font-size: 14px;
	 word-break: break-all;
	 margin-bottom: 10px;
  }
   .container{
	 margin-bottom: 10px;
   }
   .text{
	 margin-bottom: 10px;
	 color: rgb(0, 0, 0);
   }
   .introduce{
	 margin-bottom: 10px;
	 color: rgb(3, 88, 172);
   }
   .title{
	 margin-bottom: 10px;
	 color: red;
   }
   .form.p_4 {
	 padding: 1rem;
   }
  
   .form .el-input {
	 width: initial;
   }
  
   .mt {
	 margin-top: 1rem;
   }
  
   .text_center {
	 text-align: center;
   }
  
   .float-right {
	 float: right;
   }
   .left {
  /* width: 330px; */
  float: left;
}
.right {
  overflow: hidden;
}
  
   .modal_wrap{
	 width: 100vw;
	 height: 100vh;
	 position: fixed;
	 top: 0;
	 left: 0;
	 background: rgba(0,0,0,0.5);
	 z-index: 9999999999;
   }
   .modal_wrap .modal_box{
	 width: 400px;
	 height: 200px;
	 /* background: url("../../assets/modal_bg.jpg") no-repeat center; */
	 background-size: cover;
	 position: absolute;
	 top: 50%;
	 left: 50%;
	 margin-left: -200px;
	 margin-top: -100px;
	 border-radius: 10px;
	 }
   .modal_wrap .modal_box .modal_box_close{
	 font-size: 20px;
	 position: absolute;
	 top: 10px;
	 right: 10px;
	 cursor: pointer;
	 }
   .modal_wrap .modal_box .modal_box_title{
	 text-align: center;
	 font-size: 18px;
	 margin: 16px auto;
	 color: #fff;
	 border-bottom: 1px solid rgba(117, 116, 116,0.5);
	 padding-bottom: 16px;
	 width: 356px;
	 }
   .modal_wrap .modal_box .modal_box_text{
	   text-align: center;
	 font-size: 15px;
	 color: #fff;
	 margin-top: 25px;
	 }
   .modal_wrap .modal_box .btn_box{
	 display: flex;
	 flex-direction: row;
	 justify-content: center;
	 margin-top: 42px;
	 }
	   .modal_wrap .modal_box .btn_box span{
		 display: inline-block;
		 width: 80px;
		 height: 30px;
		 line-height: 30px;
		 text-align: center;
		 border: 1px solid #ccc;
		 font-size: 14px;
		 cursor: pointer;
		 color: #fff;
	  }
   .modal_wrap .modal_box .btn_box span:nth-child(2){
	 background: #409EFF;
	 color: #fff;
	 border-color: #409EFF;
	 margin-left: 15px;
   }
   </style>