import Vue from 'vue'
const globalMixin = {
	// eslint-disable-next-line
	install(Vue) {
		Vue.mixin({
		    data() {
				return {
					unitText: '点劵'
				}
			},
			methods: {
				isLogin(bool,loginUrl=false) {
					console.log('是否需要登录：'+ bool)
					var token = uni.getStorageSync('token');
					if (bool && !token) {
						if(!loginUrl) {
							uni.reLaunch({
								url: '/pages/login/login',
							})
						}
					}
				},
				success(res,title, callback) {
					console.log(res)
					if(res.code == 0) {
						uni.showToast({
							icon: 'none',
							title: title?title: res.msg
						})
						callback&&callback()
					} 
				},
				goBack() {
				
					// #ifdef H5
					let canBack = true
					const pages = getCurrentPages()
					// 有可返回的页面则直接返回，uni.navigateBack默认返回失败之后会自动刷新页面 ，无法继续返回  
					if (pages.length > 1) {
						uni.navigateBack(1)
						return;
					}
					// vue router 可以返回uni.navigateBack失败的页面 但是会重新加载  
					let a = this.$router.go(-1)
					// router.go失败之后则重定向到首页  
					if (a == undefined) {
						uni.reLaunch({
							url: "/pages/index/index"
						})
					}
					return;
					// #endif  
					uni.navigateBack(1)
				},
				viewImage(imgs) {
					let arr = []
					if(typeof imgs === 'string'){
						arr.push(imgs)
					} else {
						arr = imgs
					}
					uni.previewImage({
						urls: arr,
					});
				},
				toast(title){
					uni.showToast({
						title: title,
						icon: 'none',
						position:'bottom'
					})
				},
				openAuth(url, fn){
					var token = userConfig.session.token();
					if(!token) {
						this.$refs.auth.open();
						return
					}
					fn&&fn()
					if(url&&url =='') return
					uni.navigateTo({
						url:url
					})
					
				},
				openPopup(ref) {
					this.$refs[ref].open()
				},
				closePopup(ref) {
					this.$refs[ref].close()
				},
				goto(path) {
					console.log(path)
					uni.navigateTo({
						url: path
					});
				},
				doing() {
					uni.showToast({
						title: '功能开发中'
					});
				},
				$uGetRect(selector, all) {
					return new Promise(resolve => {
						uni.createSelectorQuery().
						in(this)[all ? 'selectAll' : 'select'](selector)
							.boundingClientRect(rect => {
								if (all && Array.isArray(rect) && rect.length) {
									resolve(rect)
								}
								if (!all && rect) {
									resolve(rect)
								}
							})
							.exec()
					})
				}
			},
			filters: {
				// ...filter,
			},
			computed: {
			
				
				
			},
		})
	},
}

Vue.use(globalMixin)

export default globalMixin
