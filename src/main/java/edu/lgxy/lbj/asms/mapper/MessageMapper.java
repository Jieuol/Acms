package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.Message;

import java.util.List;

public interface MessageMapper {
    List<Message> selectMessageByUserId(int userId);

    Message selectMessageByMsageId(long messageId);
    int updateMessage(long messageId);

    int insertMessage(Message message);

    int deleteMessageById(long messageId);
}
