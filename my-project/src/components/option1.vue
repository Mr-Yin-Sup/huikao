<template>
	<view>
		<bugking7-grid :column="2">
		    <bugking7-grid-item v-for="(item,index) in items" :key="index">
		        <view class="item_wrap">
		            {{item.title}}
		        </view>
		    </bugking7-grid-item>
		</bugking7-grid>
	</view>
</template>

<script>
	export default {
		name:"option1",
		data() {
			return {
				  items:[
				            {
				                title:'文本'
				            },
				            {
				                title:'文本'
				            }
				        ]
			};
		}
	}
</script>

<style>

</style>
