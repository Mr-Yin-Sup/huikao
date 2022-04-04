<template>
	<view class="abslrtb flex-column a-center wrap">
		<view class="topbox flex-column aj-center">
			<image class="logoimg" src="/static/logo.png" mode=""></image>
		</view>
		<view class="flex tabs mb30">
			<view @click="tab(0)" class="flex-1 flex aj-center" :class="tabIndex==0&&'active'">
				<text class="fs30 fw600 text-gray" :class="tabIndex==0&&'curtext'">账号登录</text>
			</view>
			<view @click="tab(1)" class="flex-1 flex aj-center" :class="tabIndex==1&&'active'">
				<text class="fs30 fw600 text-gray" :class="tabIndex==1&&'curtext'">账号注册</text>
			</view>
		</view>
		<view class="form" v-if="tabIndex ==0">
			<view class="flex a-center form-item">
				<view class="label">
					<text>账号</text>
				</view>
			
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input placeholder-class="placeholder" maxlength="16" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"	 v-model="loginForm.uNumber" class="qui-input" type="text" value="" placeholder="请输入账号或手机号码" />
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>密码</text>
				</view>
				
				
				
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input maxlength="12" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" placeholder-class="placeholder" :password="password" v-model="loginForm.uPassword" class="qui-input" type="text" value="" placeholder="请输入密码" />
				</view>
			</view>
			
			<view class="flex a-center form-item">
				<view class="label">
					<text>验证码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input v-model="loginForm.code" placeholder-class="placeholder" maxlength="4" class="qui-input" type="text" value="" placeholder="请输入验证码" />
				</view>
				<view @click="checkCode=getCode(4)">
					<text  style="opacity: 0.8;margin-right: 30rpx; padding: 10px 10px 10px 15px;letter-spacing:8px; font-size: 18px !important;" class="fs28 ptb20 main-color yzm">{{checkCode}}</text>
				</view>
			</view>
			
		</view>
		
		<view class="form" v-if="tabIndex ==1">
			<view class="flex a-center form-item">
				<view class="label">
					<text>账号</text>
				</view>
				<image class="label_icon" src="/static/images/icon_phone.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input  placeholder-class="placeholder" maxlength="16" class="qui-input" v-model="registerForm.uNumber" @blur="checkUNumber" type="text" value="" placeholder="请输入账号" />
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>昵称</text>
				</view>
				<image class="label_icon" src="/static/images/icon_phone.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input  maxlength="10" placeholder-class="placeholder" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" v-model="registerForm.uName" class="qui-input" type="text" value="" placeholder="请输入您的昵称" />
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>密码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input maxlength="12" placeholder-class="placeholder" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" :password="password" v-model="registerForm.uPassword" class="qui-input" type="text" value="" placeholder="请输入密码" />
				</view>
				
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>确认密码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input maxlength="12" placeholder-class="placeholder" v-model="registerForm.uPassword2" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" :password="password" class="qui-input" type="text" value="" placeholder="请再次输入密码" />
				</view>
				
			</view>
			
			
			
			<view class="flex a-center form-item">
				<view class="label">
					<text>验证码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input v-model="registerForm.code" placeholder-class="placeholder" maxlength="4" class="qui-input" type="text" value="" placeholder="请输入验证码" />
				</view>
				<view @click="checkCode=getCode(4)">
					<text  style="opacity: 0.8;margin-right: 30rpx; padding: 10px 10px 10px 15px;letter-spacing:8px; font-size: 18px !important;" class="fs28 ptb20 main-color yzm">{{checkCode}}</text>
				</view>
			</view>
			
			
			<view class="flex a-center form-item">
				<view class="label">
					<text>手机号码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input maxlength="11" placeholder-class="placeholder" v-model="registerForm.uPhone" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  class="qui-input" type="text" value=""  placeholder="请输入手机号码" />
				</view>
				<view @click="sendPhoneCode">
					<button  style="opacity: 0.8;margin-right: 30rpx;letter-spacing:8px; " class="fs28  main-color">{{registerForm.btnText}}</button>
				</view>
				
			</view>
			
			<view class="flex a-center form-item">
				<view class="label">
					<text>短信验证码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input maxlength="6"  placeholder-class="placeholder" v-model="registerForm.phoneCode" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  class="qui-input" type="text" value="" placeholder="请输入短信验证码" />
				</view>
				
			</view>
			
		</view>
		<view class="btns" style="margin-top: 30rpx;">
			<view class="qbtn" @click="doLogin" v-if="tabIndex==0" >
				<text class="btn-text-color fs30">登录</text>
			</view>
			<view v-if="tabIndex==1" @click="Register" class="qbtn">
				<text class="btn-text-color fs30">注册</text>
			</view>
			<!-- #ifdef MP-WEIXIN -->
			<!-- <view class="qbtn margin-top" >
				<button @click="wxLogin"> 获取头像昵称 </button>
			</view> -->
			
			<!-- <view class="qbtn margin-top" >
				<button open-type="getPhoneNumber"  @getphonenumber="getPhone"> 获取手机号码 </button>
			</view> -->
			<!-- #endif -->
			
			<view class="flex ptb30 mlr20 space-between" v-if="tabIndex ==0">
				<view class="" @click="tabIndex=1">
					<text class="fs26 nav-text-color underline">注册</text>
				</view>
				<view class="" @click="goForget">
					<text class="fs26 nav-text-color underline">忘记密码</text>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	//#ifdef MP-WEIXIN
		import WXBizDataCrypt from '@/common/utils/WXBizDataCrypt.js';
	//#endif
	
	export default {
		data() {
			return {
					// 为了解决微信小程序无法使用全局变量修改所以每个文件都设置了服务端地址
				api: 'http://192.168.2.100:8881',
				password: true,
				tabIndex: 0,
				userinfo:null,
				checkCode:'1234',
				loginForm:{
					code:null,
					uNumber:null,
					uPassword:null
				},
				registerForm:{
					code:null,
					uNumberok:false,
					uName:null,
					uNumber:null,
					uPassword:null,
					uPassword2:null,
					uPhone:null,
					phoneCode:null,
					btnText:'发送'
				}
			}
		},
		onLoad(){
			this.checkCode=this.getCode(4);
		},
		methods: {
		 async	sendPhoneCode(){	
				let that=this;
				let phoneReg=/^1(3|4|5|6|7|8|9)\d{9}$/;
				
				//验证手机号码
				if(!phoneReg.test(this.registerForm.uPhone)){
					uni.showToast({
					  icon: 'none',
					  title: "手机号码未填写或者格式不正确!"
					})
					return false;
				}
				//let isRegister=await this.checkPhoneNumberIsRegister( this.registerForm.phoneNumber)=="no";
				let p=this.checkPhoneNumberIsRegister( this.registerForm.uPhone);
				
				p.then((data)=>{
					console.log(data);
					
					if (data){
						uni.showToast({
						  icon: 'none',
						  title: "该手机号码已被注册!"
						})
						return false;
					}else{
						if(this.registerForm.btnText!='发送'){
							return false;
						}
						
						if( this.registerForm.code==null || this.registerForm.code.toLocaleLowerCase()!=this.checkCode.toLocaleLowerCase()){
							uni.showToast({
							  icon: 'none',
							  title: "短信验证码错误!"
							})
							this.checkCode=this.getCode(4);
							return false;
						}
						
						uni.request({
							method:"POST",
							url:that.api+"/api/sendPhoneCode",
							data:{
								phoneNumber:this.registerForm.uPhone
							},
							header:{
								'content-type': 'application/x-www-form-urlencoded',
							},
							success(res) {
								console.log(res);
								if(res.data=='ok'){
									uni.showToast({
									  icon: 'none',
									  title: "发送成功"
									})
									that.registerForm.btnText=60;
									let djs=setInterval(()=>{
										if(that.registerForm.btnText>=1){
											that.registerForm.btnText--;
										}else{
											that.registerForm.btnText="发送";
											clearInterval(djs);
										}
										
									},1000)
								}
							}
						})
						
					}
					
					
					
				})
				
				
				
			},
			 getCode(n) {//验证码生成
				  var all = "azxcvbnmsdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP0123456789";
				  var b = "";
				  for (var i = 0; i < n; i++) {
				   var index = Math.floor(Math.random() * 62);
				   b += all.charAt(index);
				 
				  }
				  return b;
			 },
			
			onInputUNumber(e){
				this.$nextTick(function(){
					this.loginForm.uNumber=e.target.value.replace(/[/W]/g,'')
				})
			},
			/**查询手机号码是否注册
			 * @param {Object} phoneNumber手机号码
			 */
			async checkPhoneNumberIsRegister(phoneNumber){
				let isRegister=false;
				let that=this;
				let p=await new Promise(function(resolve, reject) {
					uni.request({
						method:"POST",
						url:that.api+"/api/checkPhoneNumberIsRegister",
						data:{
							phoneNumber : phoneNumber
						},
						header:{
							'content-type': 'application/x-www-form-urlencoded',
						},
						success(res) {
							resolve(res.data);
							
						}
					})
				})
				
				if(p=="ok"){
					
					isRegister=false;
					console.log(isRegister);
				}else{
					isRegister=true;
					console.log(isRegister);
				}
				
				return isRegister;
			},
			doLogin(){
				let that=this;
				
				
				
				let loginRules = [
					
					 {name: 'uNumber', type: 'required', errmsg: '请输入账号'},
			
					
				  {name: 'uPassword', type: 'required', errmsg: '请输入密码'},
				  {name: 'uPassword', type: 'regex', regex: /[a-zA-Z0-9_]{6,12}$/, errmsg: '密码只能输入6-12位英文数字下划线'},
				  {name: 'code', type: 'required', errmsg: '请输入验证码'},
				  // {name: 'pwd2', type: 'eq', eqName: 'pwd', required: true, errmsg: '两次输入密码不一致'},
				   
				]
				let valLoginRes = this.$validate.validate(this.loginForm, loginRules)
				if (!valLoginRes.isOk) {
				  uni.showToast({
				    icon: 'none',
				    title: valLoginRes.errmsg
				  })
				  return false
				}
				
				if(that.loginForm.uNumber==null||that.loginForm.uNumber==''||that.loginForm.uPassword==null||that.loginForm.uPassword==''){
					uni.showToast({
					    title: '您有未输入的项',
					    duration: 1000,
						icon: 'none'
					});
					return false;
				}
				
				if(this.loginForm.code.toLocaleLowerCase()!=this.checkCode.toLocaleLowerCase()){
					uni.showToast({
					  icon: 'none',
					  title: "验证码错误!"
					})
					this.checkCode=this.getCode(4);
					return false;
				}
				
				uni.request({
					url:that.api+"/api/userLogin",
					method:"POST",
					data:that.loginForm,
					// header:{
					// 	'content-type': 'application/x-www-form-urlencoded',
					// },
					success(res) {
						
						if(res.data==''){
							uni.showModal({
							    title: '提示',
							    content: '账号或者密码不正确',
								showCancel:false,
								success() {
									that.loginForm.uPassword=null;
								}
							});
						}else{
							uni.setStorageSync("userInfo",res.data);
							// uni.switchTab({url:"/pages/mine/index"});
							uni.reLaunch({
							    url: "/pages/mine/index"
							});
						}
						
					}
				})
			},
			checkUNumber(e){//判断账号是否重复
			if(e.detail.value.length==0)return;
			let that=this;
			
		
				 uni.request({
						method:"POST",
						url:that.api+"/api/checkUserExis",
						data:{
							uNumber:e.detail.value
						},
						header:{
							'content-type': 'application/x-www-form-urlencoded',
						},
						success(res) {
							console.log(res);
							if(res.data){
								uni.showModal({
									title:'提示',
									content:'账号已存在!请更换',
									showCancel:false,
									success(res1) {
										
									}
								})
							that.registerForm.uNumber=null;
							}
							
						}
					})
			},
			tab(index) {
				this.checkCode=this.getCode(4);
				this.tabIndex = index;
			},
			goLogin() {
				uni.navigateTo({
					url: '/pages/auth/login/login'
				})
			},
			goRegister() {
				// uni.navigateTo({
				// 	url: '/pages/auth/register/register'
				// })
				this.tabIndex=1;
			},
		 async	Register(){
				let that=this;
				
				let registerRules = [
					 {name: 'uNumber', type: 'required', errmsg: '请输入账号'},
					{name: 'uNumber', type: 'regex', regex: /^[a-zA-Z]{1}[a-zA-Z0-9]{5,16}$/, errmsg: '账号需字母开头的6-16位数字、字母'},
					{name: 'uName', type: 'required', errmsg: '请输入昵称'},
					{name: 'uName', type: 'regex',regex:/^[\u4e00-\u9fa5_a-zA-Z0-9]{3,10}/, errmsg: '昵称只能说3-10位中文、英文、数字'},
				  {name: 'uPassword', type: 'required', errmsg: '请输入密码'},
				  {name: 'uPassword', type: 'regex', regex: /[a-zA-Z0-9_]{6,12}$/, errmsg: '密码只能输入6-12位英文数字下划线'},
				  {name: 'uPassword2', type: 'required', errmsg: '请输入确认密码'},
				  {name: 'code', type: 'required', errmsg: '请输入验证码'},
				  {name: 'uPassword2', type: 'eq', eqName: 'uPassword', required: true, errmsg: '两次输入密码不一致'},
				  {name: 'uPhone', type: 'required', errmsg: '请输入手机号码'},
				  {name: 'phoneCode', type: 'required', errmsg: '请输入验证码'},
				   
				]
				let valLoginRes = this.$validate.validate(this.registerForm, registerRules)
				if (!valLoginRes.isOk) {
				  uni.showToast({
				    icon: 'none',
				    title: valLoginRes.errmsg
				  })
				  return false
				}

				if(this.registerForm.code.toLocaleLowerCase()!=this.checkCode.toLocaleLowerCase()){
					uni.showToast({
					  icon: 'none',
					  title: "验证码错误!"
					})
					this.checkCode=this.getCode(4);
					return false;
				}
				
	
					uni.request({
						url:that.api+"/api/userRegister?phoneCode="+that.registerForm.phoneCode,
						method:"POST",
						data:that.registerForm,
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
									content:'注册成功!',
									showCancel:false,
									success(res1) {
										if(res1.confirm){
											uni.setStorageSync("userInfo",res.data.data);
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
			goForget() {
				uni.navigateTo({
					url: '/pages/auth/forget/forget'
				})
			},
			
		}
	}
</script>

<style lang="scss">
	
</style>
