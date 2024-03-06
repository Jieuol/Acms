package edu.lgxy.lbj.asms.controller;

import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.entity.ContestInformation;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.qo.PageQo;
import edu.lgxy.lbj.asms.service.ContestService;
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
public class ContestController {

    @Resource
    private ContestService contestService;

    @RequestMapping("/getContestListByPage")
    JsonResult<Map> contestList(int pageIndex,int pageSize ){
        log.info("pageIndex："+pageIndex);
        log.info("pageSize："+pageSize);
        Page page = contestService.getContestListByPage(pageIndex,pageSize);
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
}
