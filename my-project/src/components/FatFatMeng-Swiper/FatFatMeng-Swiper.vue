<template>
	<view>
		<!-- 轮播图 -->
		<!-- indicator-dots这个是官方的指示器，把他关闭 -->
		<!-- 项目里用的自定义美化的指示器，比官方的好看 -->
		<view :class="swiperClassName">
			<swiper class="swiper"  
			:style="swiperStyleClass" 
			:indicator-dots="false" 
			:autoplay="true" 
			:circular="true"
			:interval="3000" 
			:duration="1000"
			indicator-active-color="#333"
			@change="change"
			>
				<swiper-item 
				v-for="(item,index) in SwiperImglist"
				:key="index"
				:current="index"
				>
					<image 
					class="Imges" :src="item.image" mode="aspectFill"
					></image>
					<!-- #ifdef APP-NVUE || APP-PLUS-NVUE -->
					<image class="ImagesTextbg" src="/static/FatFatMeng-apps/FatFatMeng-image/FatFatMeng-style/FatFatMeng-SwiperTextbg2.png" mode="widthFix"></image>
					<!-- #endif -->
					<view class="title">
						<text class="text">{{item.title}}</text>
					</view>
				</swiper-item>
			</swiper>
			<!-- 自定义指示器 [官方的不好看，也不能自定义设置] -->
			<view class="indicator-dots-zdy-right"
			>   <view class="w720">
					<view v-for="(item,index) in SwiperImglist" :key="index"
					class="indicator-dots-item"
					:class="current==index ? 'dots-item-current': 'no'"
					>
						<view class="radio" :class="current==index ? 'current': 'no'"></view>
					</view>
				</view>
			</view>
		</view>
		
		
		<!--  -->
	</view>
</template>

<script>
	export default {
		name:"FatFatMeng-Swiper",
		props:{
			SwiperImglist:{
				type:Array,
				default:''
			},
			swiperStyleClass: {
				type:Array,
				default:''
			},
			swiperClassName: {
				type:String,
				default:''
			}
		},
		data() {
			return {
				// 这个是为了让自定义的指示器再对应的坐标高亮
				// 官方的指示器不能自定义，而且很丑！
				current:0//当前指示器所在的坐标
			};
		},
		methods:{
			change(e) {
				console.log(e.detail.current)
				this.current = e.detail.current
			}
		}
	}
</script>

<style lang="scss">
	// 老项目样式---直接删除即可
	// .swiperClassX {
	// 	display: flex;
	// 	width: 750rpx;
	// 	justify-content: center;
	// 	align-items: center;
	// 	swiper {
	// 		width: 750rpx;
	// 		.Imges {
	// 			//border-radius:16rpx!important;
	// 			width: 750rpx;
	// 		}
	// 		.title {
				
	// 		}
	// 	}
	// }
	
	swiper,.swiper {
		justify-content: center;
		align-items: center;
		/* #ifdef H5 || MP || APP-VUE */
		display: flex;
		/* #endif */
		width: 750rpx;
		//height: 456rpx;
		max-height: 420rpx!important;
		border: 0;
		margin: 0;
		padding: 0;
		right: 0;
		left: 0;
		view{
			/* #ifdef H5 || MP || APP-VUE */
			display: flex;
			/* #endif */
		}
		.Imges {
			/* #ifdef H5 || MP || APP-VUE */
			width: 100%;
			height: 100%;
			/* #endif */
			width: 750rpx;
			position: relative;
		}
		.ImagesTextbg {
			width: 750rpx;
			position: absolute;
			bottom: 0;
		}
		.title {
			width: 750rpx;
			position: absolute;
			bottom: 0;
			//padding: 10rpx 0;
			//height: 60rpx;
			//background-color: rgba(0, 0, 0, .2);
			//background-color: rgba(0, 0, 0, .2);
			justify-content: center;
			align-items: center;
			/* #ifdef APP-VUE || APP-PLUS || H5 || MP */
			background-image: linear-gradient(transparent, rgba(0, 0, 0, .85));
			/* #endif */
			
			/* #ifdef APP-NVUE || APP-PLUS-NVUE */
			// NVUE下不支持设置背景渐变透明度，透明度是无效的，这里已经换成了image引用图片作为text的渐变背景【.ImagesTextbg】
			//background-image: linear-gradient(180deg,transparent 70%,rgba(0,0,0,.6) 0);
			/* #endif */
			padding-top: 40rpx;
			padding-bottom: 20rpx;
			padding-left: 20rpx;
			padding-right: 20rpx;
			
		}
	}
	text,.text {
		color: #ffffff;
		width: 720rpx;
		font-size: 32rpx;
		overflow: hidden!important;
		text-overflow: ellipsis!important;
		white-space: nowrap!important;
	}
	swiper,.swiper {
		position: relative;
	}
	// 自定义指示器
	.indicator-dots-zdy-right {
		position: absolute;
		//bottom: 0;
		// swiper[max-weidth:420rpx]
		top: 370rpx;
		/* #ifndef APP-NVUE || APP-PLUS-NVUE */
		display: flex;
		/* #endif */
		//background-color: #fff;
		height: 50rpx;
		width: 750rpx;
		align-items: center;
		justify-content: center;
		/* #ifndef APP-NVUE || APP-PLUS-NVUE */
		view {
			display: flex;
		}
		/* #endif */
		.w720 {
			width: 720rpx;
			flex-direction: row;
			flex-wrap: nowrap;
			align-items: center;
			justify-content: flex-end;//放到右边好看些
			// 未选中的指示器默认大小
			.indicator-dots-item,.radio{
				width: 20rpx;
				height: 14rpx;
				border-radius: 10rpx;
				margin-right: 10rpx;
				.radio {
					background-color: rgba(255,255,255,.5);
					margin-right: 0;
				}
				// 通用选中指示器点的颜色
				.current {
					background-color: #FFFFFF;
				}
			}
			// 选中的指示器大小
			.dots-item-current {
				width: 30rpx;
				// 通用选中指示器点的大小
				.current {
					width: 30rpx;
				}
			}
		}
	}
	
	
	
	
	
</style>
