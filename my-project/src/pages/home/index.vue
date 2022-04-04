<template>
	<view>


		<!-- 轮播图 -->
		<swiper class="screen-swiper square-dot" :indicator-dots="true" :circular="true" :autoplay="true"
			interval="2000" duration="500">
			<swiper-item>
				<image src="/static/swing.png" mode="aspectFill"
					type='image'></image>
			</swiper-item>
			
		</swiper>
		<!-- k科目导航 -->
		<uni-segmented-control :current="current" :values="items" @clickItem="onClickItem" styleType="button"
			activeColor="#00aaff"></uni-segmented-control>

		<view class="content radius ">

			<view style="background-color: #eaeaea; " class="padding-top">
				<uni-row class="demo-uni-row ">
					<uni-col :span="7">
						<navigator
							:url="'/pages/dati/index?state=suiji&'+'cartype='+userCheckCat+'&&km='+current"
							hover-class="navigator-hover">
							<view class="demo-uni-col dark "
								style="padding-top: 50rpx;width: 100%; height: 300rpx; text-align: center;"
								@click="log">

								<text class="iconfont icon-repeat"
									style="font-size: 40px; line-height: 90rpx;color: #0ccd97;">
								</text>
								<br>
								<text> 随机练习</text>

							</view>
						</navigator>
					</uni-col>
					<uni-col :span="10">
						<navigator :url="'/pages/dati/index?state=sunxu&'+'cartype='+userCheckCat+'&km='+current"
							hover-class="navigator-hover">
							<view class="demo-uni-col light" style="width: 100%; height: 300rpx; text-align: center;">

								<view
									style="width: 200rpx;height: 200rpx; margin: 0 auto;border-radius: 100px; color: #ffffff; font-size: 30rpx;background-color: #0ccd97">
									<view style="height: 50rpx;padding-top: 60rpx;">
										<text>顺序练习</text>
									</view>
									<view style="height: 50rpx;padding-top: 45rpx;font-size: 26rpx;">
										<text>{{subjectTotal}}题</text>
									</view>
								</view>

							</view>
						</navigator>

					</uni-col>
					<uni-col :span="7">
						<navigator
							:url="'/pages/WrongQuestionIndex/WrongQuestionIndex?'+'cartype='+userCheckCat+'&km='+(current==0?1:4)"
							hover-class="navigator-hover">
							<view class="demo-uni-col dark "
								style="padding-top: 50rpx;width: 100%; height: 300rpx; text-align: center;"
								@click="log">

								<text class="iconfont icon-cuotiben" style="font-size: 40px;color: #ff5500; ">
								</text>
								<br>
								<text> 我的错题</text>

							</view>
						</navigator>
					</uni-col>
				</uni-row>
				<uni-row class="demo-uni-row ">
					<uni-col :span="7" @click="gotoScore">
							<view class="demo-uni-col dark "
								style="padding-top: 50rpx;width: 100%; height: 300rpx; text-align: center;"
								@click="gotoScore">
								<text class="iconfont icon-chengjitongji" style="font-size: 40px; color: #0081FF; ">
								</text>
								<br>
								<text>我的成绩</text>

							</view>
					</uni-col>
					<uni-col :span="10">
						<navigator :url="'/pages/examIndex/examIndex?'+'cartype='+userCheckCat+'&km='+current"
							hover-class="navigator-hover">
							<view class="demo-uni-col light" style="width: 100%; height: 300rpx; text-align: center;">

								<view
									style="width: 200rpx;height: 200rpx; margin: 0 auto;border-radius: 100px; color: #ffffff; font-size: 30rpx;background-color: #0081FF">
									<view style="height: 50rpx;padding-top: 60rpx;">
										<text>模拟考试</text>
									</view>
									<view style="height: 50rpx;padding-top: 45rpx;font-size: 26rpx;">
										<text>仿真冲刺</text>
									</view>
								</view>
							</view>
						</navigator>
					</uni-col>
					<uni-col :span="7">
						<navigator url="/pages/sign/sign" hover-class="navigator-hover">
							<view class="demo-uni-col dark "
								style="padding-top: 50rpx;width: 100%; height: 300rpx; text-align: center;"
								@click="log">

								<text class="iconfont icon-biaozhi" style="font-size: 40px; color: #0081FF; ">
								</text>
								<br>
								<text>图标识记</text>

							</view>
						</navigator>
					</uni-col>
				</uni-row>

			</view>



		</view>

		<view class="cu-list menu">

			<view class="cu-item" @click="xuanzheCar" style="border-bottom: #ccc 1px solid; ">
				<!-- <navigator :url="'/pages/WrongQuestionIndex/WrongQuestionIndex?'+'state=cuoti&&skin='+skin?0:1" hover-class="navigator-hover"> -->
				<view class="content">
					<text class="cuIcon-taxi text-green" style="font-size: 40rpx;"></text>
					<text class="text-grey">选择车型</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm">{{cars[userCheckCat-1]}}<text
							class="lg text-gray cuIcon-right"></text></text>
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
				items: ["科目一", "科目四"],
				current: 0,
				cars: ['小车   C1/C2/C3', '货车   A2/B2', '客车   A1/A3/B1', '摩托车   A1/A3/B1'],
				userCheckCat: 1,
				userCheckKm:1,
				subjectTotal:0,
				cats: [{
						"name": '小车',
						"text": 'C1/C2/C3',
						"icno": 'icon-car-fill'
					},
					{
						"name": '货车',
						"text": 'A2/B2',
						"icno": 'icon-icon-test1'
					},
					{
						"name": '客车',
						"text": 'A1/A3/B1',
						"icno": 'icon-gongjiaokeche'
					},
					{
						"name": '摩托车',
						"text": 'D/E/F',
						"icno": 'icon-icon-test'
					}
				]
			}
		},
		methods: {
			gotoScore(){
				if(this.checkUserLogin()==false)return;
				uni.navigateTo({
					url:'/pages/score/score?cartype='+this.userCheckCat+'&km='+this.current
				});
				
			},
			checkUserLogin(){
				console.log(uni.getStorageSync("userInfo"));
					  if(uni.getStorageSync("userInfo")==undefined || uni.getStorageSync("userInfo")==''){
					  		  uni.showToast({
					  		  	icon:"error",
					  			title:"您还没有登录!"
					  		  })
					  		  return false;
					  }
					  return true;
			},
			onClickItem(e) {
			
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex;
					
					uni.setStorageSync("userCheckKm",e.currentIndex==0?1:4)//设置用户科目缓存
					let log = this.current == 0 ? '您已切换到科目一' : '您已切换到科目四';
					uni.showToast({
						title: log,
						icon: 'none',
						duration: 1000
					});
					console.log(this.current,'curr');
					this.getTotal();
				}
			},
			log() {
				console.log(123)
			},
			selectCat(index) {
				this.userCheckCat = index;
				this.$forceUpdate();
			},
			xuanzheCar() {
				
				uni.showActionSheet({
					itemList: this.cars,
					success: (res) => {
						uni.showModal({
							title: '温馨提示',
							content: '为了避免混淆，切换后会清除答题记录、收藏等信息，您确定切换吗？',
							cancelText: '取消',
							confirmText: '确定',
							success: res1 => {
								if(res1.confirm!=true)return;
								this.userCheckCat = res.tapIndex + 1
							
								
								uni.showToast({
									title: '您的题目已切换到' + this.cars[res.tapIndex],
									icon: 'none',
									duration: 1000
								});
								
								/**
								 * 清除缓存
								 */
								uni.removeStorageSync("userSuccessKm4SubjectList");
								uni.removeStorageSync("userSuccessKm1SubjectList");
								uni.removeStorageSync("userCheckCar");
								uni.removeStorageSync("userCheckKm");
								uni.removeStorageSync("userSunXuSubjectIndex");
								uni.removeStorageSync("userSunXuSubjectKm1Index");
								uni.removeStorageSync("userErrorSubjectList");
								uni.removeStorageSync("km1SubjectList");
								uni.removeStorageSync("userSunXuSubjectKm4Index");
								uni.removeStorageSync("userErrorSubjectSkin");
								uni.setStorageSync("userCheckCar",res.tapIndex + 1);
								uni.setStorageSync("userCheckKm",1);
								this.current=0;
								this.getTotal();
							},
							fail: () => {return},
							complete: () => {return;}
						});
						
					}
				})
				
			},
			getTotal(){
				let that=this;
				uni.showLoading({title: '加载中',mask:true});
				uni.request({
					method:"POST",
					url:this.api+'/api/getSubjectTotalByCarAndKm',
					header:{'Content-Type': 'application/x-www-form-urlencoded'},
					data:{
						"sCar":this.userCheckCat,
						"sKm":this.current==0?1:4
					},
					success(res) {
						console.log(res.data);
						that.subjectTotal=res.data;
						setTimeout(function () {uni.hideLoading();}, 100);
					},
					fail(err) {
						console.log(err);
					}
					
					
				})
			},
			checkUserState(){
					  let that=this;
					  let userInfo=uni.getStorageSync("userInfo");
					  if(userInfo!==''){
						  uni.request({
						  	url:this.api+"/api/userLogin",
						  	method:"POST",
							data:{uNumber:userInfo.uNumber,uPassword:userInfo.uPassword},
							success: (res) => {
								if(res.data.uState!='0'){
									uni.showModal({
										content: '账号异常!',
										showCancel: false,
									});
									uni.removeStorageSync("userInfo");
									that.userInfo=null;
									uni.redirectTo({
										url:"/pages/mine/index"
									})
								}else{
									uni.setStorageSync("userInfo",res.data);
								
								//console.log(res.data);
								}
							}
						  })
					  }
			},

		},
		components: {

		},
		onLoad() {
			let that=this;
			console.log(uni.getStorageSync("userCheckKm"),'------------');
			that.userCheckCat=uni.getStorageSync("userCheckCar").length<=0?1:uni.getStorageSync("userCheckCar");
			that.current=uni.getStorageSync("userCheckKm")==4?1:0
			that.getTotal();
			that.checkUserState();
			
		}
	}
</script>
<style>
	@import "../../colorui/animation.css";

	.button-yuanjiao {
		width: 200rpx;
		height: 200rpx;
		display: flex;
		/* margin-top: 30rpx; */
		padding-top: 30px;
		line-height: 30rpx;
		justify-content: center;
		border-radius: 100px;
		color: #ffffff;
		background-color: #0ccd97;
		font-size: 30rpx;
		border-color: red;
		border-width: 4px;


	}

	.bg-click {
		background-color: #a7a9ff;
	}

	.bg-click_A {
		content: " ";
		width: 200rpx;
		height: 200rpx;
		display: flex;
		/* 		position: absolute;
		top: 0;
		left: 0;
		border: 1px solid rgba(0,0,0,.2);
		-webkit-transform: scale(.5);
		transform: scale(.5);
		-webkit-transform-origin: 0 0;
		transform-origin: 0 0;
		box-sizing: border-box;
		border-radius: 10px; */
	}
</style>
