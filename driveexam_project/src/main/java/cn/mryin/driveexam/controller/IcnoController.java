package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.pojo.Icno;
import cn.mryin.driveexam.service.IcnoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author MrYin
 * @date 2021/11/15
 */
@CrossOrigin
@Controller
@UserLoginToken
public class IcnoController {
    @Autowired
    IcnoService icnoService;

    @ResponseBody
    @PostMapping("/admin/findAllIcno")
    public PageInfo findAllIcno(int pageNum,int pageSize){
        return icnoService.findAll(pageNum,pageSize);
    }

    @ResponseBody
    @PostMapping("/admin/addIcno")
    public String addIcno(Icno icno){
        return icnoService.addIcno(icno);
    }

    @ResponseBody
    @PostMapping("/admin/delIcno")
    public String delIcno(int icnoId){
        return icnoService.delIcno(icnoId);
    }

    @ResponseBody
    @PostMapping("/admin/modIcno")
    public String modIcno(Icno icno){

        return icnoService.modIcno(icno);
    }

    @ResponseBody
    @PostMapping("/admin/seachIcno")
    public PageInfo seachIcno(int pageNum,int pageSize, Icno icno){

        return icnoService.seachIcno(pageNum,pageSize,icno);
    }
}
