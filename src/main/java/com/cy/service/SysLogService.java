package com.cy.service;

import com.cy.entity.SystemLog;
import com.cy.vo.PageObject;

/**
 * @author Administrator
 */
public interface SysLogService {

    /**
     * 查询日志分页信息
     * @param pageCurrent
     * @param username
     * @return
     */
    PageObject<SystemLog> findPageObject(Integer pageCurrent,String username);

    /**
     * 根据id删除日志信息
     * @param ids
     * @return
     */
    int deleteSysLogs(Integer[] ids);
}
