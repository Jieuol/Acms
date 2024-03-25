package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.ContestResults;
import edu.lgxy.lbj.asms.entity.ParticipantInfomation;
import edu.lgxy.lbj.asms.qo.ParticipantQo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {

    int insertContestDeclaration(ContestDeclaration contestDeclaration);

    List<ContestDeclaration> selectByPageAndUserId(int pageSize, int pageIndex, String contestName, String contestType, String contestDate, int applicantId);

    List<ContestDeclaration> selectByPageAndUserIdNoList(String contestName, String contestType, String contestDate, int applicantId);

    int deleteContestParticipant(long contestDeclarationId);

    int updateDeclaration(ContestDeclaration contestDeclarationId);
    int updateParticipant(ContestParticipant contestParticipant);
    ContestParticipant selectByContestParticipantId(long contestParticipantId);
    List<ParticipantInfomation> selectParticipantByPageAndUserId(@Param("pageIndex") int pageIndex,
                                             @Param("pageSize") int pageSize ,
                                             @Param("applicantId") int applicantId ,
                                             @Param("contestName")String contestName,
                                             @Param("contestType")String contestType,
                                             @Param("contestDate") String contestDate,
                                             @Param("examineState") String examineState);

    List<ParticipantInfomation> selectByPageAndUserIdNoLimit( @Param("applicantId") int applicantId ,
                                                     @Param("contestName")String contestName,
                                                     @Param("contestType")String contestType,
                                                     @Param("contestDate") String contestDate,
                                                     @Param("examineState") String examineState);

    int insertResults(ParticipantQo participantQo);

    ContestResults selectResultsByUserIdAndParticipantId(ParticipantQo participantQo);

    int updateResults(ParticipantQo participantQo);

    int deleteParticipant(long contestParticipantId);
}
