<template>
	<view>
		<view v-if="title" class="o-title">{{title}}</view>
		<view v-else class="space"></view>
		<view :class="['grid', Square]"><slot /></view>
	</view>
</template>

<script>
	export default {
		name:'grid',
		props:{
			col:{
				type:[Number,String],
				default:3
			},
			border:{
				type:[Number,String],
				default:'true'
			},
			title:{
				type:String,
				default:''
			},
			square:{//是否将格子设为正方形
				type:[Boolean,String],
				default:false
			},
			iconSize:{//图标尺寸 sm/md/lg
				type:String,
				default:'md'
			}
		},
		
		provide() {
			return {
				col:this.col,
				border:this.border,
				iconsize:this.iconSize,
			}
		},
		
		computed:{
			Col(){
				if (this.col < 2) return 2;
				else if(this.col > 5) return 5;
				else return this.col;
			},
			Square(){
				if (this.square == 'true' || this.square == true) return 'square';
				else return ''
			}
		}
	}
</script>

<style lang="scss">

	.o-title{
		font-size:28rpx;
		color:#888;
		background-color:#f6f6f6;
		height:60rpx;
		line-height:60rpx;
		position:relative;
		padding-left:30rpx;
		padding-top:6rpx;
		box-sizing:content-box;
		&::after{
			position:absolute;
			content:"";
			left:10rpx;top:24rpx;
			height:24rpx;width:6rpx;
			border-radius:30px;
			background-color:#999;
			z-index:2;
		}
	}
	.space{
		background-color:#f6f6f6;
		height:8rpx;
	}
	.grid {		width: 100%;		/* #ifdef MP */		position: relative;		box-sizing: border-box;		overflow: hidden;		/* #endif */		/* #ifndef MP */		display: flex;
		flex-direction: row;
		flex-wrap: wrap;		align-items: center;		/* #endif */	}

</style>
