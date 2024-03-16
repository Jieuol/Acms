package edu.lgxy.lbj.asms.qo;

import lombok.Data;

@Data
public class ReceiveEmail {
    private String username;
    private String email;
    private String verifyCode;
    private String code;
}
