package edu.lgxy.lbj.asms.service;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private Integer code;
    @Value("${spring.mail.username}")
    private String from="";
    @Autowired
    private JavaMailSender javaMailSender;
    public String email(String to) {
        code = RandomUtil.randomInt(100000, 999999);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("高校竞赛管理系统，用户密码找回验证");
        simpleMailMessage.setText("尊敬的用户您好！\n" +
                "\n" +
                "您的验证码是：" + code.toString()+"，请在 5 分钟内进行验证。如果该验证码不为您本人申请，请无视。\t");
        javaMailSender.send(simpleMailMessage);
        return code.toString();
    }
}