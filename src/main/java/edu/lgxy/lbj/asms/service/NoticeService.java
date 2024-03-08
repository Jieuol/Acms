package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    public Page getNoticeListByUserGroup(int pageSize, int pageIndex, String noticeName, String updateTime, String userGroup) {
        Page page =new Page();
        page.setList(noticeMapper.selectByPage(pageIndex,pageSize,noticeName,updateTime,userGroup));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        log.info("Mysql.pageIndex-->"+pageIndex);
        log.info("Mysql.pageSize-->"+pageSize);
        page.setTotalRecords(noticeMapper.getNoticeList(userGroup).size());
        return page;
    }
}
