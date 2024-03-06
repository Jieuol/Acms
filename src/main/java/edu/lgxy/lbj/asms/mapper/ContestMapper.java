package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.ContestInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContestMapper {
    public List<ContestInformation> getContestList();

    List<ContestInformation> selectByPage(@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSeize);
}
