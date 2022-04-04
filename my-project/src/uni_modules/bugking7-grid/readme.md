# 宫格
## 简介

宫格组件。

## 属性

属性|类型|必填|备注
-|-|-|-
column|Number| 是| 每行显示个数，默认为3
itemSpace |Number | 是| 每个宫格之间的间距，默认为2，注：单位为百分比


## 基本用法
    <bugking7-grid :column="2">
		<bugking7-grid-item v-for="(item,index) in items" :key="index">
			<view class="item_wrap">
				{{item.title}}
			</view>
		</bugking7-grid-item>
	</bugking7-grid>


	import bugking7_grid from '../../components/bugking7-grid/bugking7-grid.vue'
	import bugking7_grid_item from '../../components/bugking7-grid-item/bugking7-grid-item.vue'
    data:{
        return {
			items:[
				{
					title:'文本'
				},
				{
					title:'文本'
				}
			]
		}
    }

    methods: {
		
	}

## 兼容性

微信、QQ小程序、Android、Edge实测没问题，IOS没有本子尚未测试。


## 其他

遇到问题欢迎评论指出，感谢你的关注和支持。

[demo](https://gitee.com/bugking7_uniapp_plug/bugking7-list.git)


【蝴蝶壁纸】QQ小程序，每天分享免费壁纸！

![avatar](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-c4651ffd-8264-432b-af24-d142b3731b81/b78c6983-d943-460d-845d-906e98df5740.png)

【缘来是星座】微信小程序，每天1点、7点、17点更新十二星座运势，还可以查看你的他（她）和你是否配对奥！

![avatar](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-c4651ffd-8264-432b-af24-d142b3731b81/44fa0025-8e93-4aed-80e1-38fbf48efd5b.jpg)
