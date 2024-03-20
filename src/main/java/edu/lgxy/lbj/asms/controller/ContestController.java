package edu.lgxy.lbj.asms.controller;

import edu.lgxy.lbj.asms.config.CheckToken;
import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestInformation;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Participant;
import edu.lgxy.lbj.asms.qo.PageQo;
import edu.lgxy.lbj.asms.qo.PageQo2;
import edu.lgxy.lbj.asms.service.ContestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
public class ContestController {

    @Resource
    private ContestService contestService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @Autowired
    private CheckToken checkToken;

//    @RequestMapping("/insertParticipant")
//    JsonResult<Map> insertParticipant(@RequestBody Participant participant){
//        Map<String,Object> map = new HashMap<>();
//        String msg="";
//        String code="";
////        String contestInformationId = participant.getContestInformationId();
////        String applicantId = participant.getApplicantId();
//        List<ContestParticipant> contestInformations = contestService.selectParticipantInfo(participant);
//        log.info("Mysql查询的contestInformations:"+contestInformations.size());
//        if (contestInformations.size()>0){
//            msg="您已报名成功，请勿重复报名";
//            code="202";
//            return new JsonResult<>(msg,code);
//        }
//        int result = contestService.insertContestParticipant(participant);
//        if(result<=0){
//            msg="报名失败";
//            code="202";
//            return new JsonResult<>(map,msg,code);
//        }
//        msg="报名成功";
//        code="0";
//        return new JsonResult<>(msg,code);
//    }

//    @RequestMapping("/getContestListByPage")
//    JsonResult<Map> contestList(int pageIndex,int pageSize ){
//        log.info("pageIndex："+pageIndex);
//        log.info("pageSize："+pageSize);
//        Page page = contestService.getContestListByPage(pageIndex,pageSize);
//        Map<String,Object> map = new HashMap<>();
//        List<ContestInformation>  contestInformation= contestService.getContestList();
//        String code="";
//        String msg ="服务器正常";
//        if(page.getList()==null){
//            code="0";
//            msg="暂无记录";
//            return new JsonResult<>(map,msg,code);
//        }
//        log.info("-----------------"+page.getList());
//        page.getList();
//        map.put("contestInfo",page.getList());
//        map.put("totalRecords",page.getTotalRecords());
//        code="0";
//        return new JsonResult<>(map,msg,code);
//    }
    //获取所有竞赛信息
    @RequestMapping("/getContestListByPage")
    JsonResult<Map> contestList(PageQo pageQo ){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        log.info("pageIndex："+pageQo.getPageIndex());
        log.info("pageSize："+pageQo.getPageSize());
        log.info("contestDate："+pageQo.getContestDate());
        log.info("contestName："+pageQo.getContestName());
        log.info("contestType："+pageQo.getContestType());
        int pageSize=pageQo.getPageSize();
        int pageIndex= pageQo.getPageIndex();
        String contestDate=pageQo.getContestDate();
        String contestName=pageQo.getContestName();
        String contestType= pageQo.getContestType();
        Page page = contestService.getContestListByPage(pageSize,pageIndex,contestName,contestType,contestDate);
        Map<String,Object> map = new HashMap<>();
        List<ContestInformation>  contestInformation= contestService.getContestList();
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
//    @RequestMapping("getParticipantListByPageAndUserId")
//    JsonResult<Map> getParticipantListByPageAndUserId(PageQo2 pageQo){
//
//        int pageSize=pageQo.getPageSize();
//        int pageIndex= pageQo.getPageIndex();
//        int applicantId = pageQo.getApplicantId();
//        log.info("applicantId:':"+applicantId);
//        String contestDate=pageQo.getContestDate();
//        String contestName=pageQo.getContestName();
//        String contestType= pageQo.getContestType();
//        PageAndUserId pageAndUserId = contestService.getParticipantListByPageAndUserId(pageSize,pageIndex,contestName,contestType,contestDate,applicantId);
//        Map<String,Object> map = new HashMap<>();
//        String code="";
//        String msg ="服务器正常";
//        if(pageAndUserId.getList()==null){
//            code="0";
//            msg="暂无记录";
//            return new JsonResult<>(map,msg,code);
//        }
//        log.info("-----------------"+pageAndUserId.getList());
//        pageAndUserId.getList();
//        map.put("contestInformation",pageAndUserId.getList());
//        map.put("totalRecords",pageAndUserId.getTotalRecords());
//        code="0";
//        return new JsonResult<>(map,msg,code);
//
//    }
}
