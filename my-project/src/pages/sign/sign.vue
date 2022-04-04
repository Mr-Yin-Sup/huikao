<template>
	<view>
		<view >
			<view class="cu-list menu margin-top">

				<!-- <block  >
				
				
					<view class="cu-item " style="min-height: 0 ;" @click="goToGrid(1)">
						
						<view class="content">
						
							<text class="text-grey">交通标志大全</text>
						</view>
						<view class="action">
							<text class="text-grey text-sm"> <text class="lg text-gray cuIcon-right" ></text></text>
						</view>
					
				
					</view>
				
					<view class="cu-list grid col-4 no-border" style="padding: 0;border-bottom: #ccc 2px solid; "  @click="goToGrid(1)" >
						<view class="cu-item" v-for="(item,index) in top4img" :key="index"
							style="margin-top: 0; padding-top: 0;">
							<view >
								<img  style="height: 120rpx; padding: 0 30rpx;" :src="(api+item.icnoImg)" alt="">
							</view>
							
						</view>
					</view>
				</block>
 -->
				<block  v-for="item in top"  v-if="typeId==null && (item.icnos.length>0?true:(getTopTotal(item.children)>0?true:false))">


					<view class="cu-item" style="min-height: 0 ;" @click="goToGrid(item.icnoTypeId,item.icnoTypeId)">

						<view class="content">

							<text class="text-grey">{{item.icnoTypeName}}</text>
						</view>
						<view class="action" v-if="item.children!=null">
							<text
								class="text-grey text-sm">{{item.children.length==0?item.icnos.length:getTopTotal(item.children)}}
								<text class="lg text-gray cuIcon-right"></text></text>
						</view>


					</view>

					<view class="cu-list grid col-4 no-border" style="padding: 0;border-bottom: #ccc 2px solid; "
						@click="goToGrid(item.icnoTypeId,item.icnoTypeId)">
						<view class="cu-item" v-if="item.icnos.length>0&& item.children!=null"
							v-for="(item1,index) in item.icnos.slice(0,4)" :key="index"
							style="margin-top: 0; padding-top: 0;">
							<view>
								<img  mode='aspectFit' style="height: 120rpx; padding: 0 30rpx; max-width: 150rpx; "
									:src="(api+item1.icnoImg)" alt="">
							</view>

						</view>

						<view class="cu-item" v-if="item.children.length>0 && item.children!=null"
							v-for="(item2,index) in item.children.slice(0,4)" style="margin-top: 0; padding-top: 0;">
							<view>
								<img  mode='aspectFit' style="height: 120rpx; padding: 0 30rpx;max-width: 150rpx; "
									:src="item2.icnos.length>0?api+item2.icnos[0].icnoImg:''" alt="">

							</view>

						</view>


					</view>
				</block>
				
				
				<block v-if="typeId!=null"  v-for="item in top">
				
				
					<view class="cu-item " style="min-height: 0 ;" @click="goToGrid(item.icnoTypeId,item.icnoTypeId)">
				
						<view class="content">
				
							<text class="text-grey">{{item.icnoTypeName}}</text>
						</view>
						<view class="action">
							<text class="text-grey text-sm"> {{item.icnos.length}}<text class="lg text-gray cuIcon-right"></text></text>
						</view>
				
				
					</view>
				
					<view class="cu-list grid col-4 no-border" style="padding: 0;border-bottom: #ccc 2px solid; "
						@click="goToGrid(item.icnoTypeId,item.icnoTypeId)">
						<view class="cu-item" v-for="(item1,index) in item.icnos.slice(0,4)" :key="index"
							style="margin-top: 0; padding-top: 0;">
							<view>
								<img style="height: 120rpx; padding: 0 30rpx;" :src="(api+item1.icnoImg)" alt="">
							</view>
				
						</view>
					</view>
				</block>



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
				top4img: null,
				top: null,
				typeId: null,
			}
		},
		methods: {
			goToGrid(id, typeId) {
				let l=this.top.filter(item=>item.icnoTypeId==id)[0];
				if (l.children==undefined || l.children.length==0) {
					uni.navigateTo({
						url: "../signGrid/signGrid?id=" + id + '&typeId=' + typeId
					})
				} else {
					uni.navigateTo({
						url: "../sign/sign?typeId=" + typeId
					})
				}

			},
			getTopTotal(childrens) {
			
				let sum = 0;
				for (let i = 0; i < childrens.length; i++) {
					sum += childrens[i].icnos.length;
				}
				return sum;
			}
		},
		onLoad(options) {
			uni.showLoading({
				title: '加载中',
				mask: true
			});
			let that = this;
			that.typeId = options.typeId;
			if (options.typeId != undefined) {

				uni.request({
					method: "POST",
					url: that.api + '/api/findIcnoTypeByparent',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
					},
					data: {
						id: options.typeId
					},
					success(res) {
						that.top = res.data.top.filter(item => item.icnos.length > 0 );
						uni.setNavigationBarTitle({
							title:that.top[0].parentType.icnoTypeName
						})
						//console.log(that.top);

					},
					fail(err) {
						console.log(err);
					}


				})

			} else {
				uni.request({
					method: "POST",
					url: this.api + '/api/findTopIcnoSType',
					//header:{'Content-Type': 'application/x-www-form-urlencoded'},

					success(res) {
						that.top = res.data.top;
						that.top = that.top.filter(item => item.children.length > 0 || item.icnos.length > 0)
					

					},
					fail(err) {
						console.log(err);
					}


				})
			}

			setTimeout(function() {
				uni.hideLoading();
			}, 100);
		}
	}
</script>

<style>
	
</style>
