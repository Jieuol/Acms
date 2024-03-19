package edu.lgxy.lbj.asms.service;


import edu.lgxy.lbj.asms.entity.Message;
import edu.lgxy.lbj.asms.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class MessageService {
    @Resource
    private MessageMapper messageMapper;
    public List<Message> getMessageByUserId(int userId) {
        return messageMapper.selectMessageByUserId(userId);
    }

    public int updateMessage(long messageId) {

        return messageMapper.updateMessage(messageId);
    }

    public Message getMessageByMessageId(long messageId) {

        return messageMapper.selectMessageByMsageId(messageId);
    }

    public int insertMessage(Message message){
        return messageMapper.insertMessage(message);
    }
}
