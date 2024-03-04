package edu.lgxy.lbj.asms.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
  private long userId;
  private long state;
  private String userGroup;
  private Date loginTime;
  private String phone;
  private long phoneState;
  private String username;
  private String nickname;
  private String password;
  private String email;
  private long emailState;
  private String avatar;
  private Date createTime;

}
