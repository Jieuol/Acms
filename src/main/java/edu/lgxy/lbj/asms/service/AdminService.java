package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
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

    public Page getParticipantListByPage(int pageSize, int pageIndex, String contestName, String contestType, String contestDate) {
        Page page =new Page();
        page.setList(adminMapper.selectByPage(pageIndex,pageSize,contestName,contestType,contestDate));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(adminMapper.selectByPageNoLimit(contestName,contestType,contestDate).size());
        return page;
    }

    public int updateParticipant(ContestParticipant contestParticipantc) {
        return adminMapper.updateParticipant(contestParticipantc);
    }
}
