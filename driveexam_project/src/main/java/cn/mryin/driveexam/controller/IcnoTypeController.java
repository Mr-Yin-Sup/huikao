package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.pojo.IcnoType;
import cn.mryin.driveexam.service.IcnoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/16
 */
@Controller
@CrossOrigin
@UserLoginToken
public class IcnoTypeController {
    @Autowired
    IcnoTypeService icnoTypeService;

    @PostMapping("/admin/findAllIcnoType")
    @ResponseBody
    public List<IcnoType> findAllIcnoType(){
        return icnoTypeService.findAll();
    }

    @RequestMapping("/admin/addIcnoType")
    @ResponseBody
    public String addIcnoType(IcnoType icnoType){

        int insert = icnoTypeService.insert(icnoType);
        if (insert>0)return "success";
        return "error";
    }

    @RequestMapping("/admin/deleteIcnoTypeById")
    @ResponseBody
    public String deleteIcnoTypeById(int icnoTypeId){
        int i = icnoTypeService.deleteIcnoTypeById(icnoTypeId);
        if(i>0)return "success";
        return "error";
    }

    @RequestMapping("/admin/modIcnoTypeById")
    @ResponseBody
    public String modIcnoTypeById(IcnoType icnoType){
        int i = icnoTypeService.updateByPrimaryKey(icnoType);
        if(i>0)return "success";
        return "error";
    }

}
