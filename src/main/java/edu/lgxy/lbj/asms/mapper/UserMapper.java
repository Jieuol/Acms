package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.User;

public interface UserMapper {

    public User selectByUserName(User u);

    int updatePassword(User user1);
}
