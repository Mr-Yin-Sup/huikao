package cn.mryin.driveexam.handler;

import cn.mryin.driveexam.annotation.PassToken;
import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.controller.AdminController;
import cn.mryin.driveexam.mapper.UserMapper;
import cn.mryin.driveexam.pojo.User;
import cn.mryin.driveexam.service.UserService;
import cn.mryin.driveexam.utils.TokenUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author MrYin
 * @date 2022/3/31
 */
@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    AdminController adminController;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }


        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                Cookie[] cookies = request.getCookies();
                String token = null;
                for (Cookie c : cookies) {
                    if (c.getName().equals("token")) {
                        token = c.getValue();
                        break;
                    }
                }
                // 执行认证
                if (token == null) {
                    try {
                        System.out.println("token为空");
                        response.sendRedirect("/login");
                    } catch (IOException e) {
                        System.out.println("io异常");
                    }
                    return false;
                }


                try {
                    User user = TokenUtil.passToken(token);
                    if (!user.getuType().equals("1")){
                        System.out.println("用户身份不对");
                        response.sendRedirect("/login");
                        return false;
                    }
                    return true;
                } catch (Exception j) {
                    System.out.println("token验证不通过");
                    try {
                        response.sendRedirect("/login");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   return false;
                }


            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}