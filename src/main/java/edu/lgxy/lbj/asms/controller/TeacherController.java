package edu.lgxy.lbj.asms.controller;


import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
public class TeacherController {
    @Resource
    private TeacherService teacherService;
    @RequestMapping("/applyContest")
    public JsonResult<Map> applyContest(@RequestBody ContestDeclaration contestDeclaration){
        int result = teacherService.insertContestDeclaration(contestDeclaration);
        if (result<=0){
            return new JsonResult<>("申请失败，服务器异常","202");
        }

        return new JsonResult<>("申报成功","0");
    }
}
