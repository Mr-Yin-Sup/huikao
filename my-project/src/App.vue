<script>
	
	
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		onPageNotFound: [
    	function (res) {
    		// 跳转到 404 页面：
      		uni.redirectTo({
        		url: "pages/Error/Error", // 404 页面的路径
      		});
    	},
  ],
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import "@/colorui/main.css";
	@import "@/colorui/icon.css";
	@import "@/colorui/animation.css";
	
	
	// 注册登录页引用
	@import "@/common/styles/qui_weex.scss";
	@import "@/pages/auth/styles/skin03.scss";
	
	// 微信||H5引用图标库
	//#ifdef H5 || MP-WEIXIN
	@import "@/common/font/iconfont.css";
	//#endif
	
	//APP引用图标库
	//#ifdef APP-PLUS
	@import "@/static/font/iconfont2.css";
	//#endif
</style>
