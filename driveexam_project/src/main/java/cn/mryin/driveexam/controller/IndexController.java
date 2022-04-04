package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.mapper.CarMapper;
import cn.mryin.driveexam.mapper.IcnoTypeMapper;
import cn.mryin.driveexam.mapper.SubjectMapper;
import cn.mryin.driveexam.mapper.UserMapper;
import cn.mryin.driveexam.pojo.Icno;
import cn.mryin.driveexam.pojo.IcnoType;
import cn.mryin.driveexam.pojo.Subject;
import cn.mryin.driveexam.pojo.User;
import com.alibaba.fastjson.JSON;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Console;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/3
 */

@Controller
@CrossOrigin
@UserLoginToken
public class IndexController implements Serializable {


    @Autowired
    CarMapper carMapper;

    @Autowired
    SubjectMapper subjectMapper;



    @Autowired
    UserMapper userMapper;


    @Autowired
    IcnoTypeMapper icnoTypeMapper;


    @ResponseBody
    @GetMapping("/all")
    public String test (){
        List<Subject> allByCarAndKm = subjectMapper.findAllByCarAndKm("2", "1");
        System.out.println(allByCarAndKm);

        return JSON.toJSONString(allByCarAndKm);
    }




}
