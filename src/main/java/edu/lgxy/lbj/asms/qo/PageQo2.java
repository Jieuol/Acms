package edu.lgxy.lbj.asms.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
public class PageQo2 implements Serializable {
    private int pageIndex;
    private int pageSize;
    private int applicantId;
    private String contestName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String contestDate;
    private String contestType;


}
