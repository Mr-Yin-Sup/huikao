package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.PassToken;
import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.mapper.SubjectMapper;
import cn.mryin.driveexam.mapper.SubjectProportionMapper;
import cn.mryin.driveexam.pojo.Chapter;
import cn.mryin.driveexam.pojo.KnowPoints;
import cn.mryin.driveexam.pojo.Subject;
import cn.mryin.driveexam.pojo.SubjectProportion;
import cn.mryin.driveexam.service.ChapterService;
import cn.mryin.driveexam.service.KnowPointsService;
import cn.mryin.driveexam.service.SubjectSevice;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/3
 */
@Controller
@UserLoginToken
public class TemplatesController {


    @Autowired
    SubjectSevice subjectSevice;

    @Autowired
    ChapterService chapterService;

    @Autowired
    KnowPointsService knowPointsService;

    @PassToken
    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @UserLoginToken
    @RequestMapping(method = RequestMethod.GET, path = {"/admin/index", "/"})
    public String index() {
        return "index";
    }

    @UserLoginToken
    @GetMapping("/admin/productList")
    public String productlist() {
    //public String productlist(Model model) {
        //PageInfo<Subject> allUserByPageS = subjectSevice.findAllUserByPageS(1, 10);//默认进来第一页,显示10条
        //model.addAttribute("page_size", 10);
        //model.addAttribute("page_num", 1);
        //model.addAttribute("page_total", allUserByPageS.getTotal());
        //model.addAttribute("subjectList",subjectSevice.formatSubjectList( allUserByPageS.getList()));
        //model.addAttribute("knowPoints", knowPointsService.findAll());


        return "productList";
    }

    @UserLoginToken
    @GetMapping("/admin/productAdd")
    public String productAdd(Model model) {
        List<KnowPoints> knowPoints = knowPointsService.findAll();
        List<Chapter> chapters = chapterService.findByKm("1");

        model.addAttribute("knowPoints", knowPoints);
        model.addAttribute("chapters", chapters);
        return "productAdd";
    }

    @UserLoginToken
    @GetMapping("/admin/productMod")//修改
    public String productMod(HttpServletRequest request, Model model) {
        List<KnowPoints> knowPoints = knowPointsService.findAll();


        String sId = request.getParameter("sId");//获得要修改的题号
        Subject subject = subjectSevice.selectOneSubject(Integer.parseInt(sId));
        List<Chapter> chapters = chapterService.findByKm(subject.getsKm());
        model.addAttribute("subject", subject);
        model.addAttribute("knowPoints", knowPoints);
        model.addAttribute("chapters", chapters);
        return "productMod";
    }

    @UserLoginToken
    @GetMapping("/admin/chaperList")
    public String chaperList(){
        return "chaperList";
    }

    @UserLoginToken
    @GetMapping("/admin/knowPointList")
    public String knowpointList(){
        return "knowPointList";
    }

    @UserLoginToken
    @GetMapping("/admin/examSet")
    public String examSet(){
        return "examSet";
    }

    @UserLoginToken
    @GetMapping("/admin/icnoList")
    public String icnoList(){
        return "icnoList";
    }

    @UserLoginToken
    @GetMapping("/admin/fBackList")
    public String fBackList(){
        return "fBackList";
    }

    @UserLoginToken
    @GetMapping("/admin/errSubjectList")
    public String errSubjectList(){
        return "errSubjectList";
    }

    @UserLoginToken
    @GetMapping("/admin/userList")
    public String userList(){
        return "userList";
    }

    @UserLoginToken
    @GetMapping("/admin/icnoTypeList")
    public String icnoTypeList(){
        return "icnoTypeList";
    }
}
