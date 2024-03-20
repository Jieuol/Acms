package edu.lgxy.lbj.asms.controller;
import edu.lgxy.lbj.asms.config.CheckToken;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class MessageController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @Autowired
    private CheckToken checkToken;

    @Resource
    private MessageService messageService;
    @RequestMapping("/getMessage")
    public JsonResult<Map> getMessage(int userId){
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
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
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        JsonResult<Map> jsonResult= checkToken.checkTokenByUserName(username,token);
        if(jsonResult!=null){
            return jsonResult;
        }
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
