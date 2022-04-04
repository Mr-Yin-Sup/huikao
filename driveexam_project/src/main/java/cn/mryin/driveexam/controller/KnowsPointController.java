package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.mapper.KnowPointsMapper;
import cn.mryin.driveexam.pojo.KnowPoints;
import cn.mryin.driveexam.service.KnowPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/12
 */
@Controller
@CrossOrigin
@UserLoginToken
public class KnowsPointController {

    @Autowired
    KnowPointsService knowPointsService;

    //拉取全部知识点
    @ResponseBody
    @PostMapping("/admin/findAllKnowPoint")
    List<KnowPoints> findAllKnowPoint(){
        return knowPointsService.findAll();
    }
    //添加知识点
    @ResponseBody
    @PostMapping("/admin/addKnowPoint")
    String addKnowPoint(KnowPoints knowPoints){
       return knowPointsService.addKnowPoint(knowPoints);
    }

    //删除知识点
    @ResponseBody
    @PostMapping("/admin/deleteKnowPoint")
    String deleteKnowPoint(int knowId){
        return knowPointsService.deleteKnowPoint(knowId);
    }
    //修改知识点
    @ResponseBody
    @PostMapping("/admin/modKnowPoint")
    public String modKnowPoint(KnowPoints knowPoints) {
        return  knowPointsService.modKnowPoint(knowPoints);
    }
}
