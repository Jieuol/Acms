<!--  -->
<template>
  <div class="main">
    <el-form label-position="right" :model="query" ref="query"  class="form p_4" label-width="120">
      <el-row>
        <el-col :xs="24" :sm="24" :lg="8">
          <el-form-item label="赛项名称">
            <el-input v-model="query.contestName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="8">
          <el-form-item label="赛项类型">
            <el-input v-model="query.contestType"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="8">
          <el-form-item label="赛项日期">
            <el-input v-model="query.contestDate"></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="10" :lg="8">
          <el-form-item>
            <el-button type="primary" @click="getParticipantListByPageAndUserId('query')">查询</el-button>
            <el-button @click="reset()" style="margin-right: 74px;">重置</el-button>
          </el-form-item>
        </el-col>
            </el-row>
        </el-form>
   <el-table  ref="multipleTable"
   :data="contestInfo"
   tooltip-effect="dark"
   style="width: 100%">
     <el-table-column fixed tooltip-effect="dark" width="55">
     </el-table-column>
     <el-table-column prop="contestName" label="赛项名称" min-width="200">
     </el-table-column>
     <el-table-column prop="contestType" label="赛项类型" min-width="200">
     </el-table-column>
     <el-table-column prop="examineState" label="审核状态" min-width="200">
     </el-table-column>
     <el-table-column sortable prop="contestDate" label="赛项日期" min-width="200">
     </el-table-column>

     <el-table-column sortable prop="updateTime" label="更新时间" min-width="200">
     </el-table-column> -->
     <el-table-column fixed="right" label="操作" min-width="120">
       <template slot-scope="scope">
         <el-button class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" size="small" @click="detail(scope.row)">
           <span>竞赛详情</span>
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
       :page-sizes="[3,4,5]"
       :page-size = "query.pageSize" 
       layout="total, sizes, prev, pager, next, jumper"
       :total="totalRecords"
     ></el-pagination>
   </div>
   <!-- /分页器 -->
   
   <!-- 遮罩1 -->
      <el-dialog
      title="提示"
      :visible.sync="deletecenterDialogVisible"
      width="30%"
      :before-close="handleCloseDelete">
      <span>确认是否取消申报</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deletecenterDialogVisible = false">返 回</el-button>
        <el-button type="primary" @click="deleteparticipate()">确 认</el-button>
      </span>
    </el-dialog>
    <!-- 遮罩2 -->
 <el-dialog title="详细信息" :visible.sync="centerDialogVisible" width="1000px">
 <div class=" container" style="margin-top:25px;margin-left:30px;">
   <el-row :gutter="10">
     <el-col :span="4"><div class="title">竞赛名称: {{ form.contestName }}</div></el-col>
   </el-row>

     
   <el-row :gutter="10">
     <el-col :span="4"><div class="text">竞赛类别: {{ form.contestType }}</div></el-col>
   </el-row>
   
   <el-row :gutter="20">
     <el-col :span="24"><div class="introduce">竞赛日期: {{ form.contestDate }}</div></el-col>
   </el-row>

   <el-row :gutter="20">
     <el-col :span="24"><div class="introduce">竞赛介绍: {{ form.contestIntroduction }}</div></el-col>
   </el-row>

   <el-row :gutter="20">
     <el-col :span="24"><div class="introduce">参与人数: {{ form.participantsNumber }}</div></el-col>
   </el-row>

   <el-row :gutter="20">
     <el-col :span="24"><div class="introduce">竞赛规则: {{ form.contestRules }}</div></el-col>
   </el-row>

   <el-row :gutter="20">
     <el-col :span="24"><div class="introduce">审核状态: {{ form.examineState }}</div></el-col>
   </el-row>

   <el-row :gutter="10" v-if="form.examineState=='已通过'">
    <el-col :span="10">
       <el-input placeholder="请输入审核回复" type="textarea":rows="2" style="width: 80%" v-model="form.examineReply" autocomplete="off"></el-input>
       <el-button class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" type="danger" size="small" @click="updateDeclaration('fail')">
        <span>不予通过</span>
       </el-button>
    </el-col>
   </el-row>

   <el-row :gutter="10" v-if="form.examineState=='未通过'">
    <el-col :span="10">
       <el-input placeholder="请输入审核回复" type="textarea":rows="2" style="width: 80%" v-model="form.examineReply" autocomplete="off"></el-input>

       <el-button class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" type="success" size="small" @click="updateDeclaration('pass')">
        <span>审核通过</span>
       </el-button>
    </el-col>
   </el-row>

   <el-row :gutter="10" v-if="form.examineState=='未审核'">
    <el-col :span="10">
       <el-input placeholder="请输入审核回复" type="textarea":rows="2" style="width: 80%" v-model="form.examineReply" autocomplete="off"></el-input>
       <el-button class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" type="danger" size="small" @click="updateDeclaration('fail')">
        <span>不予通过</span>
       </el-button>

       <el-button class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" type="success" size="small" @click="updateDeclaration('pass')">
        <span>审核通过</span>
       </el-button>
      </el-col>
   </el-row>
 </div>
 <div slot="footer" class="dialog-footer">
   <el-button @click="centerDialogVisible = false">返 回</el-button>

 </div>

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
       //这里存放数据
       return {
         form:{
         },
         deletecenterDialogVisible:false,
         centerDialogVisible:false,
         currentPage:1,
         //分页
         totalRecords: 0,
          //总条数，总共有多少条数据,
          pageInfo:{
           pageSize: 3,
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
        pageSize: 3,
        pageIndex: 0,
        contestName:"",
        contestDate:"",
        contestType:"",
       },
       contestInformaion:{
        
       },
       // 数据
       contestInfo: [],
       multipleSelection: [],
       contestDeclaration:{},
       updateForm:{
          
        },
       }
   
     },
     //监听属性 类似于data概念
     computed: {},
     //监控data中的数据变化
     watch: {},
     //方法集合
     methods: {
      updateDeclaration(index){
        this.contestInformaion.contestName=this.form.contestName;
        this.contestInformaion.contestType=this.form.contestType;
        this.contestInformaion.contestDate=this.form.contestDate;
        this.contestInformaion.contestIntroduction=this.form.contestIntroduction;
        this.contestInformaion.contestDeclarationId=this.form.contestDeclarationId;
        this.contestInformaion.contestRules=this.form.contestRules;
        this.contestInformaion.createTime=this.form.createTime;
        this.contestInformaion.participantsNumber=this.form.participantsNumber;
        
        if(index=='pass'){
          console.log("pass_declaration");
          console.log(this.form.contestDeclarationId);
          this.updateForm.contestDeclarationId=this.form.contestDeclarationId;
          this.updateForm.examineReply=this.form.examineReply;
          this.updateForm.examineState="已通过";
          this.$axios.post("/updateDeclaration", this.updateForm).then(resp=>{
          let result = resp.data;
          if(result.code==='0'){
            this.centerDialogVisible=false;
            this.getDeclarationListByPage();
            this.$message({
              message:result.msg,
              type:'success'
            });
          }
          else{
           return this.$message.error(result.msg);
          }
        })
        this.$axios.post("/insertContestInfo",this.contestInformaion).then(resp=>{
          let result = resp.data;
          if(result.code==='0'){

            return this.$message({
              message:result.msg,
              type:'success'
            });
          }
          this.$message.error(result.msg);
        });
        }
        if(index=='fail'){
        console.log("fail_declaration");
        console.log(this.form.contestDeclarationId);
        this.updateForm.contestDeclarationId=this.form.contestDeclarationId;
        this.updateForm.examineState="未通过";
        this.$axios.post("/updateDeclaration", this.updateForm).then(resp=>{
          let result = resp.data;
          if(result.code==='0'){
            this.getDeclarationListByPage();
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
        this.$axios.post("/deleteContestInfo",this.contestInformaion).then(resp=>{
          let result = resp.data;
          if(result.code==='0'){
            return this.$message({
              message:result.msg,
              type:'success'
            });
          }
          this.$message.error(result.msg);
        });
      }


      },
      deleteparticipate(){
        console.log(this.contestDeclaration);
        this.$axios.post("/deleteDeclaration",this.contestDeclaration).then(resp=>{
          
         let result =resp.data;
         if(result.code==='0'){
            this.deletecenterDialogVisible = false;
            this.getDeclarationListByPage()
             return this.$message({
               message:result.msg,
               type:'success'
             });
           }
           this.$message.error(result.msg);
           this.deletecenterDialogVisible = false;
           this.getDeclarationListByPage()
         })
      },
      canclecontestDeclaration(row){
        this.deletecenterDialogVisible = true;
        this.contestDeclaration.contestName=row.contestName;
        this.contestDeclaration.contestDate=row.contestDate;
        this.contestDeclaration.contestType=row.contestType;
        this.contestDeclaration.contestDeclarationId=row.contestDeclarationId


      },
      reset(){
          this.query.contestName="";
          this.query.contestDate="";
          this.query.contestType="";
          this. getDeclarationListByPage();
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
         this.form=row;
         console.log("form");
         console.log(this.form);
         this.centerDialogVisible=true;
       },
       //分页器 第X页
       handleCurrentChange(newPage) {
       newPage=newPage-1
       console.log("newPage:")
       console.log(newPage)
       this.query.pageIndex = newPage*this.query.pageSize
       // this.pageInfo.pageIndex = newPage+this.pageInfo.pageSize
       console.log("after：")
       this.getDeclarationListByPage()
      },
      //选择每页信息个数
     handleSizeChange(val) {
       this.query.pageSize = val
       this.getDeclarationListByPage()
       },
       getDeclarationListByPage(){
       console.log("this.query");
       console.log(this.query);

       this.$axios({
         url: "/getDeclarationListByPage",
         method: 'GET',
         params: this.query
       }).then(resp=>{

         let result = JSON.stringify(resp.data);
         result = eval("("+result+")");
         this.contestInfo=result.data.contestDeclaration;
         
         this.totalRecords=result.data.totalRecords;
       
         })
     
       },
      getDeclarationListByPageAndUserId(){
      this.query.applicantId=sessionStorage.getItem("userId")
       console.log("this.query");
       console.log(this.query);

       this.$axios({
         url: "/getDeclarationListByPageAndUserId",
         method: 'GET',
         params: this.query
       }).then(resp=>{

         let result = JSON.stringify(resp.data);
         result = eval("("+result+")");
         this.contestInfo=result.data.contestDeclaration;
         
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
   },
     //生命周期 - 创建完成（可以访问当前this实例）
     created() {},
     //生命周期 - 挂载完成（可以访问DOM元素）
     mounted() {
       this.getDeclarationListByPage();
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