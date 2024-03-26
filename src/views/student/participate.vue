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
    :row-class-name="tableRowClassName"
    max-height="450">
			<el-table-column fixed tooltip-effect="dark" width="55">
			</el-table-column>
			<el-table-column prop="contestName" label="赛项名称" min-width="200">
			</el-table-column>
			<el-table-column prop="contestType" label="赛项类型" min-width="100">
			</el-table-column>
			<el-table-column prop="participantsNumber" label="可报名人数剩余" min-width="150">
			</el-table-column>
			<el-table-column sortable prop="contestDate" label="赛项日期" min-width="200">
                <!-- <template slot-scope="scope">
                	{{ $toTime(scope.row["contestDate"],"yyyy-MM-dd") }}
                </template> -->
			</el-table-column>
			<el-table-column sortable prop="deadlineTime" label="报名截止日期" min-width="200">
                <!-- <template slot-scope="scope">
                	{{ $toTime(scope.row["contestDate"],"yyyy-MM-dd") }}
                </template> -->
			</el-table-column>
			<!-- <el-table-column prop="participationConditions" label="竞赛状态" min-width="200">

			</el-table-column>  -->

			<el-table-column fixed="right" label="操作" min-width="120">
				<template slot-scope="scope">
          <el-button class="el-button el-button--small is-plain el-button--default" style="margin: 5px !important;" size="small" @click="detail(scope.row)">
						<span>详情</span>
					</el-button>
					<el-button class="el-button el-button--small is-plain el-button--default" v-if="scope.row.participantsNumber>=1" style="margin: 5px !important;" size="small" @click="participate(scope.row)">
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
        :page-sizes="[5,10,15]"
        :page-size = "query.pageSize" 
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalRecords"
      ></el-pagination>
    </div>
		<!-- /分页器 -->
    
     <!-- 遮罩 -->
  <el-dialog title="详细信息" :visible.sync="centerDialogVisible" width="1000px">
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
              <i class="el-icon-s-flag"></i>
              竞赛人数
            </template>
            {{ form.participantsNumber }}
          </el-descriptions-item>

          <el-descriptions-item labelStyle="width: 100px"contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-s-opportunity"></i>
              竞赛规则
            </template>
            {{ form.contestRules }}
          </el-descriptions-item>


          <el-descriptions-item labelStyle="width: 100px"contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-trophy"></i>
              竞赛条件
            </template>
            {{ form.participationConditions }}
          </el-descriptions-item>
          <el-descriptions-item labelStyle="width: 150px" contentStyle="width: 250px">
            <template slot="label">
              <i class="el-icon-s-order"></i>
              竞赛介绍
            </template>
            {{ form.contestIntroduction }}
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
          centerDialogVisible:false,
          currentPage:1,
          //分页
          totalRecords: 0,
           //总条数，总共有多少条数据,
           pageInfo:{
            pageSize: 5,
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
          pageSize: 5,
          pageIndex: 0,
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
        tableRowClassName({row, rowIndex}) {
          console.log(row.contestType);
        if (row.contestType == '院级') {
          return 'warning-row';
        } else if (row.contestType == '校级') {
          return 'success-row';
        }
        return '';
      },
        queryInfo(){
        this.query.pageIndex=0;
        this.query.pageSize=5;
        this.getContestListByPage();
       },
        reset(){
          this.query.contestName="";
          this.query.contestDate="";
          this.query.contestType="";
          this. getContestListByPage();
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
              this.getContestListByPage()
							return this.$message({
								message:result.msg,
								type:'success'
							});
						}
						this.$message.error(result.msg);
            this.getContestListByPage()
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
        this.getContestListByPage()
       },
       //选择每页信息个数
      handleSizeChange(val) {
        this.query.pageSize = val
        this.getContestListByPage()
        },

      getContestListByPage(form){
        console.log("this.query");
        console.log(this.query);
        this.$axios({
          url: "/getContestListByPage",
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