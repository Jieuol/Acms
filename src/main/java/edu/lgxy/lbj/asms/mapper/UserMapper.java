package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.User;
import edu.lgxy.lbj.asms.qo.ReceiveUser;

public interface UserMapper {

    public User selectByUserName(User u);

    int updatePassword(User user1);

    int updateUser(ReceiveUser user);
}
