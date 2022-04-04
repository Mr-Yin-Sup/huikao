package cn.mryin.driveexam.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KnowPoints implements Serializable {
    private Integer knowId;

    private String knowName;

    private String knowKm;

    private String knowCar;

    //List<Subject> subjects;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getKnowId() {
        return knowId;
    }

    public void setKnowId(Integer knowId) {
        this.knowId = knowId;
    }

    public String getKnowName() {
        return knowName;
    }

    public void setKnowName(String knowName) {
        this.knowName = knowName;
    }

    public String getKnowKm() {
        return knowKm;
    }

    public void setKnowKm(String knowKm) {
        this.knowKm = knowKm;
    }

    public String getKnowCar() {
        return knowCar;
    }

    public void setKnowCar(String knowCar) {
        this.knowCar = knowCar;
    }
}