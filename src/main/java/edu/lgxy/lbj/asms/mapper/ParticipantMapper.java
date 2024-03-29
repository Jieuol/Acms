package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Participant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParticipantMapper {
    List<ContestParticipant> selectParticipantInfo(Participant participant);

    int insertContestParticipant(Participant participant);

    List<Participant> getParticipantByUserId(@Param("applicantId")int applicantId);

    List<Participant> selectByPageAndUserIdNoList(
                                            @Param("contestName")String contestName,
                                            @Param("contestType")String contestType,
                                            @Param("contestDate") String contestDate,
                                            @Param("applicantId") int applicantId);
    List<Participant> selectByPageAndUserId(@Param("pageIndex") int pageIndex,
                                            @Param("pageSize") int pageSize ,
                                            @Param("contestName")String contestName,
                                            @Param("contestType")String contestType,
                                            @Param("contestDate") String contestDate,
                                            @Param("applicantId") int applicantId);

    int updateParticipantsNumber(@Param("contestInformationId") String contestInformationId);
    int incrParticipantsNumber(@Param("contestInformationId") String contestInformationId);

    int deleteContestParticipant(@Param("contestInformationId") String contestInformationId);
}
