<template>
  <view class="flex justify-center align-center error">
    <text class="title">页面迷路了</text>
    <text class="info">1. 请确认访问地址是否有误</text>
    <text class="info">2. 请检查网络环境是否通畅</text>
    <text class="info">3. 关闭广告屏蔽插件后重试</text>
  </view>
</template>

<style lang="scss" scoped>
.error {
  flex-direction: column;
  height: 80vh;

  .title {
    margin-bottom: 100rpx;
    color: #333;
    font-weight: 600;
    font-size: 60rpx;
  }

  .info {
    margin-bottom: 20rpx;
    color: #333;
    font-weight: 500;
    font-size: 40rpx;
  }
}
</style>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>