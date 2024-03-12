package edu.lgxy.lbj.asms.controller;


import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Participant;
import edu.lgxy.lbj.asms.qo.PageQo;
import edu.lgxy.lbj.asms.qo.PageQo2;
import edu.lgxy.lbj.asms.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
            return new JsonResult<>("申报失败，服务器异常","202");
        }
        return new JsonResult<>("申报成功","0");
    }

    @RequestMapping("getDeclarationListByPageAndUserId")
    JsonResult<Map> getDeclarationListByPageAndUserId(PageQo2 pageQo){

        int pageSize=pageQo.getPageSize();
        int pageIndex= pageQo.getPageIndex();
        int applicantId = pageQo.getApplicantId();
        log.info("applicantId:':"+applicantId);
        String contestDate=pageQo.getContestDate();
        String contestName=pageQo.getContestName();
        String contestType= pageQo.getContestType();
        PageAndUserId pageAndUserId = teacherService.getDeclarationListByPageAndUserId
                (pageSize,pageIndex,contestName,contestType,contestDate,applicantId);
        Map<String,Object> map = new HashMap<>();
        String code="";
        String msg ="服务器正常";
        if(pageAndUserId.getList()==null){
            code="0";
            msg="暂无记录";
            return new JsonResult<>(map,msg,code);
        }
        log.info("-----------------"+pageAndUserId.getList());
        pageAndUserId.getList();
        map.put("contestDeclaration",pageAndUserId.getList());
        map.put("totalRecords",pageAndUserId.getTotalRecords());
        code="0";
        return new JsonResult<>(map,msg,code);

    }

    @RequestMapping("/teacher/getParticipantListByPage")
    JsonResult<Map> getParticipantListByPage(PageQo2 pageQo2){

        int pageSize=pageQo2.getPageSize();
        int pageIndex= pageQo2.getPageIndex();
        int applicantId = pageQo2.getApplicantId();
        String contestDate=pageQo2.getContestDate();
        String contestName=pageQo2.getContestName();
        String contestType= pageQo2.getContestType();
        Page page = teacherService.getParticipantListByPage
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
        map.put("contestInfo",page.getList());
        map.put("totalRecords",page.getTotalRecords());
        code="0";
        return new JsonResult<>(map,msg,code);

    }
    @RequestMapping("getDeclarationListByPage")
    JsonResult<Map> getDeclarationListByPage(PageQo2 pageQo){

        int pageSize=pageQo.getPageSize();
        int pageIndex= pageQo.getPageIndex();
        int applicantId = pageQo.getApplicantId();
        log.info("applicantId:':"+applicantId);
        String contestDate=pageQo.getContestDate();
        String contestName=pageQo.getContestName();
        String contestType= pageQo.getContestType();
        PageAndUserId pageAndUserId = teacherService.getDeclarationListByPageAndUserId
                (pageSize,pageIndex,contestName,contestType,contestDate,applicantId);
        Map<String,Object> map = new HashMap<>();
        String code="";
        String msg ="服务器正常";
        if(pageAndUserId.getList()==null){
            code="0";
            msg="暂无记录";
            return new JsonResult<>(map,msg,code);
        }
        log.info("-----------------"+pageAndUserId.getList());
        pageAndUserId.getList();
        map.put("contestDeclaration",pageAndUserId.getList());
        map.put("totalRecords",pageAndUserId.getTotalRecords());
        code="0";
        return new JsonResult<>(map,msg,code);

    }
    @RequestMapping("/deleteDeclaration")
    JsonResult<Map> deleteParticipant(@RequestBody ContestDeclaration contestDeclaration){
        Map<String,Object> map = new HashMap<>();
        String msg="";
        String code="";
        log.info("getContestDeclarationId():"+contestDeclaration.getContestDeclarationId());
//        String contestInformationId = participant.getContestInformationId();
//        String applicantId = participant.getApplicantId();s
        int result = teacherService.deleteContestDeclaration(contestDeclaration.getContestDeclarationId());
        if(result<=0){
            msg="取消报名失败";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        msg="取消报名成功";
        code="0";
        return new JsonResult<>(msg,code);
    }


}
