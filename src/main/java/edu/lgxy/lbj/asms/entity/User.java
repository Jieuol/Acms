package edu.lgxy.lbj.asms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class User {
  private long userId;
  private long state;
  private String userGroup;
  private String gender;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String loginTime;
  private String phone;
  private long phoneState;
  private String username;
  private String password;
  private String email;
  private long emailState;
  private String avatar;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String createTime;
  private String realname;

}
