package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.mapper.ChapterMapper;
import cn.mryin.driveexam.pojo.Chapter;
import cn.mryin.driveexam.service.ChapterService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author MrYin
 * @date 2021/11/6
 */
@Controller
@CrossOrigin
@UserLoginToken
public class ChapterController {

    @Autowired
    ChapterService chapterService;


    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST},value = "/admin/findChapterByKm")
    public JSON findChapterByKm(String id){
        JSONObject object=new JSONObject();
        List<Chapter> byKm = chapterService.findByKm(id);
        object.put("data",byKm);

        return object;
    }

    @ResponseBody
    @PostMapping("/admin/findAllChapter")
    //查询所有章节信息
    public List<Chapter> findAllChapter(){
        return chapterService.findAll();
    }

    @ResponseBody
    @PostMapping("/admin/addChapter")
    //添加章节
    public String addChapter(Chapter chapter){
      return chapterService.addChatper(chapter);
    }

    //删除章节
    @ResponseBody
    @PostMapping("/admin/deleteChatper")
    public String deleteChapter(String chapterId){

       return chapterService.deleteChapter(Integer.parseInt(chapterId));
    }

    @ResponseBody
    @PostMapping("/admin/modChapter")
    public String modChapter(Chapter chapter){

        return chapterService.updateChatper(chapter);
    }
}
