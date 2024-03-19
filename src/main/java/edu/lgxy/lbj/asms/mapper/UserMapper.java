package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.ContestResults;
import edu.lgxy.lbj.asms.entity.Student;
import edu.lgxy.lbj.asms.entity.User;
import edu.lgxy.lbj.asms.qo.ReceiveUser;

import java.util.Collection;
import java.util.List;

public interface UserMapper {

    public User selectByUserName(User u);

    int updatePassword(User user1);

    int updateUser(ReceiveUser user);

    List<ContestResults> selectContestResultsByPageAndUserId(int pageIndex, int pageSize, int applicantId, String contestName, String contestType, String contestDate);

    List<ContestResults> selectContestResultsByPageAndUserIdNoLimit(int applicantId, String contestName, String contestType, String contestDate);

    Student selectStudentInfo(long userId);

    List<User> selectUserInformation(int pageIndex, int pageSize, String userGroup);

    List<User> selectUserInformationNolimit(String userGroup);

    int updateUserInformation(User user);
}
