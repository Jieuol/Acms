package edu.lgxy.lbj.asms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ParticipantInfomation {

    private long contestParticipantId;
    private long contestInformationId;
    private long applicantId;
    private String applicantRealname;
    private String contestName;
    private String contestType;
    private java.sql.Date contestDate;
    private String remarks;
    private String examineState;
    private String examineReply;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String  updateTime;
    private String major;
    private String grade;
    private String academy;

}
