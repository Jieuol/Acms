package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    List selectByPage(@Param("pageIndex")int pageIndex, @Param("pageSize")int pageSize,
                      @Param("noticeName") String noticeName,
                      @Param("updateTime")String updateTime,@Param("userGroup") String userGroup);

    List<Notice> getNoticeList(@Param("userGroup") String userGroup);
}
