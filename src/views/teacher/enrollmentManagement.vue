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
    
    <download-excel class = "export-excel-wrapper":data = "contestInfo"
       :fields = "json_fields" name = "filename.xls">
    		<el-button  style="float: right;" round type="success" icon="download" > 导出 </el-button>
    </download-excel>
    <el-button type="warning" @click="handleApprovalOption()">批量审核通过</el-button> 
    <el-button type="warning" @click="handleDisApprovalOption()">批量审核不通过</el-button>    
    <el-button type="danger" @click="handleDelete()">批量删除</el-button> 
    <el-table  ref="multipleTable"
   id="table"
   :data="contestInfo"
   tooltip-effect="dark"
   style="width: 100%"
   max-height="450"
   @selection-change="handleSelectionChange"
    :row-key="getRowKeys">
   <el-table-column
      type="selection"
      width="55"
      :reserve-selection="true">
    </el-table-column>
     <el-table-column fixed tooltip-effect="dark" width="55">
     </el-table-column>
     <el-table-column prop="applicantRealname" label="报名人" min-width="50">
     </el-table-column>
     <el-table-column prop="contestName" label="赛项名称" min-width="100">
     </el-table-column>
     <el-table-column prop="contestType" label="赛项类型" min-width="100">
     </el-table-column>
     <el-table-column prop="examineState" label="审核状态" min-width="100">
     </el-table-column>
     <el-table-column sortable prop="contestDate" label="赛项日期" min-width="100">
     </el-table-column>

     <el-table-column sortable prop="updateTime" label="更新时间" min-width="100">
     </el-table-column> -->
     <el-table-column fixed="right" label="操作" min-width="120">
       <template slot-scope="scope">
         <el-button class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" size="small" @click="detail(scope.row)">
           <span>报名详情</span>
         </el-button>
         <el-button type="danger" v-if="scope.row.examineState=='未通过'" class="el-button el-button--small is-plain el-button--default" 
         style="margin: 5px !important;" size="small" @click="deleteParticipate(scope.row)">
           <span>删除</span>
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
   <el-dialog title="报名人详细信息" :visible.sync="centerDialogVisible" width="1000px">
 <div class=" container" style="margin-top:25px;margin-left:30px;">
  <el-card>
        <el-descriptions class="margin-top" :column="2" border>
        
          <el-descriptions-item labelStyle="width: 100px"contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-office-building"></i>
              竞赛名称
            </template>
            {{ form.contestName }}
          </el-descriptions-item>
          
          <el-descriptions-item labelStyle="width: 100px"contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-star-on"></i>
              竞赛类别
            </template>
            {{ form.contestType }}
          </el-descriptions-item>
          

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              竞赛日期
            </template>
            {{ form.contestDate }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              更新日期
            </template>
            {{ form.updateTime }}
          </el-descriptions-item>
          <el-descriptions-item labelStyle="width: 100px"contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-user"></i>
              竞赛申请人
            </template>
            {{ form.applicantRealname }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-odometer"></i>
              年级
            </template>
            {{ form.grade }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              学院
            </template>
            {{ form.academy }}
          </el-descriptions-item>
          <el-descriptions-item >
            <template slot="label">
              <i class="el-icon-office-building"></i>
              专业
            </template>
            {{ form.major }}
          </el-descriptions-item>

          <el-descriptions-item labelStyle="width: 100px"contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-s-opportunity"></i>
              审核状态
            </template>
            {{ form.examineState }}
          </el-descriptions-item>
          
          <el-descriptions-item v-if="form.examineState=='已通过'" labelStyle="width: 150px" contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-s-order"></i>
              审核操作
            </template>
            <el-button class="el-button el-button--small is-plain el-button--default" 
            style="margin: 5px !important;" type="danger" size="small" @click="updateParticipant('fail')">
            <span>不予通过</span>
            </el-button>
          </el-descriptions-item>
      

        <el-descriptions-item v-if="form.examineState=='未通过'" labelStyle="width: 150px" contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-s-order"></i>
              审核操作
            </template>
            <el-button class="el-button el-button--small is-plain el-button--default" 
            style="margin: 5px !important;" type="success" size="small" @click="updateParticipant('pass')">
            <span>审核通过</span>
            </el-button>
        </el-descriptions-item>

        <el-descriptions-item v-if="form.examineState=='未审核'" labelStyle="width: 150px" contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-s-order"></i>
              审核操作
            </template>
            <el-button class="el-button el-button--small is-plain el-button--default" 
            style="margin: 5px !important;" type="danger" size="small" @click="updateParticipant('fail')">
            <span>不予通过</span>
           </el-button>

          <el-button class="el-button el-button--small is-plain el-button--default" 
            style="margin: 5px !important;float: right;" type="success" size="small" @click="updateParticipant('pass')">
            <span>审核通过</span>
          </el-button>
        </el-descriptions-item>
        
        <el-descriptions-item labelStyle="width: 150px" contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-s-order"></i>
              审核回复
            </template>
            <el-input placeholder="请输入审核回复" type="textarea":rows="2" style="width: 80%" v-model="form.examineReply" autocomplete="off"></el-input>
          </el-descriptions-item>
      </el-descriptions>
  </el-card>     
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
        tableDataType:"报名信息",
        json_fields: {
        报名人: "applicantRealname",    //常规字段
        学院:"academy",
        专业:"major",
        赛项名称: "contestName", //支持嵌套属性
        赛项类型: "contestType",
        赛项日期: "contestDate",

      },

        userGroup:sessionStorage.getItem("userGroup"),
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
       json_meta: [
        [
          {
            " key ": " charset ",
            " value ": " utf- 8 "
          }
        ]
      ],
       // 查询
       query: {
        pageSize: 50,
        pageIndex: 0,
        contestName:"",
        contestDate:"",
        contestType:"",
       },
       // 数据
       contestInfo: [],
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
      //多选
      handleSelectionChange(list) {
      console.log(list);
      this.checkedList = list
    },
    getRowKeys(row) {
      return row.contestParticipantId
    },
    deleteParticipate(row){

              this.$confirm('此操作将永久删除该参与者, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.form=row;
      this.$axios.post("/teacher/deleteParticipant",this.form).then(resp=>{
        let result = resp.data;
        if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
			if (result.code==='0'){
				this.getParticipantListByPage();
				return this.$message({
					type:"success",
					message:result.msg
				})
			}
			return this.$message.error(result.msg)
      })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
 //批量操作
 handleDelete(){
  console.log("checkedList");
      console.log(this.checkedList);
	  this.$confirm("此操作将选中的参赛者全部删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "danger",
      })
        .then(() => {
          //遍历获得多选选中的index值
		this.$axios.post("/teacher/deleteAll",this.checkedList).then(resp=>{
		let result = resp.data;
		if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
			if (result.code==='0'){
				this.getParticipantListByPage();
				return this.$message({
					type:"success",
					message:result.msg
				})
			}
			return this.$message.error(result.msg)

		})

        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消批量操作",
          });
        });
 },
 handleDisApprovalOption(){
      console.log("checkedList");
      console.log(this.checkedList);
	  this.$confirm("此操作将选中的参赛者审核 全部 不通过, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //遍历获得多选选中的index值
		this.$axios.post("/teacher/disApprovalAll",this.checkedList).then(resp=>{
		let result = resp.data;
		if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
			if (result.code==='0'){
				this.getParticipantListByPage();
				return this.$message({
					type:"success",
					message:result.msg
				})
			}
			return this.$message.error(result.msg)

		})

        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消批量操作",
          });
        });
    },
     handleApprovalOption(){
	  console.log("checkedList");
      console.log(this.checkedList);
	  this.$confirm("此操作将选中的参赛者审核 全部 通过, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //遍历获得多选选中的index值
		this.$axios.post("/teacher/approvalAll",this.checkedList).then(resp=>{
		let result = resp.data;
		if(result.code==='401'){
              this.$router.push("/login")
              return this.$message({
                type:"warning",
                message:result.msg
              })
            }
			if (result.code==='0'){
				this.getParticipantListByPage();
				return this.$message({
					type:"success",
					message:result.msg
				})
			}
			return this.$message.error(result.msg)

		})

        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消批量操作",
          });
        });
    },
      exportExcelHeader() {
      this.json_fields = {};
      this.tableFilterData.forEach(e => {
      this.json_fields[e.label] = e.prop;
      });
    },
    // exportExcel() {
      // table 是表格组件的ID
    //   let tables = document.getElementById("table");
    //   let table_book = this.$XLSX.utils.table_to_book(tables);
    //   var table_write = this.$XLSX.write(table_book, {
    //     bookType: "xlsx",
    //     bookSST: true,
    //     type: "array",
    //   });
    //   try {
    //     this.$FileSaver.saveAs(
    //       new Blob([table_write], { type: "application/octet-stream" }),
    //       "报名信息"+ ".xlsx"
    //     );
    //   } catch (e) {
    //     if (typeof console !== "undefined") console.log(e, table_write);
    //   }
    //   return table_write;
    // },
      updateParticipant(index){
        if(index=='pass'){
          console.log("pass_declaration");
          console.log(this.form.contestDeclarationId);
          this.updateForm.contestInformationId=this.form.contestInformationId;
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
            this.getParticipantListByPage();
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
        this.updateForm.contestInformationId=this.form.contestInformationId;
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
            this.getParticipantListByPage();
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
            this.getParticipantListByPage()
             return this.$message({
               message:result.msg,
               type:'success'
             });
           }
           this.$message.error(result.msg);
           this.deletecenterDialogVisible = false;
           this.getParticipantListByPage()
         })
      },
      reset(){
          this.query.contestName="";
          this.query.contestDate="";
          this.query.contestType="";
          this. getParticipantListByPage();
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
       this.getParticipantListByPage()
      },
      //选择每页信息个数
     handleSizeChange(val) {
       this.query.pageSize = val
       this.getParticipantListByPage()
       },
     getParticipantListByPage(){
      this.query.applicantId=sessionStorage.getItem("userId")
       console.log("this.query");
       console.log(this.query);
      if(this.userGroup=="管理员"){
        this.$axios({
         url: "/admin/getParticipantListByPage",
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
         this.contestInfo=result.data.contestInfo;
         
         this.totalRecords=result.data.totalRecords;
       
         })
      }
      else{
        this.$axios({
         url: "/teacher/getParticipantListByPage",
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
         this.contestInfo=result.data.contestInfo;
         
         this.totalRecords=result.data.totalRecords;
       
         })
      }
       
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
      this.getParticipantListByPage();
     }
   },
     //生命周期 - 创建完成（可以访问当前this实例）
     created() {},
     //生命周期 - 挂载完成（可以访问DOM元素）
     mounted() {
       this.getParticipantListByPage();
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