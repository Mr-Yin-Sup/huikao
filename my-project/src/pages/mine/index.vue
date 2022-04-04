<template>
  <view class="page">
    <view class="top">
      <view class="background"></view>
    </view>
    <view class="user-card">
      <view class="card">
        <view class="top">
          <view class="userImage" @click="showImg(api+userInfo.uImg)">
			
           <image v-if="userInfo==null||userInfo==''" src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.pintu360.com%2Fdefault%2Fzz_avatar_default.png&refer=http%3A%2F%2Fimg.pintu360.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640869204&t=9dc15f8bb599ba742dcb9d3c60eda004" style="width: 150rpx;height: 150rpx;"></image>
		    <image v-else :src="api+userInfo.uImg" style="width: 150rpx;height: 150rpx;"></image>
          </view>
        </view>
        <view class="bottom">
          <view class="left">
            <view class="user-text">
				
              
			  <text v-if="userInfo==null||userInfo==''" @click="gotoLogin">点击登录</text>
			  <text v-else  >{{userInfo.uName}}</text>
            </view>
           
          </view>
          <view class="right flex-center">
           <!-- <uni-icons class="mr-30" type="arrowright" size="25"></uni-icons> -->
          </view>
        </view>
      </view>
    </view>
    <view class="list-card">
      <view class="card">
        <view class="item item-bottom-solid" @click="gotoCollection">
          <view class="left flex-center">
			  <text class="cuIcon-favorfill" style="font-size: 30px; color: #0081FF; "></text>
            <!-- <text class="iconfont icon-biaozhi" style="font-size: 40px; color: #0081FF; " ></text> -->
          </view>
          <view class="center">
            <text>我的收藏</text>
          </view>
          <view class="right flex-center">
            <uni-icons class="mr-30" type="arrowright" size="25"></uni-icons>
          </view>
        </view>
      </view>
      
      <view class="card" @click="aboutUsView=true">
        <view class="item item-bottom-solid" >
          <view class="left flex-center">
           <text class="cuIcon-service" style="font-size: 30px; color: #0081FF; "></text>
          </view>
          <view class="center">
            <text>关于我们</text>
          </view>
          <view class="right flex-center">
            <uni-icons class="mr-30" type="arrowright" size="25"></uni-icons>
          </view>
        </view>
      </view>
	  <view class="card" @click="clear">
	    <view class="item item-bottom-solid">
	      <view class="left flex-center">
	      <text class="cuIcon-deletefill" style="font-size: 30px; color: #0081FF; "></text>
	      </view>
	      <view class="center">
	        <text>清除答题记录</text>
	      </view>
	      <view class="right flex-center">
	        <uni-icons class="mr-30" type="arrowright" size="25"></uni-icons>
	      </view>
	    </view>
	  </view>
	  
	  <view class="card" @click="gotoUpdateUserinfo">
	    <view class="item item-bottom-solid">
	      <view class="left flex-center">
	       <text class="cuIcon-settings" style="font-size: 30px; color: #0081FF; "></text>
	      </view>
	      <view class="center">
	        <text>设置个人信息</text>
	      </view>
	      <view class="right flex-center">
	        <uni-icons class="mr-30" type="arrowright" size="25"></uni-icons>
	      </view>
	    </view>
	  </view>
	  
	  <view class="card" @click="showFeedbackView">
	    <view class="item item-bottom-solid">
	      <view class="left flex-center">
	      <text class="cuIcon-message" style="font-size: 30px; color: #0081FF; "></text>
	      </view>
	      <view class="center">
	        <text>意见反馈</text>
	      </view>
	      <view class="right flex-center">
	        <uni-icons class="mr-30" type="arrowright" size="25"></uni-icons>
	      </view>
	    </view>
	  </view>
	  
    </view>
     <view class="quit flex-center" @click="btn">
        <view class="btn flex-center">
          退出登录
        </view>
     </view>
	 
	 
	 <neil-modal
	     :show="showFeedback" 
		 v-if="showFeedback"
	     @close="showFeedback=false" 
	     title="意见反馈" 
	 	:auto-close='false'
	    style="z-index: 1;"
	    @cancel="showFeedback=false;FeedbackMsg=null;"
	    @confirm="submitFeedback"
	    >
	 	
	 	 <view style="min-height: 90upx;padding: 32upx 24upx;">
	 	        <view class="ui-list">
	 	        	<text class="text-bold text-center" style="font-size: 50rpx;">反馈内容</text>
	 	        	<textarea style="width: 80%; margin: 0 auto; border: 1px solid #ccc;padding: 10rpx;" placeholder-style="color:#ccc" v-model="FeedbackMsg" placeholder="在此输入反馈内容"/>
	 	        </view>
	 			
	 	    </view>
	 </neil-modal>
	 
	 
	 <neil-modal
	     :show="aboutUsView" 
	     @close="aboutUsView=false" 
	     title="关于我们" 
	 	:auto-close='false'
		:showCancel="false"
	    style="z-index: 1;"
	    @confirm="aboutUsView=false"
	    >
	 	
	 	 <view style="min-height: 90upx;padding: 32upx 24upx;">
	 	        <view class="ui-list">
	 	        	本程序仅供考试学习使用，请勿使用与商业用途，如有问题请发邮件至：mryin999@foxmail.com。
	 	        </view>
	 			
	 	    </view>
	 </neil-modal>
	 
	 
	 
	 
	 
  </view>
</template>
 
 
<script>
//import {  } from "@/common/api/{$}.js";
import neilModal from '@/components/neil-modal/neil-modal.vue';
export default {
	components: {neilModal},
  data() {
    return {
		//#ifdef MP-WEIXIN
			// 为了解决微信小程序无法使用全局变量修改所以每个文件都设置了服务端地址
				api: 'http://192.168.2.100:8881',
		//#endif
		yonghuwx:[],
		userInfo:null,
		showFeedback:false,
		FeedbackMsg:null,
		aboutUsView:false
	};
  },
  //监听页面初始化，其参数同 onLoad 参数，为上个页面传递的数据，参数类型为 Object（用于页面传参），触发时机早于 onLoad
  onInit() {},
  //监听页面加载，其参数为上个页面传递的数据，参数类型为 Object（用于页面传参）
  onLoad() {
	  
	 let userInfo=uni.getStorageSync("userInfo");
	
	 this.userInfo=userInfo;
	this.checkUserState();
	
	
  },
  //监听页面初次渲染完成。注意如果渲染速度快，会在页面进入动画完成前触发
  onReady() {},
  //监听页面显示。页面每次出现在屏幕上都触发，包括从下级页面点返回露出当前页面
  beforeDestroy() {},
  //页面滚动到底部的事件（不是scroll-view滚到底），常用于下拉下一页数据。
  onReachBottom() {},
  onShareAppMessage(res) {},
  created() {
	
  },
  methods: {
	  showFeedbackView(){
		   if(this.checkUserLogin()==false)return;
		   this.showFeedback=true;
	  },
	  submitFeedback(){
		 if(this.FeedbackMsg==null || this.FeedbackMsg.length<5){
			 uni.showToast({
			 	icon:"error",
				title:"至少输入5个字"
			 })
			 return
		 }else{
			 
			 uni.request({
			 	url:this.api+'/api/postFBack',
			 	method:"POST",
			 	data:{
			 	
			 		uId:this.userInfo.uId,
			 		msg:this.FeedbackMsg
			 	},
			 	header:{
			 		'content-type': 'application/x-www-form-urlencoded',
			 	},
			 	success: (res) => {
			 		
			 		uni.showToast({
			 			icon:"success",
			 			title:res.data=='success'?'提交成功':'错误'
			 		})
			 		this.FeedbackMsg=null;
			 		this.showFeedback=false;
			 	}
			 })
			 
			 
			//  uni.showToast({
			//  	icon:"success",
			//  	title:"提交成功"
			//  })
			
		 }
		  
	  },
	  checkUserState(){
		  let that=this;
		  let userInfo=uni.getStorageSync("userInfo");
		  if(userInfo!==''){
			  uni.request({
			  	url:this.api+"/api/userLogin",
			  	method:"POST",
				data:{uNumber:userInfo.uNumber,uPassword:userInfo.uPassword},
				success: (res) => {
					if(res.data.uState!='0'){
						uni.showModal({
							content: '账号异常!',
							showCancel: false,
						});
						uni.removeStorageSync("userInfo");
						that.userInfo=null;
						uni.redirectTo({
							url:"/pages/mine/index"
						})
					}else{
						uni.setStorageSync("userInfo",res.data);
					
					//console.log(res.data);
					}
				}
			  })
		  }
	  },
	  checkUserLogin(){
		  if(uni.getStorageSync("userInfo")==undefined || uni.getStorageSync("userInfo")==''){
		  		  uni.showToast({
		  		  	icon:"error",
		  			title:"您还没有登录!"
		  		  })
		  		  return false;
		  }
		  return true;
	  },
	  gotoCollection(){
		  if(this.checkUserLogin()==false)return;
		  uni.navigateTo({
		  	url:"../userFavorIndex/userFavorIndex"
		  })
	  },
	 //修改信息
	  gotoUpdateUserinfo(){
		  if(this.checkUserLogin()==false)return;
	
		  uni.navigateTo({
		  	url:"../c-userinfo/c-userinfo"
		  })
	  },
	  //清楚答题记录
	  clear(){
		  uni.showModal({
		      title: '提示',
		      content: '此操作将清除您的答题记录、收藏、错题等信息',
		      success: function (res) {
		          if (res.confirm) {
		            uni.removeStorageSync("userErrorSubject");
					uni.removeStorageSync("userSuccessKm4SubjectList");
					uni.removeStorageSync("userSuccessKm1SubjectList");
					uni.removeStorageSync("userSunXuSubjectIndex");
					uni.removeStorageSync("userSunXuSubjectKm1Index");
					uni.removeStorageSync("userErrorSubjectList");
					uni.removeStorageSync("km1SubjectList");
					uni.removeStorageSync("userErrorSubjectSkin");
					uni.removeStorageSync("userSunXuSubjectKm4Index");
					
					uni.setStorageSync("userCheckCar",1);
					uni.setStorageSync("userCheckKm",1);
		  		   location.reload();
		  		 
		          } else if (res.cancel) {
		              console.log('用户点击取消');
		          }
		      }
		  });
	  },
	  btn(){
		  let that=this;
			if(this.userInfo==''||this.userInfo==null){
				uni.showToast({
				    title: '还没有登录!',
				    duration: 2000,
					icon:'error'
				});
				return
			}
			uni.showModal({
			    title: '退出登录',
			    content: '您确定退出登录,退出后不可使用我的成绩和收藏功能',
			    success: function (res) {
			        if (res.confirm) {
			          uni.removeStorageSync("userInfo");
					  
					  that.userInfo=null;
					 

					 
			        } else if (res.cancel) {
			            console.log('用户点击取消');
			        }
			    }
			});
			
	  },
	  
	  showImg(url){
		  console.log(url);
		  if(url.indexOf('undefined')>=0)return;
		   uni.previewImage({
		              urls: [url],
		              longPressActions: {
		                  itemList: [ '保存图片'],
		                  success: function(data) {
		                      console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
		                  },
		                  fail: function(err) {
		                      console.log(err.errMsg);
		                  }
		              }
		          });
	  },
	  checkWx(){
		//#ifdef MP-WEIXIN
				return true;
		//#endif
		return false;
	  },
	  gotoLogin(){
		  uni.navigateTo({
		  	url:"/pages/auth/login/login"
		  })
	  }
  },
};
</script>


<style lang="scss" scoped>
	 @import "../../colorui/animation.css";
.top {
  height: 350rpx;
  position: relative;
  .background {
    background-color: #5199ff;
    border-bottom-left-radius: 22px;
    border-bottom-right-radius: 22px;
    position: absolute;
    height: 250rpx;
    width: 100%;
  }
}
.icon {
  color: #96a1ae;
  font-size: 40rpx;
}
.user-card {
  height: 70rpx;
  padding: 0 15px;
  .card {
    position: relative;
    bottom: 62px;
    height: 250rpx;
    background-color: white;
    border-radius: 5px;
    .top {
      height: 30%;
      position: relative;
      .userImage {
        position: absolute;
        bottom: 24%;
        left: 10%;
        width: 150rpx;
        height: 150rpx;
        overflow: hidden;
        border-radius: 50%;
        border: 2px solid white;
      }
    }
    .bottom {
      display: flex;
      height: 70%;
      .left {
        width: 80%;
        height: 100%;
        position: relative;
        .user-text {
          width: 100%;
          font-size: 1.6em;
          padding-left: 80rpx;
          height: 50%;
        }
        .user-phone {
          color: #96a1ae;
          padding-left: 80rpx;
          height: 50%;
          width: 100%;
          font-size: 0.9em;
        }
      }
      .right {
        width: 20%;
        height: 50%;
      }
    }
  }
}
.list-card {
  padding: 0 15px;
  .card {
    border-radius: 5px;
    position: relative;
    background-color: white;
    border-radius: 5px;
    padding: 5px 30px;
    .item {
      display: flex;
      height: 120rpx;
      .left {
        width: 15%;
        image {
          width: 70rpx;
          height: 70rpx;
        }
      }
      .center {
        width: 65%;
        display: flex;
        justify-content: start;
        align-items: center;
        font-size: 1.1em;
      }
      .right {
        width: 20%;
        justify-content: flex-end;
      }
    }
  }
}
.item-bottom-solid {
  border-bottom: 1px solid #d4d6da;
}
.quit{
  height: 100rpx;
  margin-top: 50px;
  .btn{
    background-color: #4f99ff;
    border-radius: 30px;
    width: 80%;
    color: white;
    font-size: 1.2em;
    height: 100%;
  }
}
.flex-center{
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>