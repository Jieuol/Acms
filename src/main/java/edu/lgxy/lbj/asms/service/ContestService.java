package edu.lgxy.lbj.asms.service;

import edu.lgxy.lbj.asms.config.Page;
import edu.lgxy.lbj.asms.entity.ContestInformation;
import edu.lgxy.lbj.asms.mapper.ContestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContestService {
    @Resource
    private ContestMapper contestMapper;
    public List<ContestInformation> getContestList() {
        return contestMapper.getContestList();
    }

    public Page getContestListByPage(int pageIndex,int pageSize) {
        Page page =new Page();
        page.setList(contestMapper.selectByPage(pageIndex,pageSize));
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalRecords(contestMapper.getContestList().size());
        return page;
    }
}
