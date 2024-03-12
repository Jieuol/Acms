package edu.lgxy.lbj.asms.service;


import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Participant;
import edu.lgxy.lbj.asms.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    public int insertContestDeclaration(ContestDeclaration contestDeclaration) {
    return teacherMapper.insertContestDeclaration(contestDeclaration);
    }

    public PageAndUserId getDeclarationListByPageAndUserId(int pageSize, int pageIndex, String contestName, String contestType, String contestDate, int applicantId) {
        PageAndUserId pageAndUserId =new PageAndUserId();
        pageAndUserId.setList(teacherMapper.selectByPageAndUserId(pageSize,pageIndex,contestName,contestType,contestDate,applicantId));
        pageAndUserId.setPageIndex(pageIndex);
        pageAndUserId.setPageSize(pageSize);
        pageAndUserId.setTotalRecords(teacherMapper.selectByPageAndUserIdNoList(contestName,contestType,contestDate,applicantId).size());
        return pageAndUserId;
    }

    public int deleteContestDeclaration(long contestDeclarationId) {
        return teacherMapper.deleteContestParticipant(contestDeclarationId);
    }

    public Page getParticipantListByPage(int pageSize, int pageIndex,int applicantId,String contestName, String contestType, String contestDate) {
        Page page =new Page();
        page.setList(teacherMapper.selectParticipantByPageAndUserId(pageIndex,pageSize,applicantId,contestName,contestType,contestDate));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(teacherMapper.selectByPageAndUserIdNoLimit(applicantId,contestName,contestType,contestDate).size());
        return page;
    }

}
