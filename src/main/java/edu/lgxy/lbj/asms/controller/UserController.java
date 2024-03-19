package edu.lgxy.lbj.asms.controller;


import edu.lgxy.lbj.asms.config.*;
import edu.lgxy.lbj.asms.entity.Student;
import edu.lgxy.lbj.asms.entity.User;
import edu.lgxy.lbj.asms.qo.*;
import edu.lgxy.lbj.asms.service.EmailService;
import edu.lgxy.lbj.asms.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private EmailService emailService;

    @RequestMapping("/getUseInfo")
    public JsonResult<Map> login(@RequestParam String username, HttpSession session) throws ParseException {
        Map<String, Object> map = new HashMap<>();
//        redisTemplate.opsForValue().set("name","lbj!!!!!!!");
//        log.info("redis->"+redisTemplate.opsForValue().get("name"));
        String msg="服务器正常";
        String code="";
        log.info("log---->从前端收到:"+username);
        if(username == null||" ".equals(username)){
            return new JsonResult<>(map);
        }
        username = username.trim();//去除字符串首尾空白字符
        if(username.length()<6||username.length()>20){
            msg= "用户名应为6-20位";
            code="202";
            return new JsonResult<>(map,msg,code);
        }

        User u = new User();
        u.setUsername(username);
        log.info("u : "+u);
        User user = userService.selectByUserName(u);
        log.info("Mysql中查到user:"+user);
        Student student = userService.selectStudentInfo(user.getUserId());
        if(user == null){
            msg= "用户名不存在";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
//        String dateStr = String.valueOf(user.getLoginTime());
//        DateFormat cst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat gmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
//        Date dateTime = gmt.parse(dateStr);
//        String dateString = cst.format(dateTime);

        map.put("user",user);
        map.put("student",student);
        msg="查询个人信息成功!";
        log.info("userInfo.getLoginTime->"+user.getLoginTime());
        return new JsonResult<>(map,msg);
    }




    @RequestMapping("/studenrtResultSearch")
    JsonResult<Map> studenrtResultSearch(PageQo2 pageQo2){

        int pageSize=pageQo2.getPageSize();
        int pageIndex= pageQo2.getPageIndex();
        int applicantId = pageQo2.getApplicantId();
        String contestDate=pageQo2.getContestDate();
        String contestName=pageQo2.getContestName();
        String contestType= pageQo2.getContestType();
        String examineState="已通过";
        Page page = userService.getResults
                (pageSize,pageIndex,applicantId,contestName,contestType,contestDate);
        Map<String,Object> map = new HashMap<>();
        String code="";
        String msg ="服务器正常";
        if(page.getList()==null){
            code="0";
            msg="暂无记录";
            return new JsonResult<>(map,msg,code);
        }
        log.info("-----------------"+page.getList());
        page.getList();
        map.put("contestResults",page.getList());
        map.put("totalRecords",page.getTotalRecords());
        code="0";
        return new JsonResult<>(map,msg,code);

    }
    @RequestMapping("/login")
    public JsonResult<Map> login(@RequestBody Receive receive ,HttpSession session){
        String verifyCode = (String) session.getAttribute("verify_code");
        if(!verifyCode.equalsIgnoreCase(receive.getVerifyCode())){
            return new JsonResult<>("验证码错误,请重新填写","202");
        }
        Map<String, Object> map = new HashMap<>();
//        redisTemplate.opsForValue().set("name","lbj!!!!!!!");
//        log.info("redis->"+redisTemplate.opsForValue().get("name"));
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
        if(user.getState()==0){
            return new JsonResult<>("您的账号已被禁用，请联系管理员处理","202");
        }
        map.put("user",user);
        String token = TokenUtil.sign(user);
//        TokenUtil.verify(token);
        map.put("token",token);
        msg="登陆成功!";
        return new JsonResult<>(map,msg);
    }

    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        VerificationCode.output(image,resp.getOutputStream());
    }
    @RequestMapping("/sendEmail")
    public JsonResult<Map> sendCode(@RequestBody ReceiveEmail receiveEmail, HttpSession httpSession) {

        Map<String,Object> map = new HashMap<>();
        String email = receiveEmail.getEmail();
        String username = receiveEmail.getUsername();
        String msg="服务器正常";
        String code="";
        if(username==null||"".equals(username)){
            code="202";
            msg="用户名不能为空";
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

        String randomCode= emailService.email(receiveEmail.getEmail());
        httpSession.setAttribute("code", randomCode);
        map.put("randomCode",randomCode);
        return new JsonResult<>(map,"验证码已发送到指定邮箱","0");

    }
    @RequestMapping("/forgot/checkEmail")
    public JsonResult<Map> forget_checkEmail(@RequestBody ReceiveEmail receiveEmail,HttpSession session,HttpServletRequest request){
        String verifyCode = (String) session.getAttribute("verify_code");
        if(!verifyCode.equalsIgnoreCase(receiveEmail.getVerifyCode())){
            return new JsonResult<>("图片验证码错误,请重新填写","202");
        }
        String Code = (String) session.getAttribute("code");

        if (Code == null||Code.equals("")) {
            return new JsonResult<>("邮箱验证码未发送","202");
        }
        Map<String,Object> map = new HashMap<>();
        User u = new User();
        String username = receiveEmail.getUsername();
        u.setUsername(username);
        User user = userService.selectByUserName(u);
        map.put("username",username);

        if (receiveEmail.getCode().equals(Code)) {
            return new JsonResult<>(map,"验证成功，请修改您的密码","0");
        }
        String msg="验证失败，邮箱验证码错误";
        String code="202";
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

    @RequestMapping("/updateByUserName")
    public JsonResult<Map> updateByUserName(@RequestBody User user){
        Map<String,Object> map = new HashMap<>();
        String code="";
        String msg ="服务器正常";
        int update = userService.updateUser(user);
        if (update<=0){
            msg="更新个人信息失败";
            code="202";
            return new JsonResult<>(map,msg,code);
        }

        return new JsonResult<>(map);
    }


}
