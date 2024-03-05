package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.mapper.UserMapper;
import edu.lgxy.lbj.asms.entity.User;
import edu.lgxy.lbj.asms.qo.ReceiveUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User selectByUserName(User u){

        return  userMapper.selectByUserName(u);
    }

    public int updatePassword(User user1) {
        return  userMapper.updatePassword(user1);
    }

    public int updateUser(ReceiveUser user) {
        return userMapper.updateUser(user);
    }
}
