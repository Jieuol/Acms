package edu.lgxy.lbj.asms.controller;

import edu.lgxy.lbj.asms.config.CheckToken;
import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.*;
import edu.lgxy.lbj.asms.qo.PageQo;
import edu.lgxy.lbj.asms.qo.PageQo2;
import edu.lgxy.lbj.asms.qo.UserPage;
import edu.lgxy.lbj.asms.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class AdminController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @Autowired
    private CheckToken checkToken;
    @Resource
    private UserService userService;
    @Resource
    private ContestService contestService;
    @Resource
    private AdminService adminService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private MessageService messageService;
    //删除竞赛信息
    @RequestMapping("/deleteContestInfo")
    JsonResult<Map> deleteContestInfo(@RequestBody ContestInformation contestInformation){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        log.info("%%%%%%%%%%%%%%%"+contestInformation);
        int result = contestService.deleteContestInfo(contestInformation);
        if(result<=0){
            return new JsonResult<>("竞赛信息撤回失败，服务器异常","202");
        }
        return new JsonResult<>("竞赛信息已撤回","0");
    }
    //新增竞赛信息
    @RequestMapping("/insertContestInfo")
    JsonResult<Map> insertContestInfo(@RequestBody ContestInformation contestInformation){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        log.info("%%%%%%%%%%%%%%%"+contestInformation);
        int result = contestService.insertContestInfo(contestInformation);
        if(result<=0){
            return new JsonResult<>("竞赛信息发布失败，服务器异常","202");
        }
        return new JsonResult<>("竞赛信息已发布","0");
    }
    //获取 所有申报的项目
    @RequestMapping("/getDeclarationListByPage")
    JsonResult<Map> getDeclarationListByPage(PageQo2 pageQo){
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
    @RequestMapping("/admin/getParticipantListByPage")
    JsonResult<Map> getParticipantListByPage(PageQo pageQo){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        int pageSize=pageQo.getPageSize();
        int pageIndex= pageQo.getPageIndex();
        String contestDate=pageQo.getContestDate();
        String contestName=pageQo.getContestName();
        String contestType= pageQo.getContestType();
        String examineState="未审核";
        Page page = adminService.getParticipantListByPage
                (pageSize,pageIndex,contestName,contestType,contestDate,examineState);
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
    //获取报名人成绩信息
    @RequestMapping("/admin/gradesManagement")
    JsonResult<Map> gradesManagement(PageQo pageQo){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        int pageSize=pageQo.getPageSize();
        int pageIndex= pageQo.getPageIndex();
        String contestDate=pageQo.getContestDate();
        String contestName=pageQo.getContestName();
        String contestType= pageQo.getContestType();
        String examineState="已完赛";
        Page page = adminService.getParticipantListByPage
                (pageSize,pageIndex,contestName,contestType,contestDate,examineState);
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
    //更新已发布的竞赛信息
    @RequestMapping("/updateDeclaration")
    JsonResult<Map> updateDeclaration(@RequestBody ContestDeclaration contestDeclaration){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        Map<String,Object> map = new HashMap<>();
        String msg="";
        String code="";
        Message message= new Message();
        message.setUserId(contestDeclaration.getApplicantId());
        message.setMessageName("您的:"+contestDeclaration.getContestName()+",审核已完成");
        message.setMessageInformation("审核结果为:"+contestDeclaration.getExamineState()+",详细信息请前往相关页面进行查询");
        log.info("getContestDeclarationId():"+contestDeclaration.getContestDeclarationId());
        int result = adminService.updateDeclaration(contestDeclaration);
        if(result<=0){
            msg="审核失败";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        msg="审核成功";
        code="0";
        messageService.insertMessage(message);
        return new JsonResult<>(msg,code);
    }

    @RequestMapping("/getUserInformation")
    JsonResult<Map> getUserInformation(UserPage userPage){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        int pageSize=userPage.getPageSize();
        int pageIndex= userPage.getPageIndex();
        String userGroup= userPage.getUserGroup();

        Page page = userService.getUserInformation
                (pageSize,pageIndex,userGroup);
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
        map.put("userInformation",page.getList());
        map.put("totalRecords",page.getTotalRecords());
        code="0";
        return new JsonResult<>(map,msg,code);
    }

    @RequestMapping("/updateUserInformation")
    JsonResult<Map> updateUserInformation(@RequestBody User user){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
        log.info("!!!!!!!!!!!!!!!"+user);
        int result = userService.updateUserInformation(user);
        if(result<=0){
            return new JsonResult<>("操作失败","202");
        }
        String msg ="操作正常";
        return new JsonResult<>("操作成功","0");
    }

}
