package edu.lgxy.lbj.asms.controller;


import edu.lgxy.lbj.asms.config.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class BaseController {
    @RequestMapping("/api")
    public JsonResult<Map> base(){

        return new JsonResult<>();
    }
}
