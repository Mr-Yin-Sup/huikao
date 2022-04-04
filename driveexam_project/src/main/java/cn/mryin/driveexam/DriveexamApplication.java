package cn.mryin.driveexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableCaching//开启缓存支持
@EnableAsync//开启异步线程支持
public class DriveexamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriveexamApplication.class, args);
    }


}
