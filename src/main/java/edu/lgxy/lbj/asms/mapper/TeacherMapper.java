package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;

import java.util.List;

public interface TeacherMapper {

    int insertContestDeclaration(ContestDeclaration contestDeclaration);

    List<ContestDeclaration> selectByPageAndUserId(int pageSize, int pageIndex, String contestName, String contestType, String contestDate, int applicantId);

    List<ContestDeclaration> selectByPageAndUserIdNoList(String contestName, String contestType, String contestDate, int applicantId);

    int deleteContestParticipant(long contestDeclarationId);

    int updateDeclaration(ContestDeclaration contestDeclarationId);
}
