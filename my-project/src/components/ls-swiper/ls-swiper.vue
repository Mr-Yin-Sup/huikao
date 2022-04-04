<template>
  <scroll-view
    :scroll-x="true"
    :scroll-left="swiper.left"
    :scroll-with-animation="true"
    class="scroll_list"
    @touchstart="cardTouchStart($event)"
    @touchend="cardTouchEnd"
	
  >
    <view  class="card_item"
      v-for="(item, index) in swiper.list"
      :key="index"
      :class="{'first_card': !index && item.checked, 'card_checked': item.checked}"
      @tap="cardHandle(item, index)"
      @touchmove.stop.prevent
    >
      <image :src="api+item.imgUrl.icnoImg" class="img" />
	  
	  <view  >
	  	
		  <view class="text-center" style="font-size: 40rpx;">
			<text>{{item.imgUrl.icnoName}}</text>
		  </view>
		  <view  class="text-content padding  text-grey"
			style="width:100%;display:inline-block;white-space: pre-wrap; word-wrap: break-word;height: auto;">
			<text>{{item.imgUrl.icnoExplain}}</text>
		  </view>
	   </view>
    </view>
  </scroll-view>
</template>

<script>
/**
 * @props
 * cardList: ['https://mock.jpg', 'https://mock.jpg', 'https://mock.jpg']
 * cardIndex: 0
 */
export default {
  props: {
    cardList: {
      type: Array,
	  default:[{imgUrl:"/uploadFile/4c38b35a-b2b8-4b31-abc8-bd431f453f8d.jpg",icnoName:'123'}]
    },
    cardIndex: {
      type: Number,
	  default:0
    }
  },
  data () {
    return {
		//#ifdef MP-WEIXIN
		api:'http://49.234.53.224:8881',
		//#endif
      swiper: {
        list: [], // 卡片list
        left: 0, // 距离左侧距离
        index: 0, // 移动的下标
        distance: 50, // 手势滑动超过该距离才生效
        startX: 0,
        moveX: 0,
        endX: 0
      }
    }
  },
  
  methods: {
    // 滑动开始
    cardTouchStart (e) {
      let state = e.changedTouches[0]
      this.swiper.startX = state.pageX
    },
    // 滑动结束
    cardTouchEnd (e) {
      let state = e.changedTouches[0]
      this.swiper.endX = state.pageX
      let x = this.swiper.startX - this.swiper.endX
      let absX = Math.abs(x || 0)
      if (x < 0 && absX > this.swiper.distance) {
        this.sliderSwitch(false)
      } else if (x > 0 && absX > this.swiper.distance) {
        this.sliderSwitch(true)
      }
    },
    // 滑动卡片切换
    sliderSwitch (status) {
      // status: false 向右滑， true 向左滑
      let len = this.swiper.list.length
      if (!status) {
        let idx = this.swiper.index - 1
        if (this.swiper.index) this.cardHandle(this.swiper.list[idx], idx)
      } else {
        let idx = this.swiper.index + 1
        if (this.swiper.index !== (len - 1)) this.cardHandle(this.swiper.list[idx], idx)
      }
    },
    // 点击卡片切换
    cardHandle (item, index) {
		console.log(1);
      this.swiper.index = index
      this.swiper.left = uni.upx2px(index * 550)
      this.swiper.list = this.swiper.list.map((item, idx) => {
        let checked = (index === idx)
        return {
          ...item,
          checked
        }
      })
    }
  },
  
  watch: {
    cardList: {
      immediate: true,
      deep: true,
      handler (list) {
        if (!list.length) return
        // cardIndex不可以大于cardList数量
        let idx = 0
        if (this.cardIndex < list.length) {
          idx = this.cardIndex
        }
        this.swiper.index = idx

        // 遍历出card的list
        this.swiper.list = list.map((imgUrl, index) => {
          let checked = (idx === index)
		 
          return {
            imgUrl:imgUrl,
            checked:checked
          }
        })

        // 滑动至该index指定卡片
        if (idx) {
          this.swiper.left = uni.upx2px(idx * 550)
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.scroll_list {

  
  margin-top: 60upx;
  white-space: nowrap;
  
  flex-wrap: nowrap;
  .card_item {
    width: 548upx;
    padding-right: 38upx;
    height: 910upx;
    flex: none;
    position: relative;
    display: inline-block;
    vertical-align: middle;
    &:first-child {
      margin-left: 120upx;
    }
    &:last-child {
      margin-right: 120upx;
    }
    .img {
      width: 100%;
	  
      // height: 100%;
      // position: absolute;
      // left: 0;
      // right: 0;
      z-index: 0;
    }
	text{
		text-align: center;
	}
	
  }
  .card_checked {
    height: 1000upx;
  }
}
</style>
