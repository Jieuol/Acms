package edu.lgxy.lbj.asms.controller;

import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Participant;
import edu.lgxy.lbj.asms.qo.PageQo2;
import edu.lgxy.lbj.asms.service.ParticipantService;
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
public class ParticipantController {
    @Resource
    private ParticipantService participantService;

    @RequestMapping("getParticipantListByPageAndUserId")
    JsonResult<Map> getParticipantListByPageAndUserId(PageQo2 pageQo){

        int pageSize=pageQo.getPageSize();
        int pageIndex= pageQo.getPageIndex();
        int applicantId = pageQo.getApplicantId();
        log.info("applicantId:':"+applicantId);
        String contestDate=pageQo.getContestDate();
        String contestName=pageQo.getContestName();
        String contestType= pageQo.getContestType();
        PageAndUserId pageAndUserId = participantService.getParticipantListByPageAndUserId
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
        map.put("contestInformation",pageAndUserId.getList());
        map.put("totalRecords",pageAndUserId.getTotalRecords());
        code="0";
        return new JsonResult<>(map,msg,code);

    }
    @RequestMapping("/insertParticipant")
    JsonResult<Map> insertParticipant(@RequestBody Participant participant){
        Map<String,Object> map = new HashMap<>();
        String msg="";
        String code="";
//        String contestInformationId = participant.getContestInformationId();
//        String applicantId = participant.getApplicantId();
        List<ContestParticipant> contestInformations = participantService.selectParticipantInfo(participant);
        log.info("Mysql查询的contestInformations:"+contestInformations.size());
        if (contestInformations.size()>0){
            msg="您已报名成功，请勿重复报名";
            code="202";
            return new JsonResult<>(msg,code);
        }
        int result = participantService.insertContestParticipant(participant);
        int update = participantService.updateParticipantsNumbers(participant.getContestInformationId());
        if(result<=0){
            msg="报名失败";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        msg="报名成功";
        code="0";
        return new JsonResult<>(msg,code);
    }
    @RequestMapping("/deleteParticipant")
    JsonResult<Map> deleteParticipant(@RequestBody Participant participant){
        Map<String,Object> map = new HashMap<>();
        String msg="";
        String code="";
//        String contestInformationId = participant.getContestInformationId();
//        String applicantId = participant.getApplicantId();
        List<ContestParticipant> contestInformations = participantService.selectParticipantInfo(participant);
        log.info("Mysql查询的contestInformations:"+contestInformations.size());
        int result = participantService.deleteContestParticipant(participant.getContestInformationId());
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
