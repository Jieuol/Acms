package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.mapper.AdminMapper;
import edu.lgxy.lbj.asms.mapper.ParticipantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private ParticipantMapper participantMapper;
    public int updateDeclaration(ContestDeclaration contestDeclarationId) {
        return adminMapper.updateDeclaration(contestDeclarationId);
    }

    public Page getParticipantListByPage(int pageSize, int pageIndex, String contestName, String contestType, String contestDate,String examineState) {
        Page page =new Page();
        page.setList(adminMapper.selectByPage(pageIndex,pageSize,contestName,contestType,contestDate,examineState));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(adminMapper.selectByPageNoLimit(contestName,contestType,contestDate,examineState).size());
        return page;
    }

}
