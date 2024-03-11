package edu.lgxy.lbj.asms.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class NoticeQo {


    private String noticeName;
    private String noticeInformation;
    private String userGroup;



}
