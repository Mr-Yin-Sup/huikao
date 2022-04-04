package cn.mryin.driveexam.config;

import cn.mryin.driveexam.handler.AuthenticationInterceptor;
import cn.mryin.driveexam.handler.LoginHandlerInterceptor;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**
 * @author MrYin
 * @date 2021/11/4
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //没登录拦截admin下的所有请求
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/admin/**")
                .addPathPatterns("/")
                .excludePathPatterns(
                "/login",            //登录
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf",
                "/**/*.otf",
                "/**/*.eot",
                "/**/*.woff2"
        );


    }


    //设置文件上传大小
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //指定文件大小
        factory.setMaxFileSize(DataSize.ofMegabytes(10));//10MB
        /// 设定上传文件大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(1));
        return factory.createMultipartConfig();
    }


    //设置路径映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/temp-rainy/");

        //-------------
        //获取文件的真实路径
       // String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploadFile\\";
        String path=System.getProperty("user.dir")+"\\uploadFile\\";
        //static/upload/**是对应resource下工程目录
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("file:"+path);
    }



}
