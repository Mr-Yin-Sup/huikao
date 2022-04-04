<template>
	<view>
		
		<!-- #ifdef H5 -->
		<view style="position: fixed; top:80rpx; z-index: 100; width: 100%; height: 80rpx; background-color: #fff;">
		<!-- #endif -->
		<!-- #ifndef H5 -->
		<view style="position: fixed; top: 0rpx; z-index: 100; width: 100%; height: 80rpx; background-color: #fff;">
		<!-- #endif -->
		
		<!-- <view style="position: fixed;top: 85rpx;width: 100%;height: 70rpx; z-index: 100;background-color: #fff;"> -->
		<!-- 顶部倒计时 -->
		<view >
			
			<view  style=" width: 100%;  ">
				<view style="width: 20%;  float: left;" class="padding-left">{{djs}}</view>
				
		
				<view class="cu-progress" style="width: 78%;padding-top: 10rpx;" >
					
					<view class="bg-red" :style="[{ width:true?time/startTime*100+'%':''}]" ></view>
				</view>
		
			
			</view>
			<view style="clear: both;">
				
			</view>
		</view>
		
		

			<view id="top-box" class="cu-bar bg-white solid-bottom">
				
				<view class="action text-black">
				<text v-if="subjectList[subjectIndex].sType==1">单选题</text>
				<text v-else-if="subjectList[subjectIndex].sType==2">判断题</text>
				<text v-else-if="subjectList[subjectIndex].sType==3">多选题</text>
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
								<button class="cu-btn round"
									:style="index==subjectIndex? 'border: #007AFF solid 3px;':''"
									:class="[subject.userAnswer.length===0?'line-grey':subject.userAnswer==subject.answer?'bg-green':'bg-red']"
									@click="AppointedSubject(index)">{{index+1}}</button>
							</view>
						</view>
		
					</scroll-view>
				</view>
			</view>
			<view class="cu-modal padding " v-if="modalError=='modalError'" style="z-index: 900;" :class="modalError=='modalError'?'show':''" @tap="hideErrorModal">
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
				<swiper style="margin-top: 150rpx;" :current="subjectIndex" class="swiper-box" @change="SwiperChange" :style="'height:' +heigth+'px;'">
					<swiper-item  v-for="(subject,index) in subjectList">
		
						<view :id="'swid'+index" v-if="index-subjectIndex>=-1&&index-subjectIndex<=1">
		
							<view class="cu-bar bg-white solid-bottom">
								<view class="action text-black">
									<text class="cuIcon-title text-red"></text>{{subject.sQuestion}}
		
								</view>
		
							</view>
							<view class="horViewStyle">
								<image lazy-load=true v-if="subject.sImg.length>0" style="margin: 0 auto;"
									:src="api+subject.sImg" mode=""></image>
							</view>
							<view>
		
								<radio-group class="block" @change="RadioboxChange"
									v-if="subject.sType==1||subject.sType==2">
								
									<!-- A -->
								
									<label class="cu-form-group">
										<view>
											<uni-icons v-if="subject.userAnswer.length>0 "
												:type="subject.userAnswer=='A'?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer=='A'?'checkbox-filled':'circle')"
												:color="subject.userAnswer=='A'?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer=='A'?'#00e54c':'#cccccc')"
												size="30">
											</uni-icons>
								
								
											<radio v-if="subject.userAnswer.length<=0 "
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
											<uni-icons v-if="subject.userAnswer.length>0 "
												:type="subject.userAnswer=='B'?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer=='B'?'checkbox-filled':'circle')"
												:color="subject.userAnswer=='B'?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer=='B'?'#00e54c':'#cccccc')"
												size="30">
											</uni-icons>
								
								
											<radio v-if="subject.userAnswer.length<=0 "
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
											<uni-icons v-if="subject.userAnswer.length>0 "
												:type="subject.userAnswer=='C'?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer=='C'?'checkbox-filled':'circle')"
												:color="subject.userAnswer=='C'?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer=='C'?'#00e54c':'#cccccc')"
												size="30">
											</uni-icons>
								
								
											<radio v-if="subject.userAnswer.length<=0 "
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
											<uni-icons v-if="subject.userAnswer.length>0"
												:type="subject.userAnswer=='D'?(subject.userAnswer==subject.answer?'checkbox-filled':'clear'):(subject.answer=='D'?'checkbox-filled':'circle')"
												:color="subject.userAnswer=='D'?(subject.userAnswer==subject.answer?'#00e54c':'#f41b31'):(subject.answer=='D'?'#00e54c':'#cccccc')"
												size="30">
											</uni-icons>
								
								
											<radio v-if="subject.userAnswer.length<=0 "
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
								
								
											<uni-icons v-if="subject.userAnswer.length>0 "
												:type="subject.answer.indexOf('A')>-1?(subject.userAnswer.indexOf('A')>-1?'checkbox-filled':'circle'):(subject.userAnswer.indexOf('A')>-1?'clear':'clear')"
												:color="subject.answer.indexOf('A')>-1?(subject.userAnswer.indexOf('A')>-1?'#00e54c':'#00e54c'):(subject.userAnswer.indexOf('A')>-1?'#f41b31':'#cccccc')"
												size="25">
											</uni-icons>
								
											<checkbox v-if="subject.userAnswer.length<=0 " :value="'A'"
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
								
								
											<uni-icons v-if="subject.userAnswer.length>0"
												:type="subject.answer.indexOf('B')>-1?(subject.userAnswer.indexOf('B')>-1?'checkbox-filled':'circle'):(subject.userAnswer.indexOf('B')>-1?'clear':'clear')"
												:color="subject.answer.indexOf('B')>-1?(subject.userAnswer.indexOf('B')>-1?'#00e54c':'#00e54c'):(subject.userAnswer.indexOf('B')>-1?'#f41b31':'#cccccc')"
												size="25">
											</uni-icons>
								
											<checkbox v-if="subject.userAnswer.length<=0" :value="'B'"
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
								
								
											<uni-icons v-if="subject.userAnswer.length>0 "
												:type="subject.answer.indexOf('C')>-1?(subject.userAnswer.indexOf('C')>-1?'checkbox-filled':'circle'):(subject.userAnswer.indexOf('C')>-1?'clear':'clear')"
												:color="subject.answer.indexOf('C')>-1?(subject.userAnswer.indexOf('C')>-1?'#00e54c':'#00e54c'):(subject.userAnswer.indexOf('C')>-1?'#f41b31':'#cccccc')"
												size="25">
											</uni-icons>
								
											<checkbox v-if="subject.userAnswer.length<=0 " :value="'C'"
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
								
								
											<uni-icons v-if="subject.userAnswer.length>0 "
												:type="subject.answer.indexOf('D')>-1?(subject.userAnswer.indexOf('D')>-1?'checkbox-filled':'circle'):(subject.userAnswer.indexOf('D')>-1?'clear':'clear')"
												:color="subject.answer.indexOf('D')>-1?(subject.userAnswer.indexOf('D')>-1?'#00e54c':'#00e54c'):(subject.userAnswer.indexOf('D')>-1?'#f41b31':'#cccccc')"
												size="25">
											</uni-icons>
								
											<checkbox v-if="subject.userAnswer.length<=0 " :value="'D'"
												:class="boxTmp.indexOf('D') > -1?'checked':''"
												:checked="boxTmp.indexOf('D') > -1?true:false"></checkbox>
								
											<view class="title  text-black"
												:style="'display:inline-block;  height: 100%;width:'+getWidth()+'px'">
												D.{{subject.itemD}}
											</view>
										</label>
									</view>
								
								
								
									<button class="margin-r-l" v-if="subject.userAnswer.length<=0 "
										@click="fxqd" type="primary">确定</button>
								</checkbox-group>
								
								
								
		
							</view>
		
							<view :id="'content'+index" v-show="subject.showAnswer" class="margin-top solid-top">
								<view class="cu-bar min-height30">
									<view class="action  text-grey">
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
		
				<!-- <view class="action" @click="ShowAnswerChange">
					<view class="cuIcon-cu-image">
						<text class="lg text-gray cuIcon-question"></text>
					</view>
					<view class="text-gray">解答</view>
				</view> -->
				<view class="action" @tap="showErrorModal" data-target="modalError">
					<view class="cuIcon-cu-image">
						<text class="lg text-gray cuIcon-warn"></text>
					</view>
					<view class="text-gray ">纠错</view>
				</view>
				
				<view class="action" data-target="juaojuan" @tap="showModal" >
					<view class="cuIcon-cu-image">
						<uni-icons type="checkbox-filled" color="#00e54c" size="40rpx"></uni-icons>
						<view class="text-green ">交卷</view>
					</view>
					
				</view>
				
		
		
			</view>
			
			<view class="cu-modal" :class="modalName=='juaojuan'?'show':''">
				<view class="cu-dialog">
					<view class="cu-bar bg-white justify-end">
						<view class="content">交卷?</view>
						<view class="action" @tap="hideModal">
							<text class="cuIcon-close text-red"></text>
						</view>
					</view>
					<view class="padding-xl">
						<view style="font-size: 40rpx; color: #18BC37;">成绩:{{Math.round((userSuccess/subjectList.length)*100)}}分</view>
						<view>总共{{subjectList.length}}题</view>
						<view>答对{{userSuccess}}题</view>
						<view>答错{{userError}}题</view>
						<view v-if="subjectList.length>(userError+userSuccess)">未答{{subjectList.length-(userError+userSuccess)}}题</view>
					</view>
					<view class="cu-bar bg-white justify-end">
						<view class="action">
							<button class="cu-btn line-green text-green" @tap="hideModal">继续答题</button>
							<button class="cu-btn bg-green margin-left" data-target="submitScoreView" @tap="submitScore"  >确定交卷</button>
			
						</view>
					</view>
				</view>
			</view>
			
			
			<view class="cu-modal" :class="submitScoreView=='submitScoreView'?'show':''">
				<view class="cu-dialog">
					<view class="cu-bar bg-white justify-end">
						<view class="content">{{(userSuccess/subjectList.length)*100>=90?'成绩合格':'成绩不合格'}}</view>
						<view class="action">
							
						</view>
					</view>
					<view class="padding-xl">
							<view v-if="time<=0" style="font-size: 40rpx;">时间到</view>
							<view>考试得分 ：{{Math.round((userSuccess/subjectList.length)*100)}}</view>
							<view>用时 ：{{yongshi}}</view>
							<view >
							
								<view class='cu-tag' :class="(userSuccess/subjectList.length)*100<60?'bg-red':'bg-green'" style="margin-top: 10rpx; width: 200rpx; font-size: 40rpx; font-weight: bold;height: 60rpx;">{{designation}}</view>
							</view>
					</view>
					<view class="cu-bar bg-white justify-end">
						<view class="action">
						
							<button class="cu-btn bg-green margin-left" @click="gotoIndex">确定</button>
			
						</view>
					</view>
				</view>
			</view>
		
		</view>
		
		
		
		
	</view>
	
	
	
</template>

<script>
	export default {
		data() {
			return{
				//#ifdef MP-WEIXIN
					// 为了解决微信小程序无法使用全局变量修改所以每个文件都设置了服务端地址
				api: 'http://192.168.2.100:8881',
				//#endif
				submitScoreView:null,
				startTime:2700,
				heigth:750,
				swHeigth:750,
				time:2700,
				startDate:null,
				djs:'45分00秒',
				userFavor: false, //是否已收藏
				userError: 0, //用户错题数量
				userSuccess: 0, //用户答对题目数量
				currentType: 0, //当前题型
				subjectIndex: 0, //跳转索引
				autoShowAnswer: true, //答错是否显答案
				autoRadioNext: true, //判断题、单项题，自动移下一题
				swiperHeight: '800px', //
				title: '科目一模拟考试',
				yongshi:null,//考试用时，交卷的时候计算
				boxTmp: '', //多选选项缓存
				km:null,
				car:null,
				modalName: null,
				jsq:null,
				errForm:{
					errOption:'0',
					errMsg:''
				},
				subjectList: [{
						'sId': 1,
						"sQuestion": "驾驶机动车违反道路交通安全法律法规发生交通事故属于交通违章行为。",
						"sImg":'',
						"sType": 1,
						"itemA": "A",
						"itemB": "B",
						"itemC": "C",
						"itemD": "D",
						"answer": "B",
						"userAnswer": "",
						"userFavor": false,
						"sExplain": "“违反道路交通安全法”，违反法律法规即为违法行为。现在官方已无违章和违规的说法。"
					}


				],
				modalCard: null, //显示答题卡
				modalError: null, //纠错卡
				errorList: ['题目不完整', '答案不正确', '含有错别字', '图片不存在', '解析不完整', '其他错误']
			}
		},
		onLoad(option) {
			let that=this;
			uni.showLoading({title: '加载中',mask:true});
			option.km==0?this.km=1:this.km=4;
			if(option.cartype==4)this.time=this.startTime=1800;//设置摩托车考试时间
			
			this.countdown();//开启倒计时
			this.startDate=new Date();//获得开始时间
			let routes = getCurrentPages(); // 获取当前打开过的页面路由数组
			let curRoute = routes[routes.length - 1].route //获取当前页面路由
			let curParam = routes[routes.length - 1].options; //获取路由参数
			
			let examSet=null;
			this.car=option.cartype;
			//获取当前的考试设置
			
			uni.request({
				method:"POST",
				url:this.api+'/api/findExamSetByCarAndKm',
				header: {
					'Content-Type': 'application/x-www-form-urlencoded'
				},
				data:{
					car:option.cartype,
					km:this.km,
				},
				success(resExamSet) {
					examSet=resExamSet.data;
				}
			})
			
			let params = {
				'sCar': option.cartype,
				'sKm': this.km,
				'state': 'exam', //考试
				'errList': null
			};
			
			
				uni.request({
				method: "POST",
				url: this.api + '/api/findSubjectByCarAndKm',
				data: params,
				header: {
					'Content-Type': 'application/x-www-form-urlencoded'
				},
			
				success(res) {
				
					 res=res.data;
					
					let total;
					let newSubjectList=[];
					if(that.km==4){
						total=50;
					}else{
						option.cartype==4?total=50:total=100;
					}
					
					examSet=examSet.data;
					
					let xuanzheAll=res.filter(item => item.sType==1).sort(() => Math.random() - 0.5);
					let panduanAll=res.filter(item => item.sType==2).sort(() => Math.random() - 0.5);
					let duoxuanAll=res.filter(item => item.sType==3).sort(() => Math.random() - 0.5);
					
					for(let i=0;i<examSet.length;i++){
						//let zhangjieAll=res.filter(item => item.chapterId==examSet[i].chapter_id);//筛选改章节的题目
						// 筛选后打乱顺序放到对应的数组
						let xuanzhe =xuanzheAll.filter(item => item.chapterId==examSet[i].chapter_id).sort(() => Math.random() - 0.5);//选择题
						let panduan =panduanAll.filter(item => item.chapterId==examSet[i].chapter_id).sort(() => Math.random() - 0.5);//判断题
						let duoxuan =duoxuanAll.filter(item => item.chapterId==examSet[i].chapter_id).sort(() => Math.random() - 0.5);//多选题
						if(that.km==1){//设置科目一考试题目
							if(option.cartype==4){//设置摩托车科目一考试题目
								panduan=panduan.slice(0,0.2*examSet[i].proportion);
								xuanzhe=xuanzhe.slice(0,0.3*examSet[i].proportion);
								newSubjectList = newSubjectList.concat(panduan);
								newSubjectList = newSubjectList.concat(xuanzhe);
							}else{//其他车型科目一考试题目
								panduan=panduan.slice(0,0.4*examSet[i].proportion);
								xuanzhe=xuanzhe.slice(0,0.6*examSet[i].proportion);
								newSubjectList = newSubjectList.concat(panduan);
								newSubjectList = newSubjectList.concat(xuanzhe);
							}
							
						}else{//科目四设置
						
							panduan=panduan.slice(0,0.2*examSet[i].proportion);
							xuanzhe=xuanzhe.slice(0,0.2*examSet[i].proportion);
							
							duoxuan=duoxuan.slice(0,0.1*examSet[i].proportion);
						
							newSubjectList = newSubjectList.concat(panduan);
							newSubjectList = newSubjectList.concat(xuanzhe);
							newSubjectList = newSubjectList.concat(duoxuan);
						}
					}
					let sortArr=[];//用于排序;
					if(that.km==1){//科目一排序
					
							let arr=newSubjectList.filter(item => item.sType==2);
							let arr1=newSubjectList.filter(item => item.sType==1);
							arr=that.getResList(panduanAll,arr,total*0.4);
							arr1=that.getResList(xuanzheAll,arr1,total*0.6);
							 sortArr = sortArr.concat(arr);
							 sortArr = sortArr.concat(arr1);
					}else if(that.km==4){//科目四排序
						let arr=newSubjectList.filter(item => item.sType==2);
						let arr1=newSubjectList.filter(item => item.sType==1);
						let arr2=newSubjectList.filter(item => item.sType==3);
						
						arr=that.getResList(panduanAll,arr,total*0.4);
						arr1=that.getResList(xuanzheAll,arr1,total*0.4);
						arr2=that.getResList(duoxuanAll,arr2,total*0.2);
					
						

						 sortArr = sortArr.concat(arr);
						 sortArr = sortArr.concat(arr1);
						 sortArr = sortArr.concat(arr2);
					}
			
					that.subjectList = sortArr;
					
					let km1SubjectList=uni.getStorageSync("km1SubjectList");
					for(let item of km1SubjectList){
						item.userFavor=false;
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
				}
				
				
			
				
			})
			
			
			
			this.currentType = this.subjectList[0].sType;
			uni.setNavigationBarTitle({
				title: this.km==1?'科目一模拟考试':'科目四模拟考试'
			});
			
			//添加用户显示答案字段
			for (var i = 0; i < this.subjectList.length; i++) {
				this.$set(this.subjectList[i], "showAnswer", false);
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
		
		onHide(){
			clearInterval(this.jsq);
		},
		onUnload(){
			clearInterval(this.jsq);
		},
		onShow() {
			clearInterval(this.jsq);
			this.countdown();
		},
		
		methods:{
			/**
			 * @param {Object} oo 存放一个题目类型的数组如 (存放所有选择题的数组)
			 * @param {Object} nn 结果数组,经过层层筛选后得到的数组
			 * @param {Object} y  数组需要多少条题目
			 */
			getResList(oo,nn,y){
				let g=y-nn.length
				
				for(let o of oo ){
					if(g==0)break;
										
					let bool =true;
					for(let n of nn){
						if(o.sId==n.sId)bool=false;
					}
					
					if(bool){
						
						nn.push(o);
						g--;
					}
					
					
					
				}
				
				return nn;
			},
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
					if(data==null){
						
						return;
					}
					that.heigth=that.swHeigth>data.height?that.swHeigth:data.height+60;
					
				}).exec();
				
			},
			
			updateUserCollection(){//更新收藏
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
			
			// 确认交卷按钮
			submitScore(e){
				let that=this;
				clearInterval(this.jsq)
				this.modalName=null;
				this.submitScoreView = 'submitScoreView';
				let nowdata=new Date();
				let fen = Math.floor((nowdata-this.startDate)/1000/60);
				let miao =Math.floor(((nowdata-this.startDate)%60000)/1000);
				
				this.yongshi=fen+'分'+miao+'秒';
				let userInfo=uni.getStorageSync("userInfo");
				if(userInfo=='')return;//判断登录没登录则不提交成绩
				let score=Math.round((this.userSuccess/this.subjectList.length)*100);
				 let date1=new Date('2020-1-1 00:00:00')
				let yongshi=new Date((fen*60*1000)+(miao*1000)+date1.getTime());
				uni.request({
					method:"POST",
					
					data:{
						uId:userInfo.uId,
						km:this.km,
						carType:this.car,
						score:score,
						useTime:yongshi,
						examTime:this.startDate,
						successCount:this.userSuccess,
						errorCount:this.userError,
						unan:this.subjectList.length-this.userSuccess-this.userError
					},
					url:this.api+'/api/userSubmitScore',
					
					success(res) {
						console.log(res);
					}
				})
			
			},
			countdown(){
				let that=this;
				
					that.jsq=setInterval(function(){
						that.time=that.time-1;
					   var minute=parseInt(that.time/60);
					   var second=parseInt(that.time%60);
						console.log('还剩'+minute+'分'+second+'秒');
						that.djs=minute+'分'+second+'秒';
						that.$forceUpdate();
						
						if (that.time<=0){
							clearInterval(that.jsq)
							that.submitScore();//交卷
						}
						
					},1000);
					
				
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
			
					let that = this;
			
					var values = e.detail.value;
			
					if (e.detail.value == this.subjectList[this.subjectIndex].answer) {
						this.userSuccess++;
						this.subjectList[this.subjectIndex].userAnswer = values;
						this.ShowAnswerChange();
						if (this.autoRadioNext && this.subjectIndex < this.subjectList.length - 1) {
							this.subjectIndex += 1;
						};
			
					} else {
						
						let userErrorSubjectList = uni.getStorageSync('userErrorSubjectList').length <= 0 ? [] : uni
							.getStorageSync('userErrorSubjectList');
				
						
						// 用户答错题判断缓存用户错题里存不存在这道题,不存在就添加
						if (userErrorSubjectList.indexOf(this.subjectList[this.subjectIndex].sId) == -1) {
							userErrorSubjectList.push(this.subjectList[this.subjectIndex].sId);
							uni.setStorageSync('userErrorSubjectList', userErrorSubjectList);
						}
						
						this.userError++;
						this.subjectList[this.subjectIndex].userAnswer = values;
						this.ShowAnswerChange();
						
					}
				
			
			
				},
				CheckboxChange: function(e) { //复选选中
			
					var values = e.detail.value;
					this.boxTmp = e.detail.value.toString();
			
				},
				fxqd: function() { //复选确定按钮
				
					console.log(this.getWidth())
					if (this.boxTmp.length > 0) {
			
			
						this.subjectList[this.subjectIndex].userAnswer = this.boxTmp.toString().replace(/,/g,'');
						this.boxTmp = '';
						console.log("用户答案:" + this.subjectList[this.subjectIndex].userAnswer)
						//用户选对了答案
						if (this.subjectList[this.subjectIndex].userAnswer == this.subjectList[this.subjectIndex].answer) {
							this.userSuccess++;
							this.ShowAnswerChange();
							if (this.autoRadioNext && this.subjectIndex < this.subjectList.length - 1) {
								this.subjectIndex += 1;
							};
						} else {
							let userErrorSubjectList = uni.getStorageSync('userErrorSubjectList').length <= 0 ? [] : uni
								.getStorageSync('userErrorSubjectList');
											
							
							// 用户答错题判断缓存用户错题里存不存在这道题,不存在就添加
							if (userErrorSubjectList.indexOf(this.subjectList[this.subjectIndex].sId) == -1) {
								userErrorSubjectList.push(this.subjectList[this.subjectIndex].sId);
								uni.setStorageSync('userErrorSubjectList', userErrorSubjectList);
							}
							
							
							this.userError++;
							this.ShowAnswerChange();
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
			
					// if (this.subjectList[this.subjectIndex].showAnswer) {
						//this.subjectList[this.subjectIndex].showAnswer = false;
					// } else {
			
						this.subjectList[this.subjectIndex].showAnswer = true;
						this.heigth+=320;
					//}
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
			
				SubmitError: function(e) {  //提交纠错
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
				showModal(e) {//交卷对话框
				
					this.modalName = e.currentTarget.dataset.target
				},
				hideModal(e) {//交卷对话框隐蔽
					this.modalName = null
				},
				gotoIndex(){
					uni.switchTab({
						url:'../home/index'
					})
				}
		},
		onBackPress(e) {//监听用户返回前一页
			if(this.showMask) {  
			     this.showMask = false;  
			     return true;  
			   }else{  
			      uni.showModal({  
			        title: '提示',  
			        content: '正在考试,是否退出?',  
			        success: function(res) {  
			            if (res.confirm) {  
							clearInterval(this.jsq);
			                // 退出当前应用，改方法只在App中生效  
			               uni.switchTab({
			                   url: '/pages/home/index'
			               });
						   
			            } else if (res.cancel) {  
			                console.log('用户点击取消');  
			            }  
			        }  
			      });  
			      return true  
			   }  
		},
		computed:{
			designation(){
				let score=(this.userSuccess/this.subjectList.length)*100;
				let str='';
				switch (Math.floor(score/10)){
					case 10: str='驾考传奇'; break;
					case 9: str='驾考车神';break;
					case 8: str='预备车手';break;
					case 7: str='进击的菜鸟';break;
					case 6: str='进击的菜鸟';break;
					default:str='马路杀手';break;
				}
				return str;
			},
			//计算考试用时
			jisuanYongshi(){
				
			}
		}
		
	}
</script>

<style >



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
