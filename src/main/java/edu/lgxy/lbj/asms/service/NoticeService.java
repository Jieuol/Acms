package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.entity.Notice;
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
        page.setTotalRecords(noticeMapper.selectByPageNoLimit(noticeName,updateTime,userGroup).size());
        return page;
    }

    public int withDrawNoticeByNoticeId(int noticeId) {
        return noticeMapper.withDrawByNoticeId(noticeId);
    }

    public int publishNoticeNoticeByNoticeId(int noticeId) {
        return noticeMapper.publishByNoticeId(noticeId);
    }

    public int addNotice(Notice notice) {
        return  noticeMapper.insertNotice(notice);
    }
}
