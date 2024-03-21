package edu.lgxy.lbj.asms.mapper;

import edu.lgxy.lbj.asms.entity.ContestResults;
import edu.lgxy.lbj.asms.entity.Student;
import edu.lgxy.lbj.asms.entity.User;
import edu.lgxy.lbj.asms.qo.ReceiveUser;

import java.util.Collection;
import java.util.List;

public interface UserMapper {

    public User selectByUser(User u);
    public User selectByUserName(String username);
    int updatePassword(User user1);

    int updateUser(ReceiveUser user);

    List<ContestResults> selectContestResultsByPageAndUserId(int pageIndex, int pageSize, int applicantId, String contestName, String contestType, String contestDate);

    List<ContestResults> selectContestResultsByPageAndUserIdNoLimit(int applicantId, String contestName, String contestType, String contestDate);

    Student selectStudentInfo(long userId);

    List<User> selectUserInformation(int pageIndex, int pageSize, String userGroup,String username);

    List<User> selectUserInformationNolimit(String userGroup,String username);

    int updateUserInformation(User user);

    int insertUser(User user);
}
