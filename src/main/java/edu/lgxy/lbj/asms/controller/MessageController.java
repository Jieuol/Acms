package edu.lgxy.lbj.asms.controller;
import edu.lgxy.lbj.asms.config.JsonResult;
import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.entity.ContestInformation;
import edu.lgxy.lbj.asms.entity.Message;
import edu.lgxy.lbj.asms.entity.Notice;
import edu.lgxy.lbj.asms.qo.NoticeQo;
import edu.lgxy.lbj.asms.qo.PageQo3;
import edu.lgxy.lbj.asms.service.MessageService;
import edu.lgxy.lbj.asms.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class MessageController {

    @Resource
    private MessageService messageService;
    @RequestMapping("/getMessage")
    public JsonResult<Map> getMessage(int userId){
        Map<String,Object> map = new HashMap<>();
        List<Message> messagesData = messageService.getMessageByUserId(userId);
        if(messagesData==null){
            return new JsonResult<>(map,"查询失败","202");
        }
        if(messagesData.size()<=0){
            return new JsonResult<>(map,"暂无消息","0");
        }
        map.put("messageData",messagesData);
        return new JsonResult<>(map,"查询成功","0");
    }
    @RequestMapping("/updateMessage")
    public JsonResult<Map> updateMessage(@RequestParam long messageId){
        Map<String,Object> map = new HashMap<>();
        int result = messageService.updateMessage(messageId);
        if(result<=0){
            return new JsonResult<>(map,"更新失败","202");
        }
        Message messagesData = messageService.getMessageByMessageId(messageId);
        map.put("messageData",messagesData);
        return new JsonResult<>(map,"更新成功","0");
    }


}
