package cn.mryin.driveexam.handler;

import cn.mryin.driveexam.pojo.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MrYin
 * @date 2021/11/4
 * 登录拦截器配置
 */

@Configuration
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截非管理员
        User login = (User) request.getSession().getAttribute("user");

        if (login==null || login.getuType().equals("0")){
            response.sendRedirect("/login");
        }else{
            return true;
        }
        return false;
    }
}
