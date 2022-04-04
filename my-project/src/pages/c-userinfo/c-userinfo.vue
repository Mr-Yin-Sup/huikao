<template>
	
	<view class="container">
	
		<view class="ui-all">
			
			<view class="avatar" @tap="avatarChoose">
				<view  class="imgAvatar">
					<view class="iavatar" :style="'background: url('+api+form.uImg+') no-repeat center/cover #eeeeee;'"></view>
				</view>
				<text v-if="form.uImg">修改头像</text>
				<text v-if="!form.uImg">授权微信</text>
				<button v-if="!form.uImg" open-type="getUserInfo" @tap="getUserInfo" class="getInfo"></button>
			</view>
			<view class="ui-list">
				<text>账号</text>
				<input type="text" :placeholder="form.uNumber"  disabled='true' placeholder-class="place" />
			</view>
			<view class="ui-list">
				<text>手机号码</text>
				<input type="text" :placeholder="form.uPhone"  disabled='true' placeholder-class="place" />
			</view>
			<view class="ui-list">
				<text>昵称</text>
				<input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" type="text" maxlength="10" :placeholder="form.uName" v-model="form.uName" @input="bindnickName" placeholder-class="place" />
			</view>
			<!-- <view class="ui-list">
				<text>手机号</text>
				<input v-if="mobile" type="tel" :placeholder="value" :value="mobile" @input="bindmobile" placeholder-class="place" />
				<button v-if="!mobile" open-type="getPhoneNumber" @getphonenumber="getphonenumber" class="getInfo bun">授权手机号</button>
			</view> -->
			<view class="ui-list right">
				<text>性别</text>
				<picker @change="bindPickerChange" mode='selector'  range-key="name" v-model="form.uSex" :range="sex">
					<view class="picker">
						{{sex[form.uSex].name}}
					</view>
				</picker>
			</view>
			<view class="ui-list right" @click="shwoModView=true">
				<text>密码</text>
				<input type="text" disabled="flase" placeholder="点击修改密码" placeholder-class="place" />
			</view>
			
			
			<neil-modal 
			    :show="shwoModView" 
			    @close="shwoModView=false" 
			    title="修改密码" 
				:auto-close='false'
			   style="z-index: 1;"
			    @cancel="shwoModView=false;modPwdForm.password=null;modPwdForm.password2=null;modPwdForm.oldpassword=null" 
			    @confirm="onConfirmClick">
				
				 <view style="min-height: 90upx;padding: 32upx 24upx;">
					 <view class="ui-list">
					 	<text>旧密码</text>
					 	<input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" password maxlength="12" placeholder="请输入旧密码" v-model="modPwdForm.oldpassword" placeholder-class="place" />
					 </view>
				        <view class="ui-list">
				        	<text>新密码</text>
				        	<input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" password maxlength="12" placeholder="请输入新密码" v-model="modPwdForm.password" placeholder-class="place" />
				        </view>
						<view class="ui-list">
							<text>确认密码</text>
							<input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" password maxlength="12" placeholder="请确认密码" v-model="modPwdForm.password2" placeholder-class="place" />
						</view>
				    </view>
			</neil-modal>
			
			
			
			<button class="save" style="background-color: #0074D9;" @tap="savaInfo">保 存 修 改</button>
			<button class="save" style="background-color: #0074D9;" @tap="goBack">返回</button>
			
			
		</view>

		
			
	</view>
	
</template>

<script>
	import neilModal from '@/components/neil-modal/neil-modal.vue';

	export default {
	 components: {neilModal},
		data() {
			return {
				//#ifdef MP-WEIXIN
					// 为了解决微信小程序无法使用全局变量修改所以每个文件都设置了服务端地址
				api: 'http://192.168.2.100:8881',
				//#endif
				value: '请填写',
				sex: [{
					id: 0,
					name: '女'
				}, {
					id: 1,
					name: '男'
				}],
				index: 1,
				region: ['请填写'],
				date: '请填写',
				avater: '',
				description: '',
				url: '',
				nickName: '',
				mobile: '',
				headimg: '',
				form:{
					uName:null,
					uPassowrd:null,
					uSex:null,
					uImg:null,
					uNumber:null,
				},
				modPwdForm:{
					oldpassword:null,
					password:null,
					password2:null
				},
				shwoModView:false,

			}

		},
		methods: {
			onConfirmClick(){
				let that=this;
				if(that.modPwdForm.password==uni.getStorageSync("userInfo").uPassword){
					uni.showToast({
						title:"新密码不能和旧密码相同",
						icon:"error"
					})
					return;
				}
				that.modPwdForm.uPassword=uni.getStorageSync("userInfo").uPassword;
				let Rules = [
				  {name: 'oldpassword', type: 'required', errmsg: '您还没有输入旧密码'},
				  {name: 'oldpassword', type: 'eq', eqName: 'uPassword', required: true, errmsg: '旧密码不正确'},
				  {name: 'password', type: 'required', errmsg: '您还没有输入新密码'},
				  {name: 'password', type: 'regex', regex: /[a-zA-Z0-9_]{6,12}$/, errmsg: '密码只能输入6-12位英文数字下划线'},
				  {name: 'password2', type: 'required', errmsg: '请输入确认密码'},
				  {name: 'password2', type: 'eq', eqName: 'password', required: true, errmsg: '两次输入密码不一致'},
				   
				]
				let valLoginRes = this.$validate.validate(this.modPwdForm, Rules)
				if (!valLoginRes.isOk) {
				  uni.showToast({
				    icon: 'none',
				    title: valLoginRes.errmsg
				  })
				  return false
				}
				
				uni.request({
					data:{
						password:that.modPwdForm.password,
						uid:uni.getStorageSync("userInfo").uId
					},
					url:that.api+"/api/userModPassword",
					method:"POST",
					header:{
						'content-type': 'application/x-www-form-urlencoded',
					},
					success(res) {
						if(res!=''){
							uni.setStorageSync("userInfo",res.data);
							uni.showModal({
								content:"修改成功",
								showCancel:false,
								title:"成功"
							});
							that.modPwdForm.password=null;
							that.modPwdForm.password2=null;
							that.modPwdForm.oldpassword=null;
							that.shwoModView=false;
						}
					}
				})
				
			},
			 close() {
			            // TODO 做一些其他的事情，before-close 为true的情况下，手动执行 close 才会关闭对话框
			            // ...
			            this.$refs.popup.close()
			        },
			 openModPassword() {
			        this.$refs.modal.showModal() // 显示
			        },
			goBack(){
				uni.reLaunch({
					url: "/pages/mine/index"
				});
				
			},
			bindPickerChange(e) {
				this.form.uSex = e.detail.value;
				
			},
			bindRegionChange(e) {
				this.region = e.detail.value;
				
			},
			bindDateChange(e) {
				this.date = e.detail.value;
				
			},
			bindnickName(e) {
				this.nickName = e.detail.value;
				
			},
			bindmobile(e) {
				this.mobile = e.detail.value;
				
			},
			binddescription(e) {
				this.description = e.detail.value;
				
			},
			avatarChoose() {
				let that = this;
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album', 'camera'],
					success(res) {
						if(res.tempFiles[0].size>=(1024*1024)){
							uni.showToast({
								icon:"error",
								title:"上传的图片不能大于1Mb!"
							})
							return;
						}
						console.log(res);
						// tempFilePath可以作为img标签的src属性显示图片
						that.imgUpload(res.tempFilePaths);
						const tempFilePaths = res.tempFilePaths;
					}
				});
			},
			 getUserInfo () {
				  uni.getUserProfile({
			      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
			      success: (res) => {
			       console.log(res);
				   uni.showToast({
							   title: '已授权',
							   icon: 'none',
							   duration: 2000
							   }) 
			      }
			    })
			    } ,
				 getphonenumber(e){
					if(e.detail.iv){
					  console.log(e.detail.iv) //传后台解密换取手机号
						  uni.showToast({
							   title: '已授权',
							   icon: 'none',
							   duration: 2000
							   }) 
					}
								  },
			savaInfo() {
				let that=this;
				
				let Rules = [
					{name: 'uName', type: 'required', errmsg: '请输入昵称'},
					{name: 'uName', type: 'regex',regex:/^[\u4e00-\u9fa5_a-zA-Z0-9]{3,10}/, errmsg: '昵称只能说3-10位中文、英文、数字'},
				]
				let valLoginRes = this.$validate.validate(this.form,Rules)
				if (!valLoginRes.isOk) {
				  uni.showToast({
				    icon: 'none',
				    title: valLoginRes.errmsg
				  })
				  return false
				}
				uni.request({
					url:that.api+"/api/updateUserInfo",
					method:"POST",
					data:that.form,
					success(res) {
						console.log(res);
						if(res.data!=''){
							uni.setStorageSync("userInfo",res.data);
							uni.showModal({
							   showCancel:false,
							    content: '修改成功',
							    success: function (res) {
							        that.goBack();
							    }
							});
						}
					}
				})

			},
			isPoneAvailable(poneInput) {
				var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
				if (!myreg.test(poneInput)) {
					return false;
				} else {
					return true;
				}
			},
			
			imgUpload(file) {
				let that = this;
				uni.uploadFile({
					
					url:that.api+'/upload', //需传后台图片上传接口
					filePath: file[0],
					name: 'file',
					formData: {
						type: 'user_headimg'
					},
					success: function(res) {
						
						var data = JSON.parse(res.data);
						console.log(data.Data.src);
						data = data.Data;
						that.form.uImg =  data.src;


						
					},
					fail: function(error) {
						console.log(error);
					}
				});
			},
	
		},
		onLoad() {			
			let userInfo=uni.getStorageSync("userInfo")
			this.form=userInfo;
			console.log(this.form);
		}

	}
</script>

<style lang="scss">
	.container {
		display: block;
	}

	.ui-all {
		padding: 20rpx 40rpx;

		.avatar {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			.imgAvatar {
				width: 140rpx;
				height: 140rpx;
				border-radius: 50%;
				display: inline-block;
				vertical-align: middle;
				overflow: hidden;

				.iavatar {
					width: 100%;
					height: 100%;
					display: block;
				}
			}

			text {
				display: inline-block;
				vertical-align: middle;
				color: #8e8e93;
				font-size: 28rpx;
				margin-left: 40rpx;
			}

			&:after {
				content: ' ';
				width: 20rpx;
				height: 20rpx;
				border-top: solid 1px #030303;
				border-right: solid 1px #030303;
				transform: rotate(45deg);
				-ms-transform: rotate(45deg);
				/* IE 9 */
				-moz-transform: rotate(45deg);
				/* Firefox */
				-webkit-transform: rotate(45deg);
				/* Safari 和 Chrome */
				-o-transform: rotate(45deg);
				position: absolute;
				top: 85rpx;
				right: 0;
			}
		}

		.ui-list {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			text {
				color: #4a4a4a;
				font-size: 28rpx;
				display: inline-block;
				vertical-align: middle;
				min-width: 150rpx;
			}

			input {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
			}
			button{
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				background: none;
				margin: 0;
				padding: 0;
				&::after{
					display: none;
				}
			}
			picker {
				width: 90%;
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				position: absolute;
				top: 30rpx;
				left: 150rpx;
			}

			textarea {
				color: #030303;
				font-size: 30rpx;
				vertical-align: middle;
				height: 150rpx;
				width: 100%;
				margin-top: 50rpx;
			}

			.place {
				color: #999999;
				font-size: 28rpx;
			}
		}

		.right:after {
			content: ' ';
			width: 20rpx;
			height: 20rpx;
			border-top: solid 1px #030303;
			border-right: solid 1px #030303;
			transform: rotate(45deg);
			-ms-transform: rotate(45deg);
			/* IE 9 */
			-moz-transform: rotate(45deg);
			/* Firefox */
			-webkit-transform: rotate(45deg);
			/* Safari 和 Chrome */
			-o-transform: rotate(45deg);
			position: absolute;
			top: 30rpx;
			right: 0;
		}

		.save {
			background: #030303;
			border: none;
			color: #ffffff;
			margin-top: 40rpx;
			font-size: 28rpx;
		}
	}
</style>
