<template>
	<view :style="background" style="position: relative; z-index: 99999; width: 750rpx;">
		<view :style="{'height': `${placeholderHeight}px`}"></view>
		<view style="position: fixed; width: 750rpx;" :style="{'top':`${statusBarHeight}px`}" v-if="title !== ' '" class="h100 flex a-center">
			<view class="w160 flex a-center ml20">
				<uni-icons @click="goBack" v-if="isBack" :color="inverse?'#ffffff':'#000000'" type="arrowleft" size="20"></uni-icons><slot class="left"><text @click="goBack" class="fs30" :class="inverse?'text-white':'text-default'">{{backText}}</text></slot>
			</view>
			<view class="flex-1 flex aj-center">
				<slot><text class="fw30 fw600" :class="inverse?'text-white':'text-default'">{{title}}</text></slot>
			</view>
			<view class="w160 mr30 flex" style="justify-content: flex-end;">
				<slot name="right"></slot>
			</view>
		</view>
	</view>
</template>

<script>
	import globalMixin from '@/common/mixin/mixin.js'
	export default{
		mixins:[globalMixin],
		data() {
			return {
				statusBarHeight: 0,
			}
		},
		props:{
			fixed: {
				type: Boolean,
				default: false
			},
			isBack: {
				type: Boolean,
				default: true
			},
			backText: {
				type: String,
				default:''
			},
			inverse: {
				type: Boolean,
				default: false
			},
			background: {
				type: Object,
				default() {
					return {'background-color': '#ffffff'}
				}
			},
			height:{
				type: Number,
				default: 100
			},
			title: {
				type: String,
				default: ' '
			}
		},
		computed:{
			placeholderHeight() {
				const info = uni.getSystemInfoSync()
				return info.statusBarHeight + uni.upx2px(this.height)
			}
		},
		created() {
			const info = uni.getSystemInfoSync()
			this.statusBarHeight = info.statusBarHeight
		},
	}
</script>

<style>
</style>
