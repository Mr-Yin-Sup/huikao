<template>
	<view>
		<view class="cu-list grid col-2 "    >
			
			<view class="cu-item"  v-for="(item,index) in icnos" @click="gotoSwiper(index)">
				<view >
					<img  style="height: 200rpx; max-width: 300rpx;" :src="api+item.icnoImg" alt="">
				</view>
				<text>{{item.icnoName}}</text>
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
				icnos:null,
				typeId:0,
			}
		},
		methods: {
			gotoSwiper(index){
				uni.navigateTo({
					url:"../swiper/index?index="+index+'&typeId='+this.typeId
				})
			}
		},
		onLoad(options) {
			let that=this;
			
			uni.showLoading({title: '加载中',mask:true});
			that.typeId=options.id;
			
			uni.request({
				url:that.api+"/api/findIcnoByTypeId",
				data:{
					typeId:that.typeId
				},
				header: {
					'content-type': 'application/x-www-form-urlencoded', 
				},
				method:"POST",
				success(res) {
				
					that.icnos=res.data;
					console.log(that.icnos);
					uni.setNavigationBarTitle({
						title:that.icnos[0].icnoType.icnoTypeName
					})
					setTimeout(function () {uni.hideLoading();}, 100);
				}
			})
		}
	}
</script>

<style>

.cu-list .cu-item{
	border: solid 1px #ccc;
	
}
.cu-list .cu-item img{
	padding: 0 30rpx;
}
</style>
