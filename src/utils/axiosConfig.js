import Axios from "axios";
import router from "../router/index";
// 请求拦截器
Axios.interceptors.request.use(
  config => {
    // 发送请求之前可以在这里写一些东西
    return config;
  },
  error => {
    // 请求报错的时候做一些事情
    return Promise.reject(error);
  }
);
// 响应拦截器
Axios.interceptors.response.use(
  response => {
    // 对响应数据做一些事情
    return response;
  },
  error => {
    // 请求错误时做些事
    let status = "";
    if (error.request) {
      status = error.request;
    } else if (error.response) {
      status = error.response;
    }
    if (status) {
      switch (status.status) {
        case 400:
          error.message = "请求错误(400)";
          break;
        case 401:
          error.message = "未授权，请重新登录(401)";
          router.push("/login");
          break;
        case 403:
          error.message = "拒绝访问(403)";
          break;
        case 404:
          error.message = "请求出错(404)";
          router.push("/404");
          break;
        case 408:
          error.message = "请求超时(408)";
          break;
        case 500:
          error.message = "服务器错误(500)";
          router.push("/500");
          break;
        case 501:
          error.message = "服务未实现(501)";
          break;
        case 502:
          error.message = "网络错误(502)";
          break;
        case 503:
          error.message = "服务不可用(503)";
          break;
        case 504:
          error.message = "网络超时(504)";
          break;
        case 505:
          error.message = "HTTP版本不受支持(505)";
          break;
        default:
          error.message = `连接出错(${error.response.status})!`;
      }
    } else {
      error.message = "连接服务器失败!";
    }
    return Promise.reject(error);
  }
);
 
export default Axios;