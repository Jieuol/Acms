package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.config.PageAndUserId;
import edu.lgxy.lbj.asms.entity.ContestDeclaration;
import edu.lgxy.lbj.asms.entity.ContestParticipant;
import edu.lgxy.lbj.asms.entity.Message;
import edu.lgxy.lbj.asms.mapper.AdminMapper;
import edu.lgxy.lbj.asms.mapper.ParticipantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminService {
    @Resource
    private MessageService messageService;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private ParticipantMapper participantMapper;

    public int updateDeclaration(ContestDeclaration contestDeclaration) {
        Message message= new Message();
        message.setUserId(contestDeclaration.getApplicantId());
        message.setMessageName("您的:"+contestDeclaration.getContestName()+",审核已完成");
        message.setMessageInformation("审核结果为:"+contestDeclaration.getExamineState()+",详细信息请前往相关页面进行查询");
        log.info("getContestDeclarationId():"+contestDeclaration.getContestDeclarationId());
        messageService.insertMessage(message);
        return adminMapper.updateDeclaration(contestDeclaration);
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
