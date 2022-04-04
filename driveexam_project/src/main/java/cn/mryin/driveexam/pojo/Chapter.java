package cn.mryin.driveexam.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chapter implements Serializable {
    private Integer chapterId;

    private String chapterName;

    private String km;

    private String chapterCar;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //该章节下拥有的题目
    private List<Subject> subjects;

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getChapterCar() {
        return chapterCar;
    }

    public void setChapterCar(String chapterCar) {
        this.chapterCar = chapterCar;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }


}