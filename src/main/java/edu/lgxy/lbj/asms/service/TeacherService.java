package edu.lgxy.lbj.asms.service;


import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    public int insertContestDeclaration(ContestDeclaration contestDeclaration) {
    return teacherMapper.insertContestDeclaration(contestDeclaration);
    }
}
