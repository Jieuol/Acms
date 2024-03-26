package edu.lgxy.lbj.asms.controller;


import edu.lgxy.lbj.asms.config.CheckToken;
import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.*;
import edu.lgxy.lbj.asms.qo.PageQo;
import edu.lgxy.lbj.asms.qo.PageQo2;
import edu.lgxy.lbj.asms.qo.ParticipantQo;
import edu.lgxy.lbj.asms.service.MessageService;
import edu.lgxy.lbj.asms.service.TeacherService;
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
public class TeacherController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @Autowired
    private CheckToken checkToken;
    @Resource
    private TeacherService teacherService;
    @Resource
    private MessageService messageService;
    //申报竞赛项目
    @RequestMapping("/applyContest")
    public JsonResult<Map> applyContest(@RequestBody ContestDeclaration contestDeclaration){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        int result = teacherService.insertContestDeclaration(contestDeclaration);
        if (result<=0){
            return new JsonResult<>("申报失败，服务器异常","202");
        }
        return new JsonResult<>("申报成功","0");
    }


    //获取申请的竞赛信息by current userId
    @RequestMapping("getDeclarationListByPageAndUserId")
    JsonResult<Map> getDeclarationListByPageAndUserId(PageQo2 pageQo){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
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
    //获取报名人信息
    @RequestMapping("/teacher/getParticipantListByPage")
    JsonResult<Map> getParticipantListByPage(PageQo2 pageQo2){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
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

   //获取 报名人成绩
    @RequestMapping("/teacher/gradesManagement")
    JsonResult<Map> gradesManagement(PageQo2 pageQo2){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        int pageSize=pageQo2.getPageSize();
        int pageIndex= pageQo2.getPageIndex();
        int applicantId = pageQo2.getApplicantId();
        String contestDate=pageQo2.getContestDate();
        String contestName=pageQo2.getContestName();
        String contestType= pageQo2.getContestType();
        String examineState="已完赛";
        Page page = teacherService.gradesManagement
                (pageSize,pageIndex,applicantId,contestName,contestType,contestDate,examineState);
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
        map.put("participant",page.getList());
        map.put("totalRecords",page.getTotalRecords());
        code="0";
        return new JsonResult<>(map,msg,code);

    }

    @RequestMapping("/deleteDeclaration")
    JsonResult<Map> deleteParticipant(@RequestBody ContestDeclaration contestDeclaration){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        Map<String,Object> map = new HashMap<>();
        String msg="";
        String code="";
        log.info("getContestDeclarationId():"+contestDeclaration.getContestDeclarationId());
//        String contestInformationId = participant.getContestInformationId();
//        String applicantId = participant.getApplicantId();s
        int result = teacherService.deleteContestDeclaration(contestDeclaration.getContestDeclarationId());
        if(result<=0){
            msg="取消申报失败";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        msg="取消申报成功";
        code="0";
        return new JsonResult<>(msg,code);
    }


    //登记成绩
    @RequestMapping("/insertResults")
    JsonResult<Map> insertResults(@RequestBody ParticipantQo participantQo){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        Map<String,Object> map = new HashMap<>();
        String msg="";
        String code="";
        ContestResults selectResult = teacherService.getResults(participantQo);
        if(selectResult!=null){
            int updateResult = teacherService.updateResults(participantQo);
            if(updateResult<=0){
                msg="修改成绩失败";
                code="202";
                return new JsonResult<>(map,msg,code);
            }
            Message message= new Message();
            message.setUserId(participantQo.getApplicantId());
            message.setMessageName("您参加的:"+participantQo.getContestName()+",成绩已出");
            message.setMessageInformation("成绩为:"+participantQo.getContestResult()+",详细信息请前往我的成绩进行查询");
            messageService.insertMessage(message);


            msg="修改成绩成功";
            code="0";
            return new JsonResult<>(msg,code);
        }
        int result = teacherService.insertResults(participantQo);
        if(result<=0){
            msg="登记成绩失败";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        Message message= new Message();
        message.setUserId(participantQo.getApplicantId());
        message.setMessageName("您参加的:"+participantQo.getContestName()+",成绩已出");
        message.setMessageInformation("成绩为:"+participantQo.getContestResult()+",详细信息请前往我的成绩进行查询");
        messageService.insertMessage(message);
        msg="登记成绩成功";
        code="0";
        return new JsonResult<>(msg,code);
    }

    //更改审核信息
    @RequestMapping("/updateParticipant")
    JsonResult<Map> updateParticipant(@RequestBody ContestParticipant contestParticipant){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        Map<String,Object> map = new HashMap<>();
        String msg="";
        String code="";
        int participantresult = teacherService.updateParticipant(contestParticipant);
        if(participantresult<=0){
            msg="审核失败";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        Message message= new Message();
        message.setUserId(contestParticipant.getApplicantId());
        message.setMessageName("您的:"+contestParticipant.getContestName()+",审核已完成");
        message.setMessageInformation("审核结果为:"+contestParticipant.getExamineState()+",详细信息请前往相关页面进行查询");
        messageService.insertMessage(message);
        msg="审核成功";
        code="0";
        return new JsonResult<>(msg,code);
    }
    @RequestMapping("/teacher/deleteParticipant")
    JsonResult<Map> deleteParticipant(@RequestBody ContestParticipant contestParticipant){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        int result = teacherService.deleteParticipant(contestParticipant.getContestParticipantId());
        if(result<=0){
            return new JsonResult<>("操作失败","202");
        }
        return new JsonResult<>("操作成功","0");
    }

    @RequestMapping("/teacher/approvalAll")
    JsonResult<Map> approvalAll(@RequestBody List<ContestParticipant> contestParticipants){
        Message message= new Message();
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        log.info("!!!!!!!!!!!!!!!"+contestParticipants);
        for(ContestParticipant data : contestParticipants){
            try{
                data.setExamineState("已通过");
                data.setExamineReply("申报正常,通过审核");
                teacherService.updateParticipant(data);//更新一条数据，mybatis中如下面的xml文件的update
                message.setUserId(data.getApplicantId());
                message.setMessageName("您的:"+data.getContestName()+",审核已完成");
                message.setMessageInformation("审核结果为:"+data.getExamineState()+",详细信息请前往相关页面进行查询");
                messageService.insertMessage(message);
            }
            catch(Exception e){
                log.info("异常:"+e);
                return new JsonResult<>("操作失败","202");
            }
        }

        return new JsonResult<>("操作成功","0");

    }
    @RequestMapping("/teacher/disApprovalAll")
    JsonResult<Map> disApprovalAll(@RequestBody List<ContestParticipant> contestParticipants){
        Message message= new Message();
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        log.info("!!!!!!!!!!!!!!!"+contestParticipants);
        for(ContestParticipant data : contestParticipants){
            try{
                data.setExamineState("未通过");
                data.setExamineReply("未通过审核");
                teacherService.updateParticipant(data);//更新一条数据，mybatis中如下面的xml文件的update
                message.setUserId(data.getApplicantId());
                message.setMessageName("您的:"+data.getContestName()+",审核已完成");
                message.setMessageInformation("审核结果为:"+data.getExamineState()+",详细信息请前往相关页面进行查询");
                messageService.insertMessage(message);
            }
            catch(Exception e){
                return new JsonResult<>("操作失败","202");
            }
        }

        return new JsonResult<>("操作成功","0");

    }

    @RequestMapping("/teacher/deleteAll")
    JsonResult<Map> deleteAll(@RequestBody List<ContestParticipant> contestParticipants){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        log.info("!!!!!!!!!!!!!!!"+contestParticipants);
        for(ContestParticipant data : contestParticipants){
            try{
                teacherService.deleteParticipant(data.getContestParticipantId());//更新一条数据，mybatis中如下面的xml文件的update
            }
            catch(Exception e){
                return new JsonResult<>("操作失败","202");
            }
        }

        return new JsonResult<>("操作成功","0");

    }

}
