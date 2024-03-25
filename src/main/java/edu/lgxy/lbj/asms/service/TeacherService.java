package edu.lgxy.lbj.asms.service;


import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.ContestResults;
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
        page.setList(teacherMapper.selectParticipantByPageAndUserId(pageIndex,pageSize,applicantId,contestName,contestType,contestDate,"未审核"));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(teacherMapper.selectByPageAndUserIdNoLimit(applicantId,contestName,contestType,contestDate,"未审核").size());
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
    public int updateParticipant(ContestParticipant contestParticipant) {

        ContestParticipant result =  teacherMapper.selectByContestParticipantId(contestParticipant.getContestParticipantId());
        log.info("$$$$$$$$$$$$$$result:"+result.getExamineState());
        log.info("$$$$$$$$$$$$$$contestParticipant:"+contestParticipant.getExamineState());
        if (result.getExamineState().equals("未审核")){
            if (contestParticipant.getExamineState().equals("未通过")){
                participantMapper.incrParticipantsNumber(String.valueOf(contestParticipant.getContestInformationId()));
                return teacherMapper.updateParticipant(contestParticipant);
            }
            if (contestParticipant.getExamineState().equals("已通过")){
                return teacherMapper.updateParticipant(contestParticipant);
            }

        }else if (result.getExamineState().equals("已通过")){
            if (contestParticipant.getExamineState().equals("未通过")){
                participantMapper.incrParticipantsNumber(String.valueOf(contestParticipant.getContestInformationId()));
                return teacherMapper.updateParticipant(contestParticipant);
            }
            if (contestParticipant.getExamineState().equals("已通过")){
                return teacherMapper.updateParticipant(contestParticipant);
            }
        }else if (result.getExamineState().equals("未通过")){
            if (contestParticipant.getExamineState().equals("未通过")){
                return teacherMapper.updateParticipant(contestParticipant);
            }
        }
        log.info("!!!!!!!!!!!"+contestParticipant.getContestInformationId());
        participantMapper.updateParticipantsNumber(String.valueOf(contestParticipant.getContestInformationId()));
        return teacherMapper.updateParticipant(contestParticipant);
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

    public ContestResults getResults(ParticipantQo participantQo) {
        return teacherMapper.selectResultsByUserIdAndParticipantId(participantQo);
    }

    public int updateResults(ParticipantQo participantQo) {
        return teacherMapper.updateResults(participantQo);
    }

    public int deleteParticipant(long contestParticipantId) {
        return teacherMapper.deleteParticipant(contestParticipantId);
    }
}
