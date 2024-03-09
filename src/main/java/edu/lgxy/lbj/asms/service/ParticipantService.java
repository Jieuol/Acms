package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Participant;
import edu.lgxy.lbj.asms.mapper.ParticipantMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ParticipantService {
    @Resource
    private ParticipantMapper participantMapper;

    public List<ContestParticipant> selectParticipantInfo(Participant participant) {
        return participantMapper.selectParticipantInfo(participant);
    }

    public int insertContestParticipant(Participant participant) {
        return participantMapper.insertContestParticipant(participant);
    }

    public PageAndUserId getParticipantListByPageAndUserId(int pageIndex, int pageSize, String contestName, String contestType, String contestDate, int applicantId) {
        PageAndUserId pageAndUserId =new PageAndUserId();
        pageAndUserId.setList(participantMapper.selectByPageAndUserId(pageSize,pageIndex,contestName,contestType,contestDate,applicantId));
        pageAndUserId.setPageIndex(pageIndex);
        pageAndUserId.setPageSize(pageSize);
        pageAndUserId.setTotalRecords(participantMapper.selectByPageAndUserIdNoList(contestName,contestType,contestDate,applicantId).size());
        return pageAndUserId;
    }
}
