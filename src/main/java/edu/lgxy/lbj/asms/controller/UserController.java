package edu.lgxy.lbj.asms.controller;


import edu.lgxy.lbj.asms.entity.User;
import edu.lgxy.lbj.asms.qo.Receive;
import edu.lgxy.lbj.asms.qo.ReceiveEmail;
import edu.lgxy.lbj.asms.qo.ReceivePassword;
import edu.lgxy.lbj.asms.service.UserService;
import edu.lgxy.lbj.asms.config.JsonResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @RequestMapping("/login")
    public JsonResult<Map> login(@RequestBody Receive receive ,HttpSession session){
        Map<String, Object> map = new HashMap<>();
        redisTemplate.opsForValue().set("name","lbj!!!!!!!");
        log.info("redis->"+redisTemplate.opsForValue().get("name"));
        String username = receive.getUsername();
        String password =receive.getPassword();
        String msg="服务器正常";
        String code="";
        log.info("log---->从前端收到:"+username);
        log.info("log---->从前端收到:"+password);
        if(username == null||" ".equals(username)){
            return new JsonResult<>(map);
        }
        username = username.trim();//去除字符串首尾空白字符
        if(username.length()<6||username.length()>20){
            msg= "用户名应为6-20位";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        if(password == null||" ".equals(password)){
            msg= "密码不能为空";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        password = password.trim();
        if(password.length()<6||password.length()>20){
            msg= "密码应为6-20位";
            code="202";
            return new JsonResult<>(map,msg,code);
        }

        User u = new User();
        u.setUsername(username);
        log.info("u : "+u);
        User user = userService.selectByUserName(u);
        log.info("Mysql中查到user:"+user);
        if(user == null || !password.equals(user.getPassword())){
            msg= "用户名不存在或者密码错误";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        msg="登陆成功!";
        return new JsonResult<>(map,msg);
    }
    @RequestMapping("/forgot/checkEmail")
    public JsonResult<Map> forget_checkEmail(@RequestBody ReceiveEmail receiveEmail,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        String email = receiveEmail.getEmail();
        String username = receiveEmail.getUsername();
        String msg="服务器正常";
        String code="";
        if(username==null||"".equals(username)){
            code="202";
            msg="密码不能为空";
            return new JsonResult<>(map,msg,code);
        }
        username = username.trim();//去除字符串首尾空白字符
        if(username.length()<6||username.length()>20){
            msg= "用户名应为6-20位";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        if(email==null||"".equals(email)){
            code="202";
            msg="邮箱不能为空";
            return new JsonResult<>(map,msg,code);
        }
        User u = new User();
        u.setUsername(username);
        User user = userService.selectByUserName(u);
        map.put("username",username);
        if(user==null){
            code="202";
            msg="账户不存在";
            return new JsonResult<>(map,msg,code);
        }

        if(!user.getEmail().equals(email)){
            code="202";
            msg="邮箱验证失败";
            return new JsonResult<>(map,msg,code);
        }
        msg="验证成功，请修改您的密码";
        code="0";
        return new JsonResult<>(map,msg,code);

    }

    @RequestMapping("/forgot/changePsw")
    public JsonResult<Map> forget_changePsw(@RequestBody ReceivePassword receivePassword){
        Map<String,Object>map = new HashMap<>();
        String msg="服务器正常";
        String code="";
        log.info("从前端收到:"+receivePassword);
        String username = receivePassword.getUsername();
        String password2 =receivePassword.getPassword();
        String password = receivePassword.getConfirmPwd();
        username = username.trim();//去除字符串首尾空白字符
        if(password == null||" ".equals(password)){
            msg= "密码不能为空";
            return new JsonResult<>(map,msg,code);
        }
        password2 = password2.trim();
        if(password2.length()<6||password2.length()>20){
            msg= "密码为6-20位";
            return new JsonResult<>(map,msg,code);
        }
        if(!password2.equals(password)){
            msg= "密码不一致";
            return new JsonResult<>(map,msg,code);
        }
        User user1 = new User();
        user1.setUsername(username);
        user1.setPassword(password2);
        log.info("log---->从前端收到:"+receivePassword);
        int updatePassword = userService.updatePassword(user1);

        if(updatePassword<=0){
            msg="更新失败";
            return new JsonResult<>(map,msg,code);
        }
        code="0";
        msg="已经成功修改密码";
        return new JsonResult<>(map,msg,code);
    }
}
