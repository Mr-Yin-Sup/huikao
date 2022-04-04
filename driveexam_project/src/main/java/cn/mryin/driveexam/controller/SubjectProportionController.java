package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.pojo.Score;
import cn.mryin.driveexam.pojo.SubjectProportion;
import cn.mryin.driveexam.service.SubjectProportionService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author MrYin
 * @date 2021/11/13
 */

@Controller
@CrossOrigin
@UserLoginToken
public class SubjectProportionController {
    @Autowired
    SubjectProportionService subjectProportionService;

    @PostMapping("/admin/findallSubjectProportions")
    @ResponseBody
    public PageInfo<SubjectProportion> findallSubjectProportions(){
        return subjectProportionService.findAll();
    }

    @PostMapping("/admin/findExamSet")
    @ResponseBody
    public List<Map<String, String>> findExamSex(){
        List<Map<String, String>> carAndKm = subjectProportionService.findCarAndKm();

        return carAndKm;
    }

    //查找考试设置,通过车型和科目
    @PostMapping("/admin/findExamSetByCarAndKm")
    @ResponseBody
    public JSON findExamSetByCarAndKm(String car, String km){

        return subjectProportionService.findExamSetByCarAndKm(car,km);
    }

    @PostMapping("/admin/modExamSet")//修改考试设置
    @ResponseBody
    public String modExamSet(@RequestParam(value = "ids[]") int[] ids,@RequestParam(value = "propors[]") int[] propors,@RequestParam(value = "car") int car,@RequestParam(value = "km") int km ){
        return subjectProportionService.modExamSet(ids,propors,car,km);
    }


}
