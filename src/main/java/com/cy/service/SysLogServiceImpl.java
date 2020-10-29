package com.cy.service;

import com.cy.common.ServiceException;
import com.cy.dao.SysLogDao;
import com.cy.entity.SystemLog;
import com.cy.utils.PageUtils;
import com.cy.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public PageObject<SystemLog> findPageObject(Integer pageCurrent, String username) {
        if (pageCurrent == null || pageCurrent <= 0)
            throw new ServiceException("当前页码值不正确");
        int pageSize = PageUtils.getPageSize();
        int startIndex = (pageCurrent -1) * pageSize;
        int rowCount = sysLogDao.getRowCount(username);
        if (rowCount < 0)
            throw new ServiceException("系统中日志记录不存在，请核实！");
        int pageCount = PageUtils.getPageCount(rowCount);
        List<SystemLog> systemLogList = sysLogDao.findSystemLogList(startIndex, pageSize, username);
        return PageUtils.generatePageObject(pageCurrent,pageSize,pageCount,rowCount,systemLogList);
    }

    @Override
    public int deleteSysLogs(Integer[] ids) {
        if (ids == null || ids.length <= 0)
            throw new ServiceException("没有需要删除的数据，请核实！");
        //循环遍历数组，逐个进行删除
        /*for (int i = 0; i < ids.length; i++){
            sysLogDao.deleteSysLogById(ids[i]);
        }*/
        //判断id在ids中进行数据删除，批量删除
        int row = sysLogDao.deleteSysLogsByIds(ids);
        return row;
    }
}
