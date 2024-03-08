package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestInformation;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Participant;
import edu.lgxy.lbj.asms.mapper.ContestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class ContestService {
    @Resource
    private ContestMapper contestMapper;
    public List<ContestInformation> getContestList() {
        return contestMapper.getContestList();
    }

    public Page getContestListByPage(int pageSize , int pageIndex, String contestName, String contestType, String contestDate) {
        Page page =new Page();
        page.setList(contestMapper.selectByPage(pageIndex,pageSize,contestName,contestType,contestDate));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        log.info("Mysql.pageIndex-->"+pageIndex);
        log.info("Mysql.pageSize-->"+pageSize);
        page.setTotalRecords(contestMapper.getContestList().size());
        return page;
    }
//
//    public List<ContestParticipant> selectParticipantInfo(Participant participant) {
//        return contestMapper.selectParticipantInfo(participant);
//    }
//
//    public int insertContestParticipant(Participant participant) {
//        return contestMapper.insertContestParticipant(participant);
//    }
//
//    public PageAndUserId getParticipantListByPageAndUserId(int pageIndex, int pageSize, String contestName, String contestType, String contestDate, int applicantId) {
//        PageAndUserId pageAndUserId =new PageAndUserId();
//        pageAndUserId.setList(contestMapper.selectByPageAndUserId(pageSize,pageIndex,contestName,contestType,contestDate,applicantId));
//        pageAndUserId.setPageIndex(pageIndex);
//        pageAndUserId.setPageSize(pageSize);
//        pageAndUserId.setTotalRecords(contestMapper.getParticipantByUserId(applicantId).size());
//        return pageAndUserId;
//    }
}
