package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.service.ErrSubjectService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author MrYin
 * @date 2021/12/11
 */
@Controller
@CrossOrigin
@UserLoginToken
public class ErrorSubjectController {

    @Autowired
    private ErrSubjectService errSubjectService;

    @RequestMapping("/admin/findAllErrSubject")
    @ResponseBody
    public PageInfo findAllErrSubject(int pageNum,int pageSize){
        return errSubjectService.findAll(pageNum, pageSize);
    }



    @ResponseBody
    @RequestMapping("/admin/handleErrSubject")//删除题目纠错
    public String handleErrSubject(int state,int id){
        int i = errSubjectService.handleState(state, id);
        if (i>0){
            return "success";
        }
        return "error";
    }

    @ResponseBody
    @RequestMapping("/admin/handleErrSubjectBySId")//删除题目纠错通过题目Id
    public String handleErrSubjectBySID(int state,int sId){
        int i = errSubjectService.handleStateBySId(state, sId);
        if (i>0){
            return "success";
        }
        return "error";
    }


    @ResponseBody
    @PostMapping("/admin/errSubjectHandleCheck")//批量处理
    public String errSubjectHandleCheck(String checks) {
        return errSubjectService.handelCheck(checks);
    }

}
