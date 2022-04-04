<template>
	<view style="background-color: red;">
		<view class="cu-list menu">
		
			<view class="cu-item"   style="border-bottom: #ccc 2px solid; " @click="gotoCollection(1)">
					<!-- <navigator :url="'/pages/WrongQuestionIndex/WrongQuestionIndex?'+'state=cuoti&&skin='+skin?0:1" hover-class="navigator-hover"> -->
				<view class="content">
					<text class="cuIcon-favorfill text-green"></text>
					<text class="text-grey">科一收藏</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm">{{km1Total}}<text class="lg text-gray cuIcon-right" ></text></text>
				</view>
				<!-- </navigator> -->
			</view>
			
			
			<view class="cu-item"  style="border-bottom: #ccc 2px solid; " @click="gotoCollection(4)">
					<!-- <navigator :url="'/pages/WrongQuestionIndex/WrongQuestionIndex?'+'state=cuoti&&skin='+skin?0:1" hover-class="navigator-hover"> -->
				<view class="content">
					<text class="cuIcon-favorfill text-green"></text>
					<text class="text-grey">科四收藏</text>
				</view>
				<view class="action">
					<text class="text-grey text-sm">{{km4Total}}<text class="lg text-gray cuIcon-right" ></text></text>
				</view>
				<!-- </navigator> -->
			</view>
			
			<view class="cu-item" @click="clearCllec" style="border-bottom: #ccc 2px solid; ">
				
				<button class="cu-btn bg-grey lg" style="width: 100%;">清除收藏</button>
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
				km4Total:0,
				km1Total:0,
				userCheckCar:1,
				km1Ids:[],
				km4Ids:[]
			}
		},
		methods: {
			init(option){
				uni.showLoading({title: '加载中',mask:true});
				let that = this;
				let uCollection = uni.getStorageSync("userInfo").uCollection;
				let userCheckCar = this.userCheckCar = uni.getStorageSync("userCheckCar");
				
						
				
				
				//设置请求的参数
				let params={
						'sCar': option.cartype,//请求的车型
						'sKm': option.km,//请求的科目
						'state':null,
						'errList':null
					};
				//设置请求状态
				params.state='cuoti';		
				//获取存在缓存中的错题集
				params.errList=uCollection;
				console.log(uCollection);
				let p=new Promise((resolve, reject)=>{
					uni.request({
						method:"POST",
						url:that.api+'/api/findSubjectByCarAndKm',
						data:params, 
						header:{'Content-Type': 'application/x-www-form-urlencoded'},
						success(res) {
							resolve(res.data);
						},
						fail(err){
							reject(err);
						}
					})
				});
				
				p.then((res)=>{
					
					
					
					let km1List=res.filter((item)=>{return item.sKm==1 && item.chapter.chapterCar.indexOf(userCheckCar)>=0});
					let km4List=res.filter((item)=>{return item.sKm==4 && item.chapter.chapterCar.indexOf(userCheckCar)>=0});
					
					this.km1Total=km1List.length;
					this.km4Total=km4List.length;
					
					for(let s of km1List){
						this.km1Ids.push(s.sId);
					}
					
					for(let s of km4List){
						this.km4Ids.push(s.sId);
					}
					
					
					setTimeout(function () {uni.hideLoading();}, 100);
				})
				
				
				
						
				
				
				
			},
			
			updateUserCollection(){
				uni.request({
					method: "POST",
					url: this.api + '/api/updateUserCollection',
					data: {
						uId:uni.getStorageSync("userInfo").uId,
						uCollection:uni.getStorageSync("userInfo").uCollection
					},
					header: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						console.log(res);
					}
				})
			},
			gotoCollection(km){
				if(km==1 && this.km1Total<=0){
					uni.showToast({
						icon:"error",
						title:"没有收藏"
					})
					return;
				}
				if(km==4 && this.km4Total<=0){
					uni.showToast({
						icon:"error",
						title:"没有收藏"
					})
					return;
				}
				uni.navigateTo({
					url:`/pages/errSubject/index?state=collection&skin=0&sKm=${km}&sCar=${this.userCheckCar}`
				})
			},
			clearCllec(){//清除收藏
				let that=this;
				uni.showActionSheet({
					itemList:['清除科目一收藏','清除科目四收藏'],
					success(res) {
						let str=res.tapIndex==0?'科目一的收藏?':'科目四收藏?'
						uni.showModal({
							content:`确认清除${str}`,
							success: (res1) => {
								if(res1.confirm){
									
									
										let userInfo=uni.getStorageSync("userInfo");
										let uCollectionArr = uni.getStorageSync("userInfo").uCollection.split(',');
										let km1SubjectList=uni.getStorageSync("km1SubjectList");
										if(res.tapIndex==0){//判断选择哪个科目就清除哪个
											for(let a of that.km1Ids){
												
												that.removeByVal(uCollectionArr,a);
												
												for(let item of km1SubjectList){
													if(item.sId==a)item.userFavor=false;
													break;
												}
												uni.setStorageSync("km1SubjectList",km1SubjectList);
											}
										}else{
											for(let a of that.km4Ids){
												that.removeByVal(uCollectionArr,a);
												for(let item of km1SubjectList){
													if(item.sId==a){
														item.userFavor=false
														break;
													}
												}
												uni.setStorageSync("km1SubjectList",km1SubjectList);
											}
										}
										
										userInfo.uCollection=uCollectionArr.toString();
										uni.setStorageSync("userInfo",userInfo);
										that.updateUserCollection();//上传后端修改
								
									uni.showToast({
										icon:"success",
										title:"清除成功!"
									})
									location.reload();//刷新页面
								}
								
							}
						})
					}
				})
			},
		
			
		},
		onLoad(option) {
			this.init(option);
			
		},
		onBackPress(e) {//监听用户返回前一页
			let pages = getCurrentPages(); 
			
			uni.redirectTo({
			    url: pages[1].route,
				success() {
					pages[1].init(pages[1].options)
				},
				fail() {
					pages[1].init(pages[1].options)
				}
			});
			//console.log(pages);
		},
	}
</script>

<style>

</style>
