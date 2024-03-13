package edu.lgxy.lbj.asms.service;


import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Participant;
import edu.lgxy.lbj.asms.mapper.ParticipantMapper;
import edu.lgxy.lbj.asms.mapper.TeacherMapper;
import edu.lgxy.lbj.asms.qo.ParticipantQo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
@Slf4j
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private ParticipantMapper participantMapper;
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
        page.setList(teacherMapper.selectParticipantByPageAndUserId(pageIndex,pageSize,applicantId,contestName,contestType,contestDate,""));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(teacherMapper.selectByPageAndUserIdNoLimit(applicantId,contestName,contestType,contestDate,"").size());
        return page;
    }

    public Page gradesManagement(int pageSize, int pageIndex, int applicantId,
                                 String contestName, String contestType, String contestDate,String examineState) {
        Page page =new Page();
        page.setList(teacherMapper.selectParticipantByPageAndUserId(pageIndex,pageSize,applicantId,contestName,contestType,contestDate,examineState));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(teacherMapper.selectByPageAndUserIdNoLimit(applicantId,contestName,contestType,contestDate,examineState).size());
        return page;
    }
    public int updateParticipant(ContestParticipant contestParticipantc) {

        ContestParticipant result =  teacherMapper.selectByContestParticipantId(contestParticipantc.getContestParticipantId());
        log.info("$$$$$$$$$$$$$$result:"+result.getExamineState());
        log.info("$$$$$$$$$$$$$$contestParticipantc:"+contestParticipantc.getExamineState());
        if (result.getExamineState().equals("未审核")){
            if (contestParticipantc.getExamineState().equals("未通过")){
                participantMapper.incrParticipantsNumber(String.valueOf(contestParticipantc.getContestInformationId()));
                return teacherMapper.updateParticipant(contestParticipantc);
            }
            if (contestParticipantc.getExamineState().equals("已通过")){
                return teacherMapper.updateParticipant(contestParticipantc);
            }

        }else if (result.getExamineState().equals("已通过")){
            if (contestParticipantc.getExamineState().equals("未通过")){
                participantMapper.incrParticipantsNumber(String.valueOf(contestParticipantc.getContestInformationId()));
                return teacherMapper.updateParticipant(contestParticipantc);
            }
        }
        log.info("!!!!!!!!!!!"+contestParticipantc.getContestInformationId());
        participantMapper.updateParticipantsNumber(String.valueOf(contestParticipantc.getContestInformationId()));
        return teacherMapper.updateParticipant(contestParticipantc);
    }

    public int insertResults(ParticipantQo participantQo) {
        ContestParticipant contestParticipant = new ContestParticipant();
        contestParticipant.setContestName(participantQo.getContestName());
        contestParticipant.setContestType(participantQo.getContestType());
        contestParticipant.setContestDate(Date.valueOf(participantQo.getContestDate()));
        contestParticipant.setApplicantId(participantQo.getApplicantId());
        contestParticipant.setContestParticipantId(participantQo.getContestParticipantId());
        contestParticipant.setContestInformationId(participantQo.getContestInformationId());
        contestParticipant.setExamineState("已完赛");
        contestParticipant.setExamineReply("已经登录成绩");
        teacherMapper.updateParticipant(contestParticipant);
        return teacherMapper.insertResults(participantQo);

    }
}
