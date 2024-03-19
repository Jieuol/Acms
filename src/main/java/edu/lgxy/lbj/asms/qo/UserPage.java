package edu.lgxy.lbj.asms.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
@Data
public class UserPage implements Serializable {
    private int pageIndex;
    private int pageSize;
    private String userGroup;


}
