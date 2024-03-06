<template>
  <div class="main">
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
      <el-table-column prop="participantsNumber" label="参与人数" min-width="200">
      </el-table-column>
      <el-table-column prop="contestDate" label="赛项日期" min-width="200">
          <!-- <template slot-scope="scope">
              {{ $toTime(scope.row["contestDate"],"yyyy-MM-dd") }}
          </template> -->
      </el-table-column>
      <!-- <el-table-column prop="contestIntroduction" label="赛项介绍" min-width="200">
      </el-table-column>
      <el-table-column prop="contestRules" label="赛项规则" min-width="200">
      </el-table-column>
      <el-table-column prop="participationConditions" label="参与条件" min-width="200">
      </el-table-column>
      <el-table-column sortable prop="createTime" label="创建时间" min-width="200">
          <!-- <template slot-scope="scope">
              {{ $toTime(scope.row["createTime"],"yyyy-MM-dd hh:mm:ss") }}
          </template> -->
      <!-- </el-table-column> -->

      <el-table-column sortable prop="updateTime" label="更新时间" min-width="200">
          <!-- <template slot-scope="scope">
              {{ $toTime(scope.row["updateTime"],"yyyy-MM-dd hh:mm:ss") }}
          </template> -->
      </el-table-column> -->
      <el-table-column fixed="right" label="操作" min-width="120">
          <template slot-scope="scope">
    <el-button class="el-button el-button--small is-plain el-button--default" style="margin: 5px !important;" size="small" @click="to_table(scope.row,)">
                  <span>详情</span>
              </el-button>
              <el-button class="el-button el-button--small is-plain el-button--default" style="margin: 5px !important;" size="small" @click="to_table(scope.row,)">
                  <span>报名</span>
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
       :page-size = "pageInfo.pageSize" 
       layout="total, sizes, prev, pager, next, jumper"
       :total="totalRecords"
     ></el-pagination>
   </div>
       <!-- /分页器 -->
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
                   "size": 7,
                   "page": 1,
                   "contest_name": "",
                   "contest_type": "",
                   "contest_date": "",
                   "login_time": "",
                   "create_time": "",
                   "orderby": `create_time desc`
               },
               // 数据
       contestInfo: [],
       multipleSelection: [],
       
       }
       
     },
     //监听属性 类似于data概念
     computed: {},
     //监控data中的数据变化
     watch: {},
     //方法集合
     methods: {
       handleCurrentChange(newPage) {
       newPage=newPage-1
       console.log("newPage:")
       console.log(newPage)
       this.pageInfo.pageIndex = newPage*this.pageInfo.pageSize
       // this.pageInfo.pageIndex = newPage+this.pageInfo.pageSize
       console.log("after：")
       this.getContestListByPage()
     },
     handleSizeChange(val) {
       this.pageInfo.pageSize = val
       this.getContestListByPage()
     },
     getContestListByPage(){
       console.log("this.pageInfo");
       console.log(this.pageInfo);
       this.$axios({
         url: "/getContestListByPage",
         method: 'GET',
         params: this.pageInfo
       }).then(resp=>{

                   let result = JSON.stringify(resp.data);
         result = eval("("+result+")");
         this.contestInfo=result.data.contestInfo
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
       this.getContestListByPage();
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