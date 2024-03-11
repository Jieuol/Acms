package edu.lgxy.lbj.asms.controller;

import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.entity.ContestInformation;
import edu.lgxy.lbj.asms.entity.Notice;
import edu.lgxy.lbj.asms.qo.NoticeQo;
import edu.lgxy.lbj.asms.qo.PageQo3;
import edu.lgxy.lbj.asms.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/addNotice")
    public JsonResult<Map> addNotice(@RequestBody NoticeQo notice){
        log.info("notice:"+notice);
        int result = noticeService.addNotice(notice);
        if(result<=0){
            return new JsonResult<>("添加失败","202");
        }
        String msg="添加成功";
        String code="0";
        return new JsonResult<>(msg,code);
    }
    @RequestMapping("/editNotice")
    public JsonResult<Map> editNotice(@RequestBody Notice notice){
        int result = noticeService.editForm(notice);
        if(result<=0){
            return new JsonResult<>("修改失败","202");
        }
        String msg="修改成功";
        String code="0";
        return new JsonResult<>(msg,code);
    }

    @RequestMapping("/withDrawNotice")
    public JsonResult<Map> withDrawNotice(@RequestParam int noticeId){
        int rest = noticeService.withDrawNoticeByNoticeId(noticeId);
        String code= "0";
        String msg="撤回成功";
        if (rest<=0){
            code="0";
            msg="撤回失败";
            return new JsonResult<>(msg,code);
        }

        return new JsonResult<>(msg,code);
    }


    @RequestMapping("/deleteNotice")
    public JsonResult<Map> deleteNotice(@RequestParam int noticeId){
        int rest = noticeService.deleteNotice(noticeId);
        String code= "0";
        String msg="删除成功";
        if (rest<=0){
            code="0";
            msg="删除失败";
            return new JsonResult<>(msg,code);
        }

        return new JsonResult<>(msg,code);
    }
    @RequestMapping("getNoticeByUserGroup")
    public JsonResult<Map> getNoticeByUserGroup(PageQo3 pageQo){
        log.info("pageIndex："+pageQo.getPageIndex());
        log.info("pageSize："+pageQo.getPageSize());
        int pageSize=pageQo.getPageSize();
        int pageIndex= pageQo.getPageIndex();
        String noticeName=pageQo.getNoticeName();
        String updateTime=pageQo.getUpdateTime();
        String userGroup= pageQo.getUserGroup();
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

    @RequestMapping("/publishNotice")
    public JsonResult<Map> publishNotice(@RequestParam int noticeId){
        int rest = noticeService.publishNoticeNoticeByNoticeId(noticeId);
        String code= "0";
        String msg="发布成功";
        if (rest<=0){
            code="0";
            msg="发布失败";
            return new JsonResult<>(msg,code);
        }

        return new JsonResult<>(msg,code);
    }
}
