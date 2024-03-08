package edu.lgxy.lbj.asms.controller;

import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.entity.ContestInformation;
import edu.lgxy.lbj.asms.entity.Notice;
import edu.lgxy.lbj.asms.qo.PageQo3;
import edu.lgxy.lbj.asms.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class NoticeController {
    @Resource
    private NoticeService noticeService;
    @RequestMapping("getNoticeByUserGroup")
    public JsonResult<Map> getNoticeByUserGroup(PageQo3 pageQo){
        log.info("pageIndex："+pageQo.getPageIndex());
        log.info("pageSize："+pageQo.getPageSize());
        int pageSize=pageQo.getPageSize();
        int pageIndex= pageQo.getPageIndex();
        String noticeName=pageQo.getNoticeName();
        String updateTime=pageQo.getUpdateTime();
        String userGroup= pageQo.getUserGroup();
//        if (userGroup=="管理员"||userGroup.equals("管理员")){
//            userGroup="";
//        }
        Page page = noticeService.getNoticeListByUserGroup(pageSize,pageIndex,noticeName,updateTime,userGroup);
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
        map.put("noticeInfo",page.getList());
        map.put("totalRecords",page.getTotalRecords());
        code="0";
        return new JsonResult<>(map,msg,code);
    }
}
