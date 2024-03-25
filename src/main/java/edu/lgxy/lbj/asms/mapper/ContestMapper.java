package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.ContestInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContestMapper {
    public List<ContestInformation> getContestList();

    List<ContestInformation> selectByPage(@Param("pageIndex") int pageIndex,
                                          @Param("pageSize") int pageSize,
                                          @Param("contestName")String contestName,
                                          @Param("contestType")String contestType,
                                          @Param("contestDate") String contestDate);

    List<ContestInformation> selectByPageNoList(
                       @Param("contestName")String contestName,
                       @Param("contestType")String contestType,
                       @Param("contestDate") String contestDate);

    int insertContestInfo(ContestInformation contestInformation);

    int deleteContestInfo(ContestInformation contestInformation);

    ContestInformation selectContestById(long contestDeclarationId);
}
