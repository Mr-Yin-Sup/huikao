package cn.mryin.driveexam.service;

import cn.mryin.driveexam.mapper.ChapterMapper;
import cn.mryin.driveexam.pojo.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/6
 */
@Service
public class ChapterService {

    @Autowired
    ChapterMapper chapterMapper;

   public List<Chapter> findByKm(String id){
        return chapterMapper.findByKm(id);
    }

    public List<Chapter> findAll(){
        List<Chapter> all = chapterMapper.findAll();

        return this.formatChapter(all);
    }


    private List<Chapter> formatChapter(List<Chapter> chapters){
       String str="";
        for (Chapter chapter : chapters) {
            String[] split = chapter.getChapterCar().split("");
            for (String s : split) {
                switch (s){
                    case "1":str+="小车、";break;
                    case "2":str+="货车、";break;
                    case "3":str+="客车、";break;
                    case "4":str+="摩托车、";break;
                }
            }

            chapter.setChapterCar(str.substring(0,str.length()-1));
            str="";

            chapter.setKm(chapter.getKm().equals("1")?"科目一":"科目四");
        }
        return  chapters;
    }

    //添加章节
    public String addChatper(Chapter chapter){
        int insert = chapterMapper.insert(chapter);
        if (insert>0){
            return "success";
        }
        return "error";
    }

    //删除章节
    public String deleteChapter(int chapterId){
       int i=0;
       try {
           i = chapterMapper.deleteByPrimaryKey(chapterId);
       }catch (Exception e){
           return "章节可能在考试设置中已经设置了比例,无法删除!若想删除请先把对应的考试比例设置为0%";
       }

        if (i>0)return "success";
        return "error";
    }

    //修改章节
    public String updateChatper(Chapter chapter){
        int i = chapterMapper.updateByPrimaryKey(chapter);
        if (i>0){
            return "success";
        }
        return "error";
    }
}
