package edu.lgxy.lbj.asms;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author LouBinJie
 * @date 2024/3/4 14:00
 */
@SpringBootApplication
@MapperScan("edu.lgxy.lbj.asms.mapper")
public class AsmsSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsmsSpringBootApplication.class);
    }

}
