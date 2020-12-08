package com.cy.dao;

import com.cy.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
/**
 * @author Administrator
 */
@Mapper
public interface SysLogDao {

    /**
     * 分页查询日志信息
     * @param startIndex
     * @param pageSize
     * @param username
     * @return
     */
    List<SystemLog> findSystemLogList(
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize,
            @Param("username") String username);

    /**
     * 获取所有记录数
     * @param username
     * @return
     */
    int getRowCount(@Param("username") String username);

    /**
     * 根据id删除日志信息
     * @param ids
     * @return
     */
    int deleteSysLogsByIds(@Param("ids") Integer[] ids);

    void insertSysLog(SystemLog systemLog);
}
