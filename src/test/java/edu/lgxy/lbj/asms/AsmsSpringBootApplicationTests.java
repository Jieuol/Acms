package edu.lgxy.lbj.asms;

import com.sun.deploy.util.BlackList;
import edu.lgxy.lbj.asms.config.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AsmsSpringBootApplicationTests {

    @Test
    void contextLoads() {
    }
    @Resource
    private RedisUtil redisUtil;
    @Test
    public void saveValue() {
        //存入Redis

        redisUtil.set("username", "IT小辉同学");
        System.out.println("保存成功！！！");
        //根据key取出
        String username = (String) redisUtil.get("username");
        System.out.println("username="+username);
    }


}
