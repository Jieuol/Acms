<!--  -->
<template>
  <div class="main">
    <el-form :model="addForm" ref="addForm" >
        <el-form-item label="竞赛名称" class="item" label-width="100px">
          <el-input v-model="addForm.contestName" style="width: 80%" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="竞赛类型" label-width="100px">
          <el-select v-model="addForm.contestType" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        </el-form-item>

        <el-form-item label="竞赛介绍" label-width="100px">
          <el-input type="textarea":rows="2" style="width: 80%" v-model="addForm.contestIntroduction" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="竞赛时间" label-width="100px">
          <el-date-picker
            v-model="addForm.contestDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="参与人数"  label-width="100px">
          <el-input v-model="addForm.participantsNumber" style="width: 30%" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="竞赛规则"  label-width="100px">
          <el-input type="textarea":rows="2" style="width: 80%" v-model="addForm.contestRules" autocomplete="off"></el-input>
        </el-form-item>

        <el-button type="primary" @click="apply()">立即申报</el-button>
      </el-form>
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
         editForm:{
         },
         detailForm:{
         },
         addForm:{
          
         },
         AddcenterDialogVisible:false,
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
       // 数据
       noticeInfo: [],
       multipleSelection: [],
       participantInfo:{},
       }
       
     },
     methods: {
      apply(){
        console.log("addForm");
        this.addForm.declarant=sessionStorage.getItem("userId");
        console.log(this.addForm);
        this.$axios.post("/applyContest",this.addForm).then(resp=>{
          let result = resp.data;
          if(result.code==="0"){
            this.addForm={};
            return this.$message({
								message:result.msg,
								type:'success'
							});
          }
          this.$message.error(result.msg);
        })
      }
   },
     //生命周期 - 创建完成（可以访问当前this实例）
     created() {},
     //生命周期 - 挂载完成（可以访问DOM元素）
     mounted() {
  
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
 .item{
  color: black;
 }
 </style>