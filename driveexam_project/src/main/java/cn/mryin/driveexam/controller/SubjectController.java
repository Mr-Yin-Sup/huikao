package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.pojo.Subject;
import cn.mryin.driveexam.pojo.User;
import cn.mryin.driveexam.service.KnowPointsService;
import cn.mryin.driveexam.service.SubjectSevice;
import cn.mryin.driveexam.utils.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/6
 */
@Controller
@UserLoginToken
@CrossOrigin
public class SubjectController {

    @Autowired
    SubjectSevice subjectSevice;

    @Autowired
    KnowPointsService knowPointsService;

    @ResponseBody
    @PostMapping("/admin/addSubject")//添加题目
    public String addSubject(Subject subject) {
        int i = subjectSevice.addSubject(subject);

        return i > 0 ? "success" : "error";

    }


    @ResponseBody
    @PostMapping("/admin/modSubject")//修改题目
    public String modSubject(Subject subject) {

        return subjectSevice.modSubject(subject);

    }

    @ResponseBody
    @GetMapping("/admin/deleteSubject")//删除题目
    public String deleteSubject(int sId) {
        return subjectSevice.deleteSubject(sId);

    }

    @ResponseBody
    @GetMapping("/admin/findAllSubject")//查询所有题目
    public JSON findAllSubject(int pageNum, int pageSize  ) {

        return subjectSevice.findAll(pageNum, pageSize);


    }

    @ResponseBody
    @GetMapping("/admin/findAllSubject2")//查询所有题目2
    public JSON findAllSubject2(int pageNum, int pageSize,HttpServletRequest request) {

        JSONObject json = subjectSevice.findAll(pageNum, pageSize);
        // "knowPoints", knowPointsService.findAll()
        json.put("knowPoints",knowPointsService.findAll());
        //model.addAttribute("knowPoints", knowPointsService.findAll());

        return json;

    }

    @ResponseBody
    @PostMapping("/admin/deleteCheck")//删除多个题目
    public String deleteCheck(String checks) {

        return subjectSevice.deleteCheck(checks);
    }

    @ResponseBody
    @PostMapping("/admin/modSubjcetState")//修改题目是否启用
    public String modSubjcetState(String sId, String state) {
        return subjectSevice.modSubjectState(sId, state);
    }

    @ResponseBody
    @PostMapping(value = "/admin/seachSubject", produces = "application/json;charset=UTF-8")//搜索题目
    public JSON seachSubject(int pageNum, int pageSize, Subject seach) {

        return subjectSevice.seachSubject(seach, pageNum, pageSize);
    }


    @ResponseBody
    @PostMapping("/admin/addSubjectByExcel")//Excel批量导入题目
    public JSON addSubjectByExcel(MultipartFile file){
        return subjectSevice.addSubjectByExcel(file);
    }

    @ResponseBody
    @RequestMapping("/admin/updateExcelTemplate")//更新Excel表格模板
    public String updateExcelTemplate(){
        return subjectSevice.updateExcelTemplate();
    }
}
