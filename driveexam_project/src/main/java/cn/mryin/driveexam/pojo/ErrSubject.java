package cn.mryin.driveexam.pojo;

import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ErrSubject implements Serializable {

  private long errId;
  private long sId;
  private long uId;
  private String errMsg;
  private String errState;
  private String time;
  private User user;
  private Subject subject;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public long getsId() {
    return sId;
  }

  public void setsId(long sId) {
    this.sId = sId;
  }

  public long getuId() {
    return uId;
  }

  public void setuId(long uId) {
    this.uId = uId;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public long getErrId() {
    return errId;
  }

  public void setErrId(long errId) {
    this.errId = errId;
  }


  public long getSId() {
    return sId;
  }

  public void setSId(long sId) {
    this.sId = sId;
  }


  public long getUId() {
    return uId;
  }

  public void setUId(long uId) {
    this.uId = uId;
  }


  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }


  public String getErrState() {
    return errState;
  }

  public void setErrState(String errState) {
    this.errState = errState;
  }

}
