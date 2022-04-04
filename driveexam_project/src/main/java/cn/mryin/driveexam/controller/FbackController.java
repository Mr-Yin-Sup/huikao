package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.pojo.FBack;
import cn.mryin.driveexam.service.FBackService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/12/10
 */
@Controller
@CrossOrigin
@UserLoginToken
public class FbackController {
    @Autowired
    FBackService service;

    @ResponseBody
    @RequestMapping("/admin/findAllFback")
   public PageInfo findAllFback(int pageNum, int pageSize){

        return service.findAll(pageNum, pageSize);
    }

    @ResponseBody
    @RequestMapping("/admin/handleFBack")
    public String handleFBack(int state,int id){
        int i = service.handleState(state, id);
        if (i>0){
            return "success";
        }
        return "error";
    }


    @ResponseBody
    @PostMapping("/admin/handleCheck")//批量处理
    public String deleteCheck(String checks) {
        return service.handelCheck(checks);
    }
}

