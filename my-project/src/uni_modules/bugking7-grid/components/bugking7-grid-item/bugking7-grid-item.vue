<template>
	<view class="grid-item" :style="{'width': formatItemWidth +'%','margin-left':itemSpace+'%','margin-top':itemSpace+'%'}">
		<slot></slot>
	</view>
</template>

<script>
	export default {
		name: "bugking7-grid-item",
		data() {
			return {
				column: 0,
				itemSpace: 0
			};
		},
		mounted() {
			uni.$on('setItemColumn', (res) => {
				if(this.column !==0 || this.itemSpace !==0){
					return
				}else{
					this.column = res.column
					this.itemSpace = res.itemSpace
				}
			})
			uni.$emit('getColumn', {})
		},
		computed: {
			//计算每个item的长度
			formatItemWidth: function() {
				return (100 - (this.itemSpace * (this.column + 1))) / this.column
			}
		},
		methods: {
			
		}

	}
</script>

<style scoped lang="scss">
	@import './bugking7-grid-item.scss';
</style>
