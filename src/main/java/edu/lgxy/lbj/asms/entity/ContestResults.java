package edu.lgxy.lbj.asms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

@Slf4j
@Data
public class ContestResults {

  private long contestResultsId;
  private long contestInformationId;
  private long contestParticipantId;
  private String contestName;
  private String contestType;
  private java.sql.Date contestDate;
  private String remarks;
  private String contestResult;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String updateTime;



}
