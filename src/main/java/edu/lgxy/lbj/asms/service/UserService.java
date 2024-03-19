package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.entity.Student;
import edu.lgxy.lbj.asms.mapper.UserMapper;
import edu.lgxy.lbj.asms.entity.User;
import edu.lgxy.lbj.asms.qo.ReceiveUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public int updateUser(User user) {
        return userMapper.updateUserInformation(user);
    }

    public Page getResults(int pageSize, int pageIndex, int applicantId, String contestName, String contestType, String contestDate) {
        Page page =new Page();
        page.setList(userMapper.selectContestResultsByPageAndUserId(pageIndex,pageSize,applicantId,contestName,contestType,contestDate));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(userMapper.selectContestResultsByPageAndUserIdNoLimit(applicantId,contestName,contestType,contestDate).size());
        return page;
    }

    public Student selectStudentInfo(long userId) {
        return userMapper.selectStudentInfo(userId);
    }


    public Page getUserInformation(int pageSize, int pageIndex, String userGroup) {
        Page page =new Page();
        page.setList(userMapper.selectUserInformation(pageIndex,pageSize,userGroup));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(userMapper.selectUserInformationNolimit(userGroup).size());
        return page;
    }

    public int updateUserInformation(User user) {
        return userMapper.updateUserInformation(user);
    }
}
