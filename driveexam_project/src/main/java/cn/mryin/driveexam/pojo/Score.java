package cn.mryin.driveexam.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Score implements Serializable {
    private Integer scoreId;

    private Integer uId;

    private String km;

    private String carType;



    private Byte score;


    private Date useTime;


    private Date examTime;

    private Byte successCount;

    private Byte errorCount;

    private Byte unan;

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Byte getScore() {
        return score;
    }

    public void setScore(Byte score) {
        this.score = score;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public Byte getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Byte successCount) {
        this.successCount = successCount;
    }

    public Byte getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Byte errorCount) {
        this.errorCount = errorCount;
    }

    public Byte getUnan() {
        return unan;
    }

    public void setUnan(Byte unan) {
        this.unan = unan;
    }
}