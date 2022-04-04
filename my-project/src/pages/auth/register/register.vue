<template>
	<view class="abslrtb flex-column a-center wrap">
		<qui-navbar title="注册"></qui-navbar>
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
					<input placeholder-class="placeholder" class="qui-input" type="text" value="" placeholder="请输入手机号" />
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>验证码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_code.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input placeholder-class="placeholder" :password="password" class="qui-input" type="text" value="" placeholder="请输入验证码" />
				</view>
				<view>
					<text style="opacity: 0.8;" class="fs28 ptb20 main-color yzm">获取验证码</text>
				</view>
			</view>
			<view class="flex a-center form-item">
				<view class="label">
					<text>密码</text>
				</view>
				<image class="label_icon" src="/static/images/icon_pw.png" mode=""></image>
				<view class="label_fgs"></view>
				<view class="flex-1">
					<input :password="password" placeholder-class="placeholder" class="qui-input" type="text" value="" placeholder="请输入6-14位密码" />
				</view>
			</view>
			<view class="ptb30 flex">
				<label class="radio">
					<radio @click="changeCheck" :checked="checked" color="#F73131" class="qradio" value="" /><text class="fs24 text-gray">我已阅读并遵守</text> <text class="fs24 main-color underline">《用户协议》</text> <text class="fs24 text-gray">与</text> <text class="fs24 main-color underline">《隐私协议》</text>
				</label>
			</view>
		</view>
		<view class="btns">
			<view class="qbtn">
				<text class="btn-text-color fs30">立即注册</text>
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
				checked: false,
				password: true,
			}
		},
		methods: {
			changeCheck() {
				this.checked = !this.checked;
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
			}
		}
	}
</script>

<style lang="scss">
	// @import "../styles/skin01.scss";
	.qradio{
		transform: scale(0.6);
		color: #FF5A5F;
	}
</style>
