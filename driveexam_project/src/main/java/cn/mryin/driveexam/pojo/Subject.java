package cn.mryin.driveexam.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Builder
public class Subject implements Serializable {
    private Integer sId;

    //private static  final String dir="";

    @Excel(name="题目")
    private String sQuestion;

    @Excel(name = "图片",type = 2 ,savePath = "uploadFile")
    private String sImg;

    @Excel(name="题目类型")
    private String sType;

    @Excel(name="所属科目")
    private String sKm;

    private String sCar;

    @Excel(name="选项A")
    private String itemA;

    @Excel(name="选项B")
    private String itemB;

    @Excel(name="选项C")
    private String itemC;

    @Excel(name="选项D")
    private String itemD;

    @Excel(name="答案")
    private String answer;

    @Excel(name="解析")
    private String sExplain;

    @Excel(name="所属章节")
    private Integer chapterId;

    @Excel(name="知识点")
    private Integer knowId;

    private Date addTime;

    private Date modTime;

    private Long successCount;

    private Long errorCount;

    private String sDisable;

    private Chapter chapter;

    private KnowPoints knowPoints;


    private String userAnswer="";

    private boolean userFavor;

    public boolean isUserFavor() {
        return userFavor;
    }

    public void setUserFavor(boolean userFavor) {
        this.userFavor = userFavor;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = "";
    }

    private Car car;



    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public KnowPoints getKnowPoints() {
        return knowPoints;
    }

    public void setKnowPoints(KnowPoints knowPoints) {
        this.knowPoints = knowPoints;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getsQuestion() {
        return sQuestion;
    }

    public void setsQuestion(String sQuestion) {
        this.sQuestion = sQuestion;
    }

    public String getsImg() {
        return sImg;
    }

    public void setsImg(String sImg) {
        this.sImg = sImg;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public String getsKm() {
        return sKm;
    }

    public void setsKm(String sKm) {
        this.sKm = sKm;
    }


    public String getsCar() {
        return sCar;
    }

    public void setsCar(String sCar) {
        this.sCar = sCar;
    }

    public String getItemA() {
        return itemA;
    }

    public void setItemA(String itemA) {
        this.itemA = itemA;
    }

    public String getItemB() {
        return itemB;
    }

    public void setItemB(String itemB) {
        this.itemB = itemB;
    }

    public String getItemC() {
        return itemC;
    }

    public void setItemC(String itemC) {
        this.itemC = itemC;
    }

    public String getItemD() {
        return itemD;
    }

    public void setItemD(String itemD) {
        this.itemD = itemD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getsExplain() {
        return sExplain;
    }

    public void setsExplain(String sExplain) {
        this.sExplain = sExplain;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getKnowId() {
        return knowId;
    }

    public void setKnowId(Integer knowId) {
        this.knowId = knowId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Long successCount) {
        this.successCount = successCount;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    public String getsDisable() {
        return sDisable;
    }

    public void setsDisable(String sDisable) {
        this.sDisable = sDisable;
    }
}