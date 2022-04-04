<template>
	<view>
		<!-- #ifdef H5 -->
		<view style="position: fixed; top:80rpx; z-index: 100; width: 100%; height: 80rpx; background-color: #fff;">
		<!-- #endif -->
		<!-- #ifndef H5 -->
		<view style="position: fixed; top: 0rpx; z-index: 100; width: 100%; height: 80rpx; background-color: #fff;">
		<!-- #endif -->
		
		<!-- <view style="position: fixed; top: 80rpx; z-index: 100; width: 100%; height: 70rpx; background-color: #fff;"> -->
			<view  >
				<uni-segmented-control :current="current" :values="items" @clickItem="onClickItem" styleType="button"
					activeColor="#00aaff"></uni-segmented-control>
			</view>

			<view  id="top-box" class="cu-bar bg-white solid-bottom">
				<view class="action text-black">
					<text v-if="currentType==1">判断题</text>
					<text v-else-if="currentType==2">单选题</text>
					<text v-else-if="currentType==3">多选题</text>
					<text class="cuIcon-edit text-green padding-left"></text>
					<text>{{subjectIndex+1}}/{{subjectList.length}}</text>
					<text style="padding-left: 30px;" class="cuIcon-check text-green padding-left"></text>
					<text>{{userSuccess}}</text>
					<text style="padding-left: 30px;" class="cuIcon-close text-red padding-left"></text>
					<text>{{userError}}</text>
				</view>
				<view class="action">
					<button id='dtk' class="cu-btn bg-green shadow" @tap="showCardModal"
						data-target="modalCard">答题卡</button>
				</view>
			</view>
		</view>
		<view class="cu-modal" :class="modalCard=='modalCard'?'show':''" @tap="hideCardModal">
			<view class="cu-dialog" @tap.stop>

				<scroll-view class="page padding" :scroll-y=true :style="{'height':swiperHeight}">
					<view class="cu-bar solid-bottom">
						<view class="action">
							<text class="cuIcon-title text-red"></text>
							<text>答题卡</text>
							<text style="padding-left: 30px;" class="cuIcon-edit text-green padding-left"></text>
							<text>{{subjectIndex+1}}/{{subjectList.length}}</text>
							<text style="padding-left: 30px;" class="cuIcon-check text-green padding-left"></text>
							<text>{{userSuccess}}</text>
							<text style="padding-left: 30px;" class="cuIcon-close text-red padding-left"></text>
							<text>{{userError}}</text>
						</view>
					</view>
					<view class="grid col-5 ">
						<view class="margin-tb-sm text-center" v-for="(subject,index) in subjectList" :key="index">
							<!-- 	<button  class="cu-btn round" :class="[subject.userAnswer.length===0?'line-grey':'bg-green']"
								@click="AppointedSubject(index)">{{index+1}}</button> -->
							<button class="cu-btn round" :style="index==subjectIndex? 'border: #007AFF solid 3px;':''"
								:class="[subject.userAnswer.length===0?'line-grey':subject.userAnswer==subject.answer?'bg-green':'bg-red']"
								@click="AppointedSubject(index)">{{index+1}}</button>
						</view>
					</view>

				</scroll-view>
			</view>
		</view>
		<view class="cu-modal padding " style="z-index: 900;" v-if="modalError=='modalError'" :class="modalError=='modalError'?'show':''" @tap="hideErrorModal">
			<view class="cu-dialog bg-white" @tap.stop>

				<view class="cu-bar solid-bottom ">
					<view class="action">
						<text class="cuIcon-title text-red"></text>试题纠错
					</view>
				</view>

			<radio-group @change="errRadioChange" class="block">
				<view class="cu-list menu text-left">
					<view  style="width: 100%;"   class="cu-item cu-item-error" v-for="(error,index) in errorList">
						<radio  style="width: 9%;" :value="index+''" :checked="index == errForm.errOption"  :id="'error'+index"></radio>
					 <label  style="width: 90%;" :for="'error'+index">	<view class="title text-black margin-left">{{error}}</view></label>
					</view>
				</view>
			</radio-group>
			<view class="cu-bar solid-bottom ">
				<view class="action">
					<textarea style="border: 1px solid #ccc;padding: 10rpx;text-align: left;" v-model="errForm.errMsg" placeholder="请输入发现的问题" />
				</view>
			</view>

				<view class="padding flex flex-direction ">
					<button class="cu-btn bg-red margin-tb-sm lg" @click="SubmitError">提 交</button>
					<button class="cu-btn bg-blue margin-tb-sm lg margin-left"  @click="modalError=''">取消</button>
				</view>
			</view>
		</view>
		<form>
			<swiper style="margin-top: 170rpx;" :current="subjectIndex" class="swiper-box" @change="SwiperChange" :style="'height:' +heigth+'px;'">
				<swiper-item v-for="(subject,index) in subjectList">

					<view :id="'swid'+index" v-if="index-subjectIndex>=-1&&index-subjectIndex<=1">

						<view class="cu-bar bg-white solid-bottom">
							<view class="action ">
								<text class="cuIcon-title text-red"></text>
								<block>{{subject.sQuestion}}</block>
							</view>

						</view>
						
						<view class="horViewStyle">
							<image lazy-load=true v-if="(subject.sImg!=null && subject.sImg.length>0)" style="margin: 0 auto;"
								:src="api+subject.sImg" mode=""></image>
						</view>
						<view>

							<radio-group class="block" @change="RadioboxChange"
								v-if="subject.sType==1||subject.sType==2">


								<!-- <label class="cu-form-group" v-for="option in subject.optionList">
									<view>
										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.userAnswer==option.id?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer==option.id?'checkbox-filled':'circle')"
											:color="subject.userAnswer==option.id?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer==option.id?'#00e54c':'#cccccc')"
											size="30">
										</uni-icons>


										<radio v-if="subject.userAnswer.length<=0 && current==0"
											:color="(subject.userAnswer!=null && subject.answer==subject.userAnswer)?'#00e54c':'#E54D42'"
											:value="option.id" :disabled="(subject.userAnswer.length>0)?true:false"
											:checked="subject.userAnswer.indexOf(option.id) > -1?true:false"></radio>

									</view>
									<view class="title text-black">
										<text>{{option.id}}.{{option.content}}</text>
									</view>
								</label> -->



								<!-- A -->

								<label class="cu-form-group">
									<view>
										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.userAnswer=='A'?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer=='A'?'checkbox-filled':'circle')"
											:color="subject.userAnswer=='A'?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer=='A'?'#00e54c':'#cccccc')"
											size="30">
										</uni-icons>


										<radio v-if="subject.userAnswer.length<=0 && current==0"
											:color="(subject.userAnswer!=null && subject.answer==subject.userAnswer)?'#00e54c':'#E54D42'"
											:value="'A'" :disabled="(subject.userAnswer.length>0)?true:false"
											:checked="subject.userAnswer.indexOf('A') > -1?true:false"></radio>

									</view>
									<view class="title text-black">
										<text v-if="subject.sType==2">正确</text>
										<text v-else>A.{{subject.itemA}}</text>
									</view>
								</label>

								<!-- B -->
								<label class="cu-form-group">
									<view>
										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.userAnswer=='B'?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer=='B'?'checkbox-filled':'circle')"
											:color="subject.userAnswer=='B'?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer=='B'?'#00e54c':'#cccccc')"
											size="30">
										</uni-icons>


										<radio v-if="subject.userAnswer.length<=0 && current==0"
											:color="(subject.userAnswer!=null && subject.answer==subject.userAnswer)?'#00e54c':'#E54D42'"
											:value="'B'" :disabled="(subject.userAnswer.length>0)?true:false"
											:checked="subject.userAnswer.indexOf('B') > -1?true:false"></radio>

									</view>
									<view class="title text-black">
										<text v-if="subject.sType==2">错误</text>
										<text v-else>B.{{subject.itemB}}</text>
									</view>
								</label>



								<!-- C -->
								<label v-if="subject.sType==1" class="cu-form-group">
									<view>
										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.userAnswer=='C'?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer=='C'?'checkbox-filled':'circle')"
											:color="subject.userAnswer=='C'?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer=='C'?'#00e54c':'#cccccc')"
											size="30">
										</uni-icons>


										<radio v-if="subject.userAnswer.length<=0 && current==0"
											:color="(subject.userAnswer!=null && subject.answer==subject.userAnswer)?'#00e54c':'#E54D42'"
											:value="'C'" :disabled="(subject.userAnswer.length>0)?true:false"
											:checked="subject.userAnswer.indexOf('C') > -1?true:false"></radio>

									</view>
									<view class="title text-black">
										<text>C.{{subject.itemC}}</text>
									</view>
								</label>



								<!-- D -->
								<label v-if="subject.sType==1" class="cu-form-group">
									<view>
										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.userAnswer=='D'?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer=='D'?'checkbox-filled':'circle')"
											:color="subject.userAnswer=='D'?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer=='D'?'#00e54c':'#cccccc')"
											size="30">
										</uni-icons>


										<radio v-if="subject.userAnswer.length<=0 && current==0"
											:color="(subject.userAnswer!=null && subject.answer==subject.userAnswer)?'#00e54c':'#E54D42'"
											:value="'D'" :disabled="(subject.userAnswer.length>0)?true:false"
											:checked="subject.userAnswer.indexOf('D') > -1?true:false"></radio>

									</view>
									<view class="title text-black">
										<text>D.{{subject.itemD}}</text>
									</view>
								</label>



							</radio-group>

							<checkbox-group class="block" @change="CheckboxChange" v-else-if="subject.sType==3">

							

								<!-- A -->

								<view class="cu-form-group">
									<label>


										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.answer.indexOf('A')>-1?(subject.userAnswer.indexOf('A')>-1?'checkbox-filled':'circle'):(subject.userAnswer.indexOf('A')>-1?'clear':'clear')"
											:color="subject.answer.indexOf('A')>-1?(subject.userAnswer.indexOf('A')>-1?'#00e54c':'#00e54c'):(subject.userAnswer.indexOf('A')>-1?'#f41b31':'#cccccc')"
											size="25">
										</uni-icons>

										<checkbox v-if="subject.userAnswer.length<=0 && current==0" :value="'A'"
											:class="boxTmp.indexOf('A') > -1?'checked':''"
											:checked="boxTmp.indexOf('A') > -1?true:false"></checkbox>

										<view class="title  text-black"
											:style="'display:inline-block;  height: 100%;width:'+getWidth()+'px'">
											A.{{subject.itemA}}
										</view>
									</label>
								</view>


								<!-- B -->

								<view class="cu-form-group">
									<label>


										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.answer.indexOf('B')>-1?(subject.userAnswer.indexOf('B')>-1?'checkbox-filled':'circle'):(subject.userAnswer.indexOf('B')>-1?'clear':'clear')"
											:color="subject.answer.indexOf('B')>-1?(subject.userAnswer.indexOf('B')>-1?'#00e54c':'#00e54c'):(subject.userAnswer.indexOf('B')>-1?'#f41b31':'#cccccc')"
											size="25">
										</uni-icons>

										<checkbox v-if="subject.userAnswer.length<=0 && current==0" :value="'B'"
											:class="boxTmp.indexOf('B') > -1?'checked':''"
											:checked="boxTmp.indexOf('B') > -1?true:false"></checkbox>

										<view class="title  text-black"
											:style="'display:inline-block;  height: 100%;width:'+getWidth()+'px'">
											B.{{subject.itemB}}
										</view>
									</label>
								</view>




								<!-- C -->

								<view class="cu-form-group">
									<label>


										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.answer.indexOf('C')>-1?(subject.userAnswer.indexOf('C')>-1?'checkbox-filled':'circle'):(subject.userAnswer.indexOf('C')>-1?'clear':'clear')"
											:color="subject.answer.indexOf('C')>-1?(subject.userAnswer.indexOf('C')>-1?'#00e54c':'#00e54c'):(subject.userAnswer.indexOf('C')>-1?'#f41b31':'#cccccc')"
											size="25">
										</uni-icons>

										<checkbox v-if="subject.userAnswer.length<=0 && current==0" :value="'C'"
											:class="boxTmp.indexOf('C') > -1?'checked':''"
											:checked="boxTmp.indexOf('C') > -1?true:false"></checkbox>

										<view class="title  text-black"
											:style="'display:inline-block;  height: 100%;width:'+getWidth()+'px'">
											C.{{subject.itemC}}
										</view>
									</label>
								</view>




								<!-- D -->

								<view class="cu-form-group">
									<label>


										<uni-icons v-if="subject.userAnswer.length>0 || current==1"
											:type="subject.answer.indexOf('D')>-1?(subject.userAnswer.indexOf('D')>-1?'checkbox-filled':'circle'):(subject.userAnswer.indexOf('D')>-1?'clear':'clear')"
											:color="subject.answer.indexOf('D')>-1?(subject.userAnswer.indexOf('D')>-1?'#00e54c':'#00e54c'):(subject.userAnswer.indexOf('D')>-1?'#f41b31':'#cccccc')"
											size="25">
										</uni-icons>

										<checkbox v-if="subject.userAnswer.length<=0 && current==0" :value="'D'"
											:class="boxTmp.indexOf('D') > -1?'checked':''"
											:checked="boxTmp.indexOf('D') > -1?true:false"></checkbox>

										<view class="title  text-black"
											:style="'display:inline-block;  height: 100%;width:'+getWidth()+'px'">
											D.{{subject.itemD}}
										</view>
									</label>
								</view>



								<button class="margin-r-l" v-if="subject.userAnswer.length<=0 && current!=1"
									@click="fxqd" type="primary">确定</button>
							</checkbox-group>

						</view>

						<view v-show="subject.showAnswer || current==1" class="margin-top solid-top">
							<view class="cu-bar min-height30">
								<view v-if="subject.userAnswer.length>0" class="action  text-grey">
									<text>您的答案：</text>
									<text class="solid-bottom  padding-left text-green">{{subject.userAnswer}}</text>
								</view>
							</view>
							<view class="cu-bar min-height30">
								<view class="action  text-grey">
									<text>正确答案：</text>
									<text class="solid-bottom  padding-left text-green">{{subject.answer}}</text>
								</view>
							</view>

							<view class="cu-bar cu-bar-title">
								<view class="action  text-grey">
									<text>解析：</text>
								</view>
							</view>
							<view class="text-content padding  text-grey"
								style="width:90%;display:inline-block;white-space: pre-wrap; word-wrap: break-word;height: auto;">
								<text>{{subject.sExplain}}</text>
							</view>
						</view>

					</view>
				</swiper-item>
			</swiper>
		</form>
		<view id="foot-box" class="cu-bar tabbar bg-white shadow foot">
			<view class="action" @click="MoveSubject(-1)">
				<view class="cuIcon-cu-image">
					<text class="lg cuIcon-back text-gray"></text>
				</view>
				<view class="text-gray">上一题</view>
			</view>
			<view class="action" @click="MoveSubject(1)">
				<view class="cuIcon-cu-image">
					<text class="lg text-gray cuIcon-right"></text>
				</view>
				<view class="text-gray">下一题</view>
			</view>

			<view class="action" @click="FavorSubject">
				<view class="cuIcon-cu-image">
					<text class="lg cuIcon-favor" :class="[subjectList[subjectIndex].userFavor?'text-red':'text-gray']"></text>
				</view>
				<view :class="[subjectList[subjectIndex].userFavor?'text-red':'text-gray']">收藏</view>
			</view>

			<view class="action" @click="ShowAnswerChange">
				<view class="cuIcon-cu-image">
					<text class="lg text-gray cuIcon-question"></text>
				</view>
				<view class="text-gray">解答</view>
			</view>
			<view class="action" @tap="showErrorModal" data-target="modalError">
				<view class="cuIcon-cu-image">
					<text class="lg text-gray cuIcon-warn"></text>
				</view>
				<view class="text-gray ">纠错</view>
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
				userFavor: false, //是否已收藏
				heigth:750,
				swHeigth:750,
				userError: 0, //用户错题数量
				userSuccess: 0, //用户答对题目数量
				currentType: 0, //当前题型
				subjectIndex: 0, //跳转索引
				autoShowAnswer: true, //答错是否显答案
				autoRadioNext: true, //判断题、单项题，自动移下一题
				swiperHeight: '800px', //
				skin:0,//答对是移除
				title: '我的错题',
				boxTmp: '', //多选选项缓存
				items: ["答题模式", "背题模式"], //上方选项(答题模式,背题模式)
				current: 0, //上方选项索引
				subjectList: [{
						'sId': 10,
						"sQuestion": "驾驶机动车违反道路交通安全法律法规发生交通事故属于交通违章行为。",
						"sImg": null,
						"sType": 1,
						"itemA": "A",
						"itemB": "B",
						"itemC": "C",
						"itemD": "D",
						"answer": "B",
						"userAnswer": "",
						"userFavor": false,
						"sExplain": "“违反道路交通安全法”，违反法律法规即为违法行为。现在官方已无违章和违规的说法。"
					},
					


				],
				errForm:{
					errOption:'0',
					errMsg:''
				},
				modalCard: null, //显示答题卡
				modalError: null, //纠错卡
				errorList: ['题目不完整', '答案不正确', '含有错别字', '图片不存在', '解析不完整', '其他错误']
			}
		},
		onReady() {

			var tempHeight = 800;
			var _me = this;
			uni.getSystemInfo({
				//获取手机屏幕高度信息，让swiper的高度和手机屏幕一样高                
				success: function(res) {
					                 
					tempHeight = res.windowHeight;


					console.log("屏幕可用高度 " + tempHeight);
					console.log("屏幕可用宽度 " + res.windowWidth);
					uni.createSelectorQuery().select("#top-box").fields({
						size: true,
						scrollOffset: true
					}, (data) => {
						tempHeight -= data.height;
						console.log("减掉顶部后的高度 " + tempHeight);

						uni.createSelectorQuery().select("#foot-box").fields({
							size: true,
							scrollOffset: true
						}, (data) => {
							tempHeight -= data.height;
							console.log("减掉底部后的高度 " + tempHeight);
							_me.swiperHeight = tempHeight + 'px';
							_me.swHeigth=tempHeight;
							console.log("滑屏最后高度 " + _me.swiperHeight);
						}).exec();

					}).exec();
				}
			});

		},
		onLoad(option) {
			uni.showLoading({title: '加载中',mask:true});
			this.skin==option.skin;
			let routes = getCurrentPages(); // 获取当前打开过的页面路由数组
			let curRoute = routes[routes.length - 1].route //获取当前页面路由
			let curParam = routes[routes.length - 1].options; //获取路由参数
			
			let that = this
			let km = option.km == 0 ? 1 : 4;

			//设置请求的参数
			let params={
					'sCar': option.cartype,//请求的车型
					'sKm': option.sKm,//请求的科目
					'state':null,
					'errList':null
				};
			//设置请求状态
			params.state='cuoti';		
			//获取存在缓存中的错题集
			
			if(option.state=='collection'){
				params.errList=uni.getStorageSync("userInfo").uCollection;
			}else if(option.state=='cuoti'){
				params.errList=uni.getStorageSync("userErrorSubjectList").toString();
			}
			uni.request({
				method:"POST",
				url:this.api+'/api/findSubjectByCarAndKm',
				data:params, 
				header:{'Content-Type': 'application/x-www-form-urlencoded'},
				success(res) {
					//页面存储获取到的错题
					console.log(res)
					
					that.subjectList=res.data.filter((item)=>{return item.sKm==option.sKm && item.chapter.chapterCar.indexOf(option.sCar)>=0});
					
					that.currentType = that.subjectList[0].sType;
					uni.setNavigationBarTitle({
						title: that.title
					});
					
					//添加用户显示答案字段
					for (var i = 0; i < that.subjectList.length; i++) {
						that.$set(that.subjectList[i], "showAnswer", false);
					}
					
					
					/**
					 * 遍历用户收藏
					 */
					if(uni.getStorageSync("userInfo").uCollection!=undefined){
						let uCollection = uni.getStorageSync("userInfo").uCollection;
						let carr = uCollection.split(',');
						for(let i of carr){
							for(let s of that.subjectList){
								if(s.sId == i){
									s.userFavor=true;
									break;
								}
							}
						}
						
						
					}
					
					that.setHeigth();
					
					
					
					setTimeout(function () {uni.hideLoading();}, 100);
					uni.setNavigationBarTitle({
						title:option.state=='cuoti'?'我的错题':'我的收藏'
					})
				}
			})


			
			

		},
		methods: {
			errRadioChange(e){
					
				  for (let i = 0; i < this.errorList.length; i++) {
						if (i == e.detail.value) {
							this.errForm.errOption = i+'';
							break;
						}
					}
			},
			setHeigth(){
				let that=this;
				
				uni.createSelectorQuery().select("#swid"+ that.subjectIndex).fields({
					size: true,
					scrollOffset: true
				}, (data) => {
					console.log(data);
					that.heigth=that.swHeigth>data.height?that.swHeigth:data.height+60;
					
				}).exec();
				
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
			
			showCardModal: function(e) {
				this.modalCard = e.currentTarget.dataset.target
			},
			hideCardModal: function(e) {
				this.modalCard = null
			},
			showErrorModal: function(e) {
				this.modalError = e.currentTarget.dataset.target
			},
			hideErrorModal: function(e) {
				this.modalError = null
			},
			SwiperChange: function(e) { //滑动事件

				let index = e.target.current;

				if (index != undefined) {
					this.subjectIndex = index;
					this.currentType = this.subjectList[index].sType;
					this.userFavor = this.subjectList[index].userFavor;
				}
				this.setHeigth();
			},
			RadioboxChange: function(e) { //单选选中

				console.log("这道题的SId", this.subjectList[this.subjectIndex].sId);
				let sId=this.subjectList[this.subjectIndex].sId;
				let that = this;
				
			
				var items = this.subjectList[this.subjectIndex].optionList;
				var values = e.detail.value;

				if (e.detail.value == this.subjectList[this.subjectIndex].answer) {
					if(uni.getStorageSync("userErrorSubjectSkin")){
					let userErrorSubjectList=uni.getStorageSync("userErrorSubjectList");
					 this.removeByValue(userErrorSubjectList,this.subjectList[this.subjectIndex].sId);
					uni.setStorageSync("userErrorSubjectList",userErrorSubjectList);
					}
					this.userSuccess++;
					this.subjectList[this.subjectIndex].userAnswer = values;
					this.subjectList[this.subjectIndex].showAnswer = true;
					if (this.autoRadioNext && this.subjectIndex < this.subjectList.length - 1) {
						this.subjectIndex += 1;
					};

				} else {
					// uni.removeStorageSync('userErrorSubjectList')
					 let userErrorSubjectList = uni.getStorageSync('userErrorSubjectList').length<=0?[]:uni.getStorageSync('userErrorSubjectList');
				
					 // 用户答错题判断缓存用户错题里存不存在这道题,不存在就添加
					if(userErrorSubjectList.indexOf(this.subjectList[this.subjectIndex].sId)==-1){
					userErrorSubjectList.push(this.subjectList[this.subjectIndex].sId);
					 uni.setStorageSync('userErrorSubjectList', userErrorSubjectList);
					 }
					 // }
					this.userError++;
					this.subjectList[this.subjectIndex].userAnswer = values;
					this.subjectList[this.subjectIndex].showAnswer = true;
				}
				// this.subjectList[this.subjectIndex].userAnswer = values;
				// if (this.autoRadioNext && this.subjectIndex < this.subjectList.length - 1) {
				// 	this.subjectIndex += 1;
				// };


			},
			CheckboxChange: function(e) { //复选选中
				var values = e.detail.value;
				this.boxTmp = e.detail.value.toString();

			},
			fxqd: function() { //复选确定按钮

				if (this.boxTmp.length > 0) {

					let sId=this.subjectList[this.subjectIndex].sId;
					this.subjectList[this.subjectIndex].userAnswer = this.boxTmp.replace(/,/g,'');
					this.boxTmp = '';

					console.log(this.subjectList[this.subjectIndex].userAnswer)
					//用户选对了答案
					if (this.subjectList[this.subjectIndex].userAnswer == this.subjectList[this.subjectIndex].answer) {
						if(uni.getStorageSync("userErrorSubjectSkin")){
							let userErrorSubjectList=uni.getStorageSync("userErrorSubjectList");
							 this.removeByValue(userErrorSubjectList,this.subjectList[this.subjectIndex].sId);
							uni.setStorageSync("userErrorSubjectList",userErrorSubjectList);
						}
						this.userSuccess++;
						this.subjectList[this.subjectIndex].showAnswer = true;
						if (this.autoRadioNext && this.subjectIndex < this.subjectList.length - 1) {
							this.subjectIndex += 1;
						};
					} else {
					let userErrorSubjectList = uni.getStorageSync('userErrorSubjectList').length<=0?[]:uni.getStorageSync('userErrorSubjectList');
									
					 // 用户答错题判断缓存用户错题里存不存在这道题,不存在就添加
					if(userErrorSubjectList.indexOf(this.subjectList[this.subjectIndex].sId)==-1){
					userErrorSubjectList.push(this.subjectList[this.subjectIndex].sId);
					 uni.setStorageSync('userErrorSubjectList', userErrorSubjectList);
					 }
						this.userError++;
						this.subjectList[this.subjectIndex].showAnswer = true;
					}
				} else {
					uni.showToast({
						title: this.windowWidth,
						icon: 'none',
						duration: 1000
					})
					uni.showToast({
						title: "您还没有任何选择",
						icon: 'none',
						duration: 1000
					})
				}

			},

			ShowAnswerChange: function(e) { //显示答案

				// console.log(this.subjectList[this.subjectIndex].showAnswer);

				if (this.subjectList[this.subjectIndex].showAnswer) {
					this.subjectList[this.subjectIndex].showAnswer = false;
					this.heigth-=320;
				} else {

					this.subjectList[this.subjectIndex].showAnswer = true;
					this.heigth+=320;
				}
				this.$forceUpdate();
			},

			FavorSubject: function(e) { //收藏题



				/**
				 * 判断登录
				 */
				if(uni.getStorageSync("userInfo")==''){

					uni.showToast({
						title:"需要登录",
						icon:"error"
					})
					return;
				}
				
				if (this.subjectList[this.subjectIndex].userFavor) {
					
					
					this.subjectList[this.subjectIndex].userFavor = false;
					let userInfo = uni.getStorageSync("userInfo");
					let carr = userInfo.uCollection.split(',');
					this.removeByVal(carr,this.subjectList[this.subjectIndex].sId);//删除Sid
					userInfo.uCollection=carr.toString();
					uni.setStorageSync("userInfo",userInfo);
					uni.setStorageSync("km1SubjectList",this.subjectList);//记录到缓存
					this.updateUserCollection();
				} else {
					let userInfo = uni.getStorageSync("userInfo");
					userInfo.uCollection+=','+this.subjectList[this.subjectIndex].sId;
					uni.setStorageSync("userInfo",userInfo);
					
					this.subjectList[this.subjectIndex].userFavor = true;
					uni.setStorageSync("km1SubjectList",this.subjectList);//记录到缓存
					this.updateUserCollection();
				}
				
				
				// if (this.userFavor) {
				// 	this.userFavor = false;
				// 	this.subjectList[this.subjectIndex].userFavor = false;
				// } else {

				// 	this.userFavor = true;
				// 	this.subjectList[this.subjectIndex].userFavor = true;
				// }
			},

			MoveSubject: function(e) { //上一题、下一题

				if (e === -1 && this.subjectIndex != 0) {
					this.subjectIndex -= 1;
				}
				if (e === 1 && this.subjectIndex < this.subjectList.length - 1) {
					this.subjectIndex += 1;
				}
			},

			AppointedSubject: function(e) { //题卡指定

				this.modalCard = null;
				this.subjectIndex = e;
			},

			SubmitError: function(e) { //提交纠错

				if(uni.getStorageSync("userInfo")==''){
				
					uni.showToast({
						title:"需要登录",
						icon:"error"
					})
					return;
				}
				
				if(this.errForm.errMsg.length<5){
					uni.showModal({
						content:"至少输入5个字符的描述",
						showCancel:false
					})
					return;
				}else{
					let sId=Number.parseInt(this.subjectList[this.subjectIndex].sId);
					let uId=Number.parseInt( uni.getStorageSync("userInfo").uId);
					let str=this.errorList[Number.parseInt(this.errForm.errOption)]+',';
					uni.request({
						url:this.api+'/api/postErrorSubjectFB',
						method:"POST",
						data:{
							sId:sId,
							uId:uId,
							errMsg:str+this.errForm.errMsg
						},
						header:{
							'content-type': 'application/x-www-form-urlencoded',
						},
						success: (res) => {
							
							uni.showToast({
								icon:"success",
								title:res.data=='success'?'提交成功':'错误'
							})
							this.errForm.errMsg='';
							
						}
					})
					
					
				this.modalError = null;
				
				}
			},
			getWidth: function() { //返回屏幕宽度-60
				var w;
				uni.getSystemInfo({
					success(res) {
						w = res.windowWidth;
					}
				})
				return w - 60;
			},
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex;
				}
			},
		 removeByValue(arr, val) {//删除数组元素通过value
					for(var i = 0; i < arr.length; i++) {
						if(arr[i] == val) {
								arr.splice(i, 1);
								break;
							}
						}
					}
		},
		computed: {

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
	@import "../../colorui/animation.css";

	page {
		background-color: #FFFFFF;
	}

	.cu-form-group {
		justify-content: flex-start
	}

	.cu-form-group .title {
		padding-left: 30upx;
		padding-right: 0upx;
	}

	.cu-form-group+.cu-form-group {
		border-top: none;
	}

	.cu-bar-title {
		min-height: 50upx;
	}

	.cu-list.menu>.cu-item-error {
		justify-content: flex-start;
	}

	.horViewStyle {
		display: flex;
		flex-direction: row;
		/* margin: 3rpx 2%; */
		align-content: center;
		align-items: center;
		width: 100%;

	}

	.min-height30 {
		min-height: 30px;
	}

	.margin-r-l {
		margin: 0 10px;
	}
</style>
