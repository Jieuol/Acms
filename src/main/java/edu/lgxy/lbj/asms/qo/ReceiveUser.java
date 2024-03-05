package edu.lgxy.lbj.asms.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
@Data
public class ReceiveUser {
    private String userId;
    private String state;
    private String userGroup;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String loginTime;
    private String phone;
    private String phoneState;
    private String username;
    private String password;
    private String email;
    private String emailState;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    private String realname;

}
