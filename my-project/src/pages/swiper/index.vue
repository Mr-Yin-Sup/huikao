<template>
  <view class="containers">
    <lsSwiper :cardList="cardList" :cardIndex="cardIndex" class="padding-top"/>
	<view style="width:70%; margin: 30rpx auto;  text-align: center; color: #555555;">
	
		
	</view>
  </view>
  
</template>

<script>
import lsSwiper from '../../components/ls-swiper/ls-swiper'
export default {
  components: {
    lsSwiper
  },
  data () {
    return {
		//#ifdef MP-WEIXIN
			// 为了解决微信小程序无法使用全局变量修改所以每个文件都设置了服务端地址
				api: 'http://192.168.2.100:8881',
		//#endif
      cardList:[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}],
      cardIndex: 0,
	
    }
  },
  onLoad(options) {
		let that=this;
		that.cardIndex=Number.parseInt(options.index);
		
		uni.request({
			url:that.api+"/api/findIcnoByTypeId",
			data:{
				typeId:options.typeId
			},
			header: {
				'content-type': 'application/x-www-form-urlencoded', 
			},
			method:"POST",
			success(res) {
				that.cardList=res.data;
				console.log(that.cardList);
				uni.setNavigationBarTitle({
					title:that.cardList[0].icnoType.icnoTypeName
				})
				setTimeout(function () {uni.hideLoading();}, 100);
				
			}
		})
  }
}
</script>

<style lang="scss" scoped>
.containers {
  width: 750upx;
  min-height: 100vh;
  position: relative;
  overflow: hidden;
  background-color: #f2f2f2;
}

</style>
