package edu.lgxy.lbj.asms.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
@Data
@Component
public class CheckToken {
    private String username;
    private String token;

    @Resource
    private RedisUtil redisUtil;
    public JsonResult<Map> checkTokenByUserName(String username, String token){
        if(!redisUtil.get(username).equals(token)){
            return new JsonResult<>("您的账号在另一设备登录，请重新登录","401");
        }
            return null;
    }
}
