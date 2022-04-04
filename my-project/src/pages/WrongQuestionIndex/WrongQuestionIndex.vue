<template>
	<view >
		<view class="cu-list menu">
		
			<view class="cu-item" @click="start"  style="border-bottom: #ccc 2px solid; ">
					<!-- <navigator :url="'/pages/WrongQuestionIndex/WrongQuestionIndex?'+'state=cuoti&&skin='+skin?0:1" hover-class="navigator-hover"> -->
				<view class="content">
					<text class="cuIcon-warn text-green"></text>
					<text class="text-grey">全部错题</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm">{{total}}<text class="lg text-gray cuIcon-right" ></text></text>
				</view>
				<!-- </navigator> -->
			</view>
			
			<view class="cu-item">
				<view class="content padding-tb-sm">
					<view>
						<text class=" text-blue margin-right-xs"></text> 答对后自动移除错题集</view>
					<view class="text-gray text-sm">
						</view>
				</view>
				<view class="action">
					<switch  @change="Switch" :class="skin?'checked':''" :checked="skin?true:false"></switch>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				//#ifdef MP-WEIXIN
					// 为了解决微信小程序无法使用全局变量修改所以每个文件都设置了服务端地址
				api: 'http://192.168.2.100:8881',
				//#endif
				skin:false,
				sKm:null,
				sCar:null,
				total:null
			}
		},
		methods: {
			Switch(e) {
				this.skin = e.detail.value
				uni.setStorageSync("userErrorSubjectSkin",e.detail.value)//设置缓存
			},
			start(){
				if(this.total>0){
					uni.navigateTo({
						url:'/pages/errSubject/index?'+'state=cuoti&&skin='+(this.skin?1:0)+'&sKm='+this.sKm+'&sCar='+this.sCar
					})
				}else{
					uni.showToast({
						icon:"error",
						title:"没有错题!"
					})
				}
			},
			init(option){
				uni.showLoading({title: '加载中',mask:true});
				let that = this;
				this.sKm=option.km;
				this.skin=uni.getStorageSync("userErrorSubjectSkin")?true:false;
				this.sCar=option.cartype;
				let userErrorSubjectList=uni.getStorageSync("userErrorSubjectList");
				
				
						
				
				
				//设置请求的参数
				let params={
						'sCar': option.cartype,//请求的车型
						'sKm': option.km,//请求的科目
						'state':null,
						'errList':null
					};
				//设置请求状态
				params.state='cuoti';		
				//获取存在缓存中的错题集
				params.errList=userErrorSubjectList.toString();
				uni.request({
					method:"POST",
					url:this.api+'/api/findSubjectByCarAndKm',
					data:params, 
					header:{'Content-Type': 'application/x-www-form-urlencoded'},
					success(res) {
						console.log(res);
						//页面存储获取到的错题
						that.total=res.data.filter((item)=>{return item.sKm==option.km}).length;
						setTimeout(function () {uni.hideLoading();}, 100);
					}
				})
				
				
				
				
			}
		},
		onLoad(option) {
			
			this.init(option)
			
		}
	}
</script>

<style>

</style>
