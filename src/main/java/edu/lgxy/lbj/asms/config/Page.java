package edu.lgxy.lbj.asms.config;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页的相关属性和方法
 *
 * @author lck100
 */
public class Page implements Serializable {
    private List list;
    // 所有数据结果集
    // 每页显示多少条数据
    private int pageSize;
    // 页码（第几页）
    private int pageIndex;
    // 记录总条数
    private int totalRecords;

    /**
     * 计算总的页面数
     *
     * @return 返回一共有多少页
     */
    public int getTotalPages() {
        if (totalRecords % pageSize == 0) {
            // 如果记录总条数对每页显示记录数取整等于0，则表示页面数刚好分完。
            return totalRecords / pageSize;
        } else {// 如果取不尽，那么就添加一页来放剩余的记录
            return totalRecords / pageSize + 1;
        }
    }

    /**
     * 获取首页的页码
     *
     * @return 返回首页的页码，即为1
     */
    public int getFirstPageIndex() {
        return 1;
    }

    /**
     * 获取上一页的页码
     *
     * @return 返回上一页的页码，即当前页码减去1
     */
    public int getPreviousPageIndex() {
        if (pageIndex <= 1) {
            return 1;
        }
        return pageIndex - 1;
    }

    /**
     * 获取下一页的页码
     *
     * @return 返回下一页的页码，即当前页码加上1
     */
    public int getNextPageIndex() {
        if (pageIndex >= getLastPageIndex()) {
            return getLastPageIndex();
        }
        return pageIndex + 1;
    }

    /**
     * 获取最后一页的页码
     *
     * @return 返回最后一页的页码，即总页数
     */
    public int getLastPageIndex() {
        return getTotalPages();
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
}