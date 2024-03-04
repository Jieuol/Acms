<template>
	<div>
		<AppHeader />
		<el-carousel height="200px">
		      <el-carousel-item v-for="item in banners" :key="item">
		        <el-image :src="item" />
		      </el-carousel-item>
		    </el-carousel>
			<el-tabs value="first" id="mianban">
			    <el-tab-pane label="快递服务" name="first" id="ch1">
					<bt />
				</el-tab-pane>
			    <el-tab-pane label="快运服务" name="second" id="ch1">快运服务</el-tab-pane>
			    <el-tab-pane label="冷运服务" name="third" id="ch1">冷运服务</el-tab-pane>
			    <el-tab-pane label="医药服务" name="fourth" id="ch1">医药服务</el-tab-pane>
				<el-tab-pane label="国际服务" name="five" id="ch1">国际服务</el-tab-pane>
			</el-tabs>
		<AppFooter />
	</div>
</template>


<style scoped>
	body{
	  margin: 0;
	  background-color: #FFFFFF;
	}
	#mb{
		background-color: #FFFFFF;
		height: 500px;
	}
	.el-image__inner{
		background-color: #00ffff;
		height: 5000px;
	}
</style>

<script>
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
import T1 from "@/assets/images/T1.jpg";
import T2 from "@/assets/images/T2.jpg";
import T4 from "@/assets/images/T4.jpg";
import bt from "@/components/bt.vue";

export default{
	data(){
		return{
			banners:[T1,T2,T4]
		}
	},
	components:{
	  AppHeader,
	  AppFooter,
	  bt,
	},
};
</script>

