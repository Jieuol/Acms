package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminMapper {

    int updateDeclaration(ContestDeclaration contestDeclarationId);

    List<ParticipantInfomation> selectByPage(@Param("pageIndex") int pageIndex,
                                             @Param("pageSize") int pageSize ,
                                             @Param("contestName")String contestName,
                                             @Param("contestType")String contestType,
                                             @Param("contestDate") String contestDate,
                                             @Param("examineState") String  examineState
                                            );

    List<ParticipantInfomation> selectByPageNoLimit( @Param("contestName")String contestName,
                                           @Param("contestType")String contestType,
                                           @Param("contestDate") String contestDate,
                                           @Param("examineState") String  examineState  );

    

    ContestParticipant selectByContestParticipantId(long contestParticipantId);
    int banByList(List<User> user);
}
