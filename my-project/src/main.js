import Vue from 'vue'
import App from './App'

// import request from './util/request.js'

// import $http from '@/uni_modules/zhouWei-request/js_sdk/requestConfig';
// Vue.prototype.$http = $http;

// import Request from '@/js_sdk/luch-request/luch-request/index.js' // 下载的插件
//  Vue.prototype.http = new Request();

// 配置全局代理
// Vue.prototype.apiUrl = '/api';


// Vue.prototype.request = function(obj) {
// 	var header = obj.header || {}
// 	uni.request({
// 	        // 设置请求地址 变成了 /h5api+后台路由接口 以/h5api的请求都会被代理 
// 		url: this.apiUrl + obj.url, 
// 		data: obj.data || {},
// 		method: obj.method || 'GET',
// 		header: header,
// 		success: res => {
// 		    typeof obj.success == "function" && obj.success(res)
// 		},
// 		fail: res => {
// 		    typeof obj.fail == "function" && obj.fail(res)
// 		}
// 	});
// }
// 这里改成你服务端的域名或者ip地址
Vue.prototype.api="https://192.168.2.100:8881";
// Vue.prototype.api="https://localhost:8881/";

Vue.prototype.removeByVal=(arrylist , val) =>{
	for(var i = 0; i < arrylist .length; i++) {
		if(arrylist [i] == val) {
			arrylist .splice(i, 1);
			break;
		}
	}
}



//正则表达式
import validate from '@/js_sdk/fshjie-formvalidate/ys-validate.js'

// 挂载到全局
Vue.prototype.$validate = validate


Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
  ...App
})
app.$mount()
