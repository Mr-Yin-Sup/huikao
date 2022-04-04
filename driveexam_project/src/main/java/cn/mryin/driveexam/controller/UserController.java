package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.mapper.UserMapper;
import cn.mryin.driveexam.pojo.User;
import cn.mryin.driveexam.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author MrYin
 * @date 2021/12/12
 */
@Controller
@CrossOrigin
@UserLoginToken
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/findAllUser")
    @ResponseBody
    public PageInfo findAllUser(int pageNum,int pageSize,String uName){

        return userService.findAll(pageNum,pageSize,uName);
    }

    @RequestMapping("/admin/setUserState")
    @ResponseBody
    public String setUserState(int uId,String uState){
        int i = userService.setUserState(uId, uState);
        if (i>0)return "success";
        return "error";
    }

    @RequestMapping("/admin/modUserInfo")
    @ResponseBody
    public String modUserInfo(User user){
        try {
            int i = userService.updateByPrimaryKey(user);

            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

    }
}
