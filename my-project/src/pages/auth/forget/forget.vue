<template>
	<view class="abslrtb flex-column a-center wrap">
		<qui-navbar title="忘记密码"></qui-navbar>
		<view class="topbox flex-column aj-center">
			<image class="logoimg" src="/static/logo.png" mode=""></image>
		</view>

		<view class="form">
			<view class="flex a-center form-item">
				<view class="label">
					<text>手机号</text>
				</view>
				<image class="label_icon" src="/static/images/icon_phone.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input placeholder-class="placeholder" maxlength="11" class="qui-input" type="text"
						v-model="submitForm.uPhone" placeholder="请输入手机号" />
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>验证码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input v-model="submitForm.code" placeholder-class="placeholder" maxlength="4" class="qui-input"
						type="text" value="" placeholder="请输入验证码" />
				</view>
				<view @click="checkCode=getCode(4)">
					<text
						style="opacity: 0.8;margin-right: 30rpx; padding: 10px 10px 10px 15px;letter-spacing:8px; font-size: 18px !important;"
						class="fs28 ptb20 main-color yzm">{{checkCode}}</text>
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>短信验证码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input v-model="submitForm.phoneCode" placeholder-class="placeholder" maxlength="6" class="qui-input"
						type="text" value="" placeholder="请输入短信验证码" />
				</view>
				<view @click="sendPhoneCode">
					<text style="opacity: 0.8;" class="fs28 ptb20 main-color yzm">{{submitForm.btnText}}</text>
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>新密码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_pw.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input :password="password" v-model="submitForm.uPassword" placeholder-class="placeholder"
						class="qui-input" type="text" value="" placeholder="请输入新密码" />
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>确认密码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_pw.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input :password="password" v-model="submitForm.uPassword2" placeholder-class="placeholder"
						class="qui-input" type="text" value="" placeholder="请输入确认密码" />
				</view>
			</view>
		</view>
		<view class="btns">
			<view class="qbtn" @click="updatePasswordBtn">
				<text class="btn-text-color fs30">确认</text>
			</view>
			<view class="flex ptb30 mlr20 aj-center">
				<view @click="goLogin()" class="">
					<text class="fs26 nav-text-color underline">返回登录</text>
				</view>
			</view>
		</view>

		<view class="bottom">

		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 为了解决微信小程序无法使用全局变量修改所以每个文件都设置了服务端地址
				api: 'http://192.168.2.100:8881',
				password: true,
				checkCode: '',
				submitForm: {
					uPhone: "",
					code: "",
					phoneCode: '',
					uPassword: "",
					newPassword2: "",
					btnText: '获取验证码'
				}
			}
		},
		methods: {
			/**查询手机号码是否注册
			 * @param {Object} phoneNumber手机号码
			 */
			async checkPhoneNumberIsRegister(phoneNumber) {
				let isRegister = false;
				let that = this;
				let p = await new Promise(function(resolve, reject) {
					uni.request({
						method: "POST",
						url: that.api + "/api/checkPhoneNumberIsRegister",
						data: {
							phoneNumber: phoneNumber
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded',
						},
						success(res) {
							resolve(res.data);

						}
					})
				})

				if (p == "ok") {

					isRegister = false;
					
				} else {
					isRegister = true;
					
				}

				return isRegister;
			},
			async sendPhoneCode() {
				let that = this;
				let phoneReg = /^1(3|4|5|6|7|8|9)\d{9}$/;

				//验证手机号码
				if (!phoneReg.test(this.submitForm.uPhone)) {
					uni.showToast({
						icon: 'none',
						title: "手机号码未填写或者格式不正确!"
					})
					return false;
				}
				//let isRegister=await this.checkPhoneNumberIsRegister( this.registerForm.phoneNumber)=="no";
				let p = this.checkPhoneNumberIsRegister(this.submitForm.uPhone);

				p.then((data) => {
					

					if (!data) {
						uni.showToast({
							icon: 'none',
							title: "该手机号码还没有注册过"
						})
						return false;
					} else {
						if (this.submitForm.btnText != '获取验证码') {
							return false;
						}

						if (this.submitForm.code == null || this.submitForm.code.toLocaleLowerCase() !=
							this.checkCode.toLocaleLowerCase()) {
							uni.showToast({
								icon: 'none',
								title: "验证码错误!"
							})
							this.checkCode = this.getCode(4);
							return false;
						}


	





						uni.request({
							method: "POST",
							url: that.api + "/api/sendPhoneCode",
							data: {
								phoneNumber: this.submitForm.uPhone
							},
							header: {
								'content-type': 'application/x-www-form-urlencoded',
							},
							success(res) {
								
								if (res.data == 'ok') {
									uni.showToast({
										icon: 'none',
										title: "发送成功"
									})
									that.submitForm.btnText = 60;
									let djs = setInterval(() => {
										if (that.submitForm.btnText >= 1) {
											that.submitForm.btnText--;
										} else {
											that.submitForm.btnText = "获取验证码";
											clearInterval(djs);
										}

									}, 1000)
								}
							}
						})

					}



				})



			},
			updatePasswordBtn(){
				
				let that=this;
				
				let submitRules = [
					 
				 {name: 'uPhone', type: 'required', errmsg: '请输入手机号码'},
				  {name: 'uPhone', type: 'regex',regex: /^1(3|4|5|6|7|8|9)\d{9}$/ ,errmsg: '请输入手机号码'},
					
				  {name: 'uPassword', type: 'required', errmsg: '请输入密码'},
				  {name: 'uPassword', type: 'regex', regex: /[a-zA-Z0-9_]{6,12}$/, errmsg: '密码只能输入6-12位英文数字下划线'},
				  {name: 'uPassword2', type: 'required', errmsg: '请输入确认密码'},
				  {name: 'code', type: 'required', errmsg: '请输入验证码'},
				  {name: 'uPassword2', type: 'eq', eqName: 'uPassword', required: true, errmsg: '两次输入密码不一致'},
				 
				  {name: 'phoneCode', type: 'required', errmsg: '请输入短信验证码'},
				   
				]
				let valLoginRes = this.$validate.validate(this.submitForm, submitRules)
				if (!valLoginRes.isOk) {
				  uni.showToast({
				    icon: 'none',
				    title: valLoginRes.errmsg
				  })
				  return false
				}
				
				
				uni.request({
					url:that.api+"/api/userUpdatePassword?phoneCode="+that.submitForm.phoneCode,
					method:"POST",
					data:that.submitForm,
					// header:{
					// 	'content-type': 'application/x-www-form-urlencoded',
					// },
					success(res) {
						console.log(res);
						if(res.data.msg=="code error"){
							uni.showToast({
								icon: 'none',
								title:"短信验证码错误!"
							})
							return false;
						}
						if(res.data.data!='' && res.data.data!=undefined){
							uni.showModal({
								title:'提示',
								content:'密码修改成功!',
								showCancel:false,
								success(res1) {
									if(res1.confirm){
										
										uni.reLaunch({
										    url: "/pages/mine/index"
										});
									}
								}
							})
						}else{
							uni.showToast({
								icon: 'none',
								title:"发生错误,请稍后重试。"
							})
						}
						
					}
				})
								
				
				
			},
			goLogin() {
				uni.navigateTo({
					url: '/pages/auth/login/login'
				})
			},
			goRegister() {
				uni.navigateTo({
					url: '/pages/auth/register/register'
				})
			},
			goForget() {
				uni.navigateTo({
					url: '/pages/auth/forget/forget'
				})
			},
			getCode(n) { //验证码生成
				var all = "azxcvbnmsdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP0123456789";
				var b = "";
				for (var i = 0; i < n; i++) {
					var index = Math.floor(Math.random() * 62);
					b += all.charAt(index);

				}
				return b;
			},
		},
		onLoad() {
			this.checkCode = this.getCode(4);
		}
	}
</script>

<style lang="scss">
	// @import "../styles/skin01.scss";
</style>
