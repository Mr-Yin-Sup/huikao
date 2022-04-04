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
public class User implements Serializable {
    private Integer uId;

    private String uName;

    private String uSex;

    private String uNumber;

    private String uPassword;

    private String uPhone;

    private String uImg;

    private String uWxid;

    private String uType;

    private String uCar;

    private String uCollection;

    private String uState;

    public String getuState() {
        return uState;
    }

    public void setuState(String uState) {
        this.uState = uState;
    }

    public String getuCollection() {
        return uCollection;
    }

    public void setuCollection(String uCollection) {
        this.uCollection = uCollection;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuImg() {
        return uImg;
    }

    public void setuImg(String uImg) {
        this.uImg = uImg;
    }

    public String getuWxid() {
        return uWxid;
    }

    public void setuWxid(String uWxid) {
        this.uWxid = uWxid;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getuCar() {
        return uCar;
    }

    public void setuCar(String uCar) {
        this.uCar = uCar;
    }
}