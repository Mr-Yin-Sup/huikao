<template>
	<view class="grid">
		<slot></slot>
	</view>
</template>

<script>
	import bugking7_grid_item from '../bugking7-grid-item/bugking7-grid-item.vue'
	export default {
		name:"bugking7-grid",
		props:{
			column:{
				type:Number,
				default:3
			},
			itemSpace:{
				type:Number,
				default:2
			}
		},
		comments:{
			'bugking7-grid-item':bugking7_grid_item
		},
		created() {
			uni.$on('getColumn',res=>{
				uni.$emit('setItemColumn',{column:this.$props.column,itemSpace:this.$props.itemSpace})
			})
		},
		data() {
			return {
				
			};
		}
	}
</script>

<style lang="scss" scoped>
	@import './bugking7-grid.scss';
</style>
