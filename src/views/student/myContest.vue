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
            <el-select v-model="query.contestType" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="8">
          <el-form-item label="赛项日期">
            <el-date-picker
            v-model="query.contestDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
            <!-- <el-input v-model="query.contestDate"></el-input> -->
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
  
   <el-table  ref="multipleTable"
   :data="contestInfo"
   tooltip-effect="dark"
   style="width: 100%"
   max-height="450"
    >
   <el-table-column
      type="selection"
      width="55"
>
    </el-table-column>
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
           <span>报名详情</span>
         </el-button>
         <el-button v-if="scope.row.examineState=='未审核'" class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" type="danger" size="small" @click="cancleparticipate(scope.row)">
           <span>取消报名</span>
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
      <span>确认是否取消报名</span>
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
     <el-col :span="24"><div class="introduce">审核状态: {{ form.examineState }}</div></el-col>
   </el-row>

   <el-row :gutter="10">
     <el-col :span="4"><div class="title">审核回复: {{ form.examineReply }}</div></el-col>
   </el-row>
 </div>
 <div slot="footer" class="dialog-footer">
   <el-button @click="centerDialogVisible = false">取 消</el-button>
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
        options: [{
          value: '院级',
          label: '院级'
        }, {
          value: '校级',
          label: '校级'
        },
        {
          value: '国家级',
          label: '国家级'
        },
        ],
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
        applicantId:sessionStorage.getItem("userId"),
        contestName:"",
        contestDate:"",
        contestType:"",
       },
       // 数据
       contestInfo: [],
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
    //多选
      handleSelectionChange(list) {
      console.log(list);
      this.checkedList = list
    },
    getRowKeys(row) {
      return row.contestParticipantId
    },
    //批量操作
    handleOption(){
      
    },
      deleteparticipate(){
        console.log(this.participantInfo);
        this.$axios.post("/deleteParticipant",this.participantInfo).then(resp=>{
         let result =resp.data;
         if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
         if(result.code==='0'){
            this.deletecenterDialogVisible = false;
            this.getParticipantListByPageAndUserId()
             return this.$message({
               message:result.msg,
               type:'success'
             });
           }
           this.$message.error(result.msg);
           this.deletecenterDialogVisible = false;
           this.getParticipantListByPageAndUserId()
         })
      },
      cancleparticipate(row){
        this.deletecenterDialogVisible = true;
        this.participantInfo.contestName=row.contestName;
        this.participantInfo.contestDate=row.contestDate;
        this.participantInfo.contestType=row.contestType;
        this.participantInfo.contestInformationId=row.contestInformationId;
        this.participantInfo.applicantId=sessionStorage.getItem("userId");
        this.participantInfo.applicantRealname=sessionStorage.getItem("realname");

      },
      reset(){
          this.query.contestName="";
          this.query.contestDate="";
          this.query.contestType="";
          this. getParticipantListByPageAndUserId();
        },
        handleCloseDelete(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            
            done();
          })
          .catch(_ => {

          });
      },
       participate(row){
         this.participantInfo.contestName=row.contestName;
         this.participantInfo.contestDate=row.contestDate;
         this.participantInfo.contestType=row.contestType;
         this.participantInfo.contestInformationId=row.contestInformationId;
         this.participantInfo.applicantId=sessionStorage.getItem("userId");
         this.participantInfo.applicantRealname=sessionStorage.getItem("realname");
         console.log("参与人信息");
         console.log(this.participantInfo);
         this.$axios.post("/insertParticipant",this.participantInfo).then(resp=>{
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
       this.getParticipantListByPageAndUserId()
      },
      //选择每页信息个数
     handleSizeChange(val) {
       this.query.pageSize = val
       this.getParticipantListByPageAndUserId()
       },
     getParticipantListByPageAndUserId(){
      this.query.applicantId=sessionStorage.getItem("userId")
       console.log("this.query");
       console.log(this.query);

       this.$axios({
         url: "/getParticipantListByPageAndUserId",
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
         this.contestInfo=result.data.contestInformation
         
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
      this.query.pageSize=3;
      this.getParticipantListByPageAndUserId();
     }
   },
     //生命周期 - 创建完成（可以访问当前this实例）
     created() {},
     //生命周期 - 挂载完成（可以访问DOM元素）
     mounted() {
       this.getParticipantListByPageAndUserId();
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