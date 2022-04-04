package cn.mryin.driveexam.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Icno implements Serializable {
    private Integer icnoId;

    private Integer icnoTypeId;

    private String icnoName;

    private String icnoExplain;

    private String icnoImg;

    private IcnoType icnoType;




    public Integer getIcnoId() {
        return icnoId;
    }

    public void setIcnoId(Integer icnoId) {
        this.icnoId = icnoId;
    }

    public Integer getIcnoTypeId() {
        return icnoTypeId;
    }

    public void setIcnoTypeId(Integer icnoTypeId) {
        this.icnoTypeId = icnoTypeId;
    }

    public String getIcnoName() {
        return icnoName;
    }

    public void setIcnoName(String icnoName) {
        this.icnoName = icnoName;
    }

    public String getIcnoExplain() {
        return icnoExplain;
    }

    public void setIcnoExplain(String icnoExplain) {
        this.icnoExplain = icnoExplain;
    }

    public String getIcnoImg() {
        return icnoImg;
    }

    public void setIcnoImg(String icnoImg) {
        this.icnoImg = icnoImg;
    }

    public IcnoType getIcnoType() {
        return icnoType;
    }

    public void setIcnoType(IcnoType icnoType) {
        this.icnoType = icnoType;
    }
}