package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.PassToken;
import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.mapper.SubjectMapper;
import cn.mryin.driveexam.mapper.UserMapper;
import cn.mryin.driveexam.pojo.Subject;
import cn.mryin.driveexam.pojo.User;
import cn.mryin.driveexam.service.SubjectSevice;
import cn.mryin.driveexam.utils.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

/**
 * @author MrYin
 * @date 2021/11/4
 */

@Controller
@CrossOrigin
@UserLoginToken
public class AdminController {

    @Autowired
    SubjectSevice subjectSevice;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    UserMapper userMapper;

    @ResponseBody
    @PostMapping("/upload")
    public JSON uploadImg(MultipartFile file, HttpServletRequest request) {

        JSONObject json = new JSONObject();
        String url = subjectSevice.uploadImg(file, request);

        json.put("code", 0);

        json.put("msg", "null");

        JSONObject json2 = new JSONObject();
        json2.put("src", url);
        json2.put("title", "");
        json.put("Data", json2);
        return json;
    }

    //@ResponseBody
    //@GetMapping("/testSeach")
    //public List<Subject> test() {
    //    Subject subject = new Subject();
    //    subject.setsQuestion("311");
    //    System.out.println(subject);
    //    List<Subject> subjects = subjectMapper.seachSubject(subject);
    //    System.out.println(subjects);
    //    return subjects;
    //}

    /**
     * 效验旧密码是否正确
     * @param password
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/admin/checkPassword")
    public String checkPassword(String password,HttpServletRequest request){
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");
        if (password.equals(user.getuPassword())){
            return "success";
        }
        return "密码不相同";
    }


    @ResponseBody
    @PostMapping("/admin/updateAdminPassword")
    public String updateAdminPassword(String password,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        user.setuPassword(password);
        int i = userMapper.updateByPrimaryKey(user);

        if(i>0){
            return "success";
        }
        return "error";

    }


    @ResponseBody
    @PostMapping("/doLogin")
    public  String doLogin(User user, HttpServletRequest request){

        User login = userMapper.findLogin(user);
        String token = TokenUtil.getToken(login);
        System.out.println(token);
        request.getSession().setAttribute("user",login);
        request.getSession().setMaxInactiveInterval(60*60*24);//session过期时间 一天
        if (login!=null){
            return token;
        }else {
            return "error";
        }
    }


    @PassToken
    @GetMapping("/loginOut")
    public  String loginOut(HttpServletRequest request){
        request.getSession().invalidate();
        return "login";
    }


}
