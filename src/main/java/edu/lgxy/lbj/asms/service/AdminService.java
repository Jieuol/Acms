package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {
    @Resource
    private AdminMapper adminMapper;
    public int updateDeclaration(ContestDeclaration contestDeclarationId) {
        return adminMapper.updateDeclaration(contestDeclarationId);
    }
}
