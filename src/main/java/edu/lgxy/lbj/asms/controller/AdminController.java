package edu.lgxy.lbj.asms.controller;

import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestInformation;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.qo.PageQo;
import edu.lgxy.lbj.asms.qo.PageQo2;
import edu.lgxy.lbj.asms.service.AdminService;
import edu.lgxy.lbj.asms.service.ContestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class AdminController {
    @Resource
    private ContestService contestService;
    @Resource
    private AdminService adminService;

    //删除竞赛信息
    @RequestMapping("/deleteContestInfo")
    JsonResult<Map> deleteContestInfo(@RequestBody ContestInformation contestInformation){
        log.info("%%%%%%%%%%%%%%%"+contestInformation);
        int result = contestService.deleteContestInfo(contestInformation);
        if(result<=0){
            return new JsonResult<>("竞赛信息撤回失败，服务器异常","202");
        }
        return new JsonResult<>("竞赛信息已撤回","0");
    }

    @RequestMapping("/insertContestInfo")
    JsonResult<Map> insertContestInfo(@RequestBody ContestInformation contestInformation){
        log.info("%%%%%%%%%%%%%%%"+contestInformation);
        int result = contestService.insertContestInfo(contestInformation);
        if(result<=0){
            return new JsonResult<>("竞赛信息发布失败，服务器异常","202");
        }
        return new JsonResult<>("竞赛信息已发布","0");
    }



    @RequestMapping("/admin/getParticipantListByPage")
    JsonResult<Map> getParticipantListByPage(PageQo pageQo){
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

    @RequestMapping("/admin/gradesManagement")
    JsonResult<Map> gradesManagement(PageQo pageQo){
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

    @RequestMapping("/updateDeclaration")
    JsonResult<Map> updateDeclaration(@RequestBody ContestDeclaration contestDeclaration){
        Map<String,Object> map = new HashMap<>();
        String msg="";
        String code="";
        log.info("getContestDeclarationId():"+contestDeclaration.getContestDeclarationId());
        int result = adminService.updateDeclaration(contestDeclaration);
        if(result<=0){
            msg="审核失败";
            code="202";
            return new JsonResult<>(map,msg,code);
        }
        msg="审核成功";
        code="0";
        return new JsonResult<>(msg,code);
    }


}
