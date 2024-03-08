<!--  -->
<template>
  <div class="main">
   <el-form label-position="right" :model="query" ref="query"  class="form p_4" label-width="120">
     <el-row>
       <el-col :xs="24" :sm="24" :lg="8">
         <el-form-item label="公告名称">
           <el-input v-model="query.noticeName"></el-input>
         </el-form-item>
       </el-col>

       <el-col :xs="24" :sm="24" :lg="8">
         <el-form-item label="发布日期">
           <el-input v-model="query.updateTime"></el-input>
         </el-form-item>
       </el-col>

       <el-col :xs="24" :sm="10" :lg="8">
         <el-form-item>
           <el-button type="primary" @click="getNoticeByUserGroup('query')">查询</el-button>
           <el-button @click="reset()" style="margin-right: 74px;">重置</el-button>
         </el-form-item>
       </el-col>
     </el-row>
   </el-form>
   <el-button type="success" v-if="query.userGroup=='管理员'" @click="add()" style="margin-left: 900px;">添加新公告</el-button>
   <el-table  ref="multipleTable"
   :data="noticeInfo"
   tooltip-effect="dark"
   style="width: 100%">
     <el-table-column fixed tooltip-effect="dark" width="55">
     </el-table-column>
     <el-table-column prop="noticeName" label="公告名称" min-width="200">
     </el-table-column>
     <el-table-column v-if="query.userGroup=='管理员'" prop="userGroup" label="通知对象" min-width="200">
     </el-table-column>
     <el-table-column prop="updateTime" sortable label="发布日期" min-width="200">
     </el-table-column>
     <el-table-column fixed="right" label="操作" min-width="120">
       <template slot-scope="scope">
         <el-button v-if="query.userGroup=='学生'||query.userGroup=='老师' " 
         class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" size="small" @click="detail(scope.row)">
           <span>详情</span>
         </el-button>
         <el-button type="warning" v-if="query.userGroup=='管理员' && scope.row.state==1 " 
         class="el-button el-button--small is-plain el-button--default" style="margin: 5px !important;" 
         size="small" @click="withdraw(scope.row)">
           <span>撤回</span>
         </el-button>

         <el-button type="danger" v-if="query.userGroup=='管理员'  && scope.row.state==0 " 
         class="el-button el-button--small is-plain el-button--default" style="margin: 5px !important;" 
         size="small" @click="deleteBy(scope.row)">
           <span>删除</span>
         </el-button>

         <el-button type="success" v-if="query.userGroup=='管理员'  && scope.row.state==0 " 
         class="el-button el-button--small is-plain el-button--default" style="margin: 5px !important;" 
         size="small" @click="edit(scope.row)">
           <span>编辑</span>
         </el-button>
         <el-button type="success" v-if="query.userGroup=='管理员'  && scope.row.state==0" 
         class="el-button el-button--small is-plain el-button--default" style="margin: 5px !important;" 
         size="small" @click="publish(scope.row)">
           <span>发布</span>
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
   
    <!-- 编辑遮罩 -->
  <el-dialog title="编辑通知" :visible.sync="centerDialogVisible" :close-on-click-modal ="false" width="1000px">

  <div class=" container" style="margin-top:25px;margin-left:30px;">
 <el-form :model="editForm">
    <el-form-item label="通知名称" :label-width="formLabelWidth">
      <el-input v-model="editForm.noticeName" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="通知内容" :label-width="formLabelWidth">
      <el-input type="textarea":rows="2" v-model="editForm.noticeInformation" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
 </div>
 <div slot="footer" class="dialog-footer">
  <el-button @click="editNow('editForm')">确认修改</el-button>
   <el-button @click="centerDialogVisible = false">取 消</el-button>
 </div>
</el-dialog>
 
    <!-- 详情遮罩 -->
  <el-dialog title="通知详情" :visible.sync="deatilcenterDialogVisible" :close-on-click-modal ="false" width="1000px">
  <div class=" container" style="margin-top:25px;margin-left:30px;">

    <el-row :gutter="10">
      <el-col :span="24"><div class="title"> {{ detailForm.noticeName }}</div></el-col>
    </el-row>

    <el-row :gutter="10">
      <el-col :span="24"><div class="text"> {{ detailForm.noticeInformation }}</div></el-col>
    </el-row>

</div>
<div slot="footer" class="dialog-footer">
 <el-button @click="deatilcenterDialogVisible = false">返回 </el-button>
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
        
         editForm:{
         },
         detailForm:{

         },
         centerDialogVisible:false,
         deatilcenterDialogVisible:false,
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
         noticeName:"",
         updateTime:"",
         userGroup:sessionStorage.getItem('userGroup'),
       },
       // 数据
       noticeInfo: [],
       multipleSelection: [],
       participantInfo:{},
       }
       
     },
     //监听属性 类似于data概念
     computed: {},
     //监控data中的数据变化
     watch: {},
     //方法集合
     methods: {
       reset(){
         this.query.noticeName="";
         this.query.updateTime="";
         this. getNoticeByUserGroup();
       },
       participate(row){
         this.participantInfo.contestName=row.contestName;
         this.participantInfo.contestDate=row.contestDate;
         this.participantInfo.contestType=row.contestType;
         this.participantInfo.noticeInformationId=row.noticeInformationId;
         this.participantInfo.applicantId=sessionStorage.getItem("userId");
         this.participantInfo.applicantRealname=sessionStorage.getItem("realname");
         console.log("参与人信息");
         console.log(this.participantInfo);
         this.$axios.post("/insertParticipant",this.participantInfo).then(resp=>{
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
         
       },
        //通知详情
      detail(row){
         console.log("row");
         console.log(row);
         this.detailForm=row;
         console.log("form");
         console.log(this.detailForm);
         this.deatilcenterDialogVisible=true;
       },
       //修改通知
       edit(row){
         console.log("row");
         console.log(row);
         this.editForm=row;
         console.log("form");
         console.log(this.form);
         this.centerDialogVisible=true;
       },
       editNow(form){

       },
       //分页器 第X页
       handleCurrentChange(newPage) {
       newPage=newPage-1
       console.log("newPage:")
       console.log(newPage)
       this.query.pageIndex = newPage*this.query.pageSize
       // this.pageInfo.pageIndex = newPage+this.pageInfo.pageSize
       console.log("after：")
       this.getNoticeByUserGroup()
      },
      //选择每页信息个数
     handleSizeChange(val) {
       this.query.pageSize = val
       this.getNoticeByUserGroup()
       },

     getNoticeByUserGroup(form){
       console.log("this.query");
       console.log(this.query);
       this.$axios({
         url: "/getNoticeByUserGroup",
         method: 'GET',
         params: this.query
       }).then(resp=>{

         let result = JSON.stringify(resp.data);
         result = eval("("+result+")");
         this.noticeInfo=result.data.noticeInfo
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
       this.getNoticeByUserGroup();
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
   text-align: center;
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