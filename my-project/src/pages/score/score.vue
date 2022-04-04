<template>
	<view>
		<view v-if="userInfo!=''" style="height: 300rpx; width: 100%;background-color: cyan;">
			<view class="left text-center" style="padding-top: 50rpx;">
				<view style="font-size: 40rpx;">{{km==1?'科一预测':'科四预测'}}</view>
				<view style="font-weight: bold; padding-top: 30rpx; font-size: 60rpx;"><text style="font-size: 100rpx;">{{yuce}}</text>分</view>
			</view>
			<view class="right text-center">
				<image :src="api+userInfo.uImg" style="width: 150rpx;height: 150rpx; margin-top: 40rpx; border-radius: 100rpx;margin: 40rpx auto 0 auto;"></image>
				<view style="font-size: 40rpx;"><text>{{userInfo.uName}}</text></view>
			</view>
			<block style="clear: both;"></block>
		</view>
		<view class="count">
			<view class="count-item">
				<view>
					<text>{{scores.length}}</text>次
				</view>
				<view>
					累计考试
				</view>
			</view>
			<view class="count-item">
				<view>
					<text>{{hege}}</text>次
				</view>
				<view>
					合格次数
				</view>
			</view>
			<view class="count-item">
				<view>
					<text>{{subSum}}</text>题
				</view>
				<view>
					累计考试答题数
				</view>
			</view>
		</view>
		<view class="text-center padding-top before-socre">
			历史成绩
		</view>
		<view v-if="scores.length>0">
			<view class="score text-center" v-for="item in scores">
				<view class="score-item" style="width: 20%;">
					{{item.score}}分
				</view>
				<view class="score-item">
					{{item.useTime}}
				</view>
				<view class="score-item" style="width: 30%;">
					{{ item.examTime}}
				</view>
				<view class="score-item">
					{{item.chenghao}}
				</view>
			</view>
		</view>
		<view v-else class="text-center" style="font-size: 40rpx; margin-top: 20rpx;color: #8F939C;">
			<text >您还没有考试记录哦</text>
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
				userInfo:'',
				scores:[],
				hege:0,
				subSum:0,
				yuce:0,
				km:1,
			}
		},
		methods: {
			chenghao(score){//获取称号
			let str='';
				switch (Math.floor(score/10)){
					case 10: str='驾考传奇'; break;
					case 9: str='驾考车神';break;
					case 8: str='预备车手';break;
					case 7: str='进击的菜鸟';break;
					case 6: str='进击的菜鸟';break;
					default:str='马路杀手';break;
				}
			
				return str;
			},
			init(options){
				let that=this;
				uni.showLoading({title: '加载中',mask:true});
				let km=this.km=options.km==0?1:4;
				this.userInfo=uni.getStorageSync("userInfo");
				uni.request({
					url:this.api+"/api/getUserScores",
					data:{
						uId:this.userInfo.uId,
						km:km,
						carType:options.cartype
					},
					method:"POST",
					success:(res)=>{
						if(res.data!=''){
							let data=res.data;
							let yuce_sum=0;//成绩预测设置初始值
							for(let i=0;i<data.length;i++){
								data[i].useTime=new Date(data[i].useTime);
								
								let f=data[i].useTime.getMinutes();
								let m=data[i].useTime.getSeconds();
								data[i].useTime=f+'分'+m+'秒';//考试用时
								
								let date1=new Date(data[i].examTime);
								date1=date1.getFullYear()+'年'+(date1.getMonth()+1)+'月'+date1.getDate()+'日';//
								data[i].examTime=date1;//考试日期
								data[i].chenghao=that.chenghao(data[i].score);//成绩称号
								that.subSum+=data[i].successCount+data[i].errorCount;//总答题数
								yuce_sum+=data[i].score;
							}
							that.scores=data;
							that.yuce=Math.floor(yuce_sum/data.length);//设置成绩预测
							 that.hege=data.filter((item)=>{return item.score>=90}).length;//合格次数
						 
						}
						
					},fail: (err) => {
						console.log(err);
					}
				})
				
				
				setTimeout(function () {uni.hideLoading();}, 100);
			}
		},
		onLoad(options) {
			this.init(options);
			// uni.getStorage({
			//     key: 'userinfo',
			//     success: function (res) {
			//         console.log(res.data);
			//     }
			// });
			
		}
	}
</script>

<style>

.left{
	height: 100%;
	width: 40%;

	float: left;
}
.right{
	height: 100%;
	width: 40%;
	float: right;

}
.count{
	height:200rpx;
	width: 100%;
}

.count .count-item{
	height:100%;
	width: 33.333%;
	background-color: #0CCD97;
	float: left;
	text-align: center;
	padding-top: 50rpx;
}
.count .count-item text{
	font-size: 40rpx;
	font-weight: 700;
	
}

.count .count-item view{
	padding-bottom: 30rpx;
}
.before-socre{
	font-size: 40rpx;
	padding-bottom: 10rpx;
	border-bottom: solid #ccc 3px;
}

.score{
	margin-top: 40rpx;
}
.score-item{
	float: left;
	width: 25%;
	height: 80rpx;
	
	line-height: 80rpx;
	border-bottom: #CCCCCC 1px solid;
	border-left: #CCCCCC 1px solid;
}
</style>
