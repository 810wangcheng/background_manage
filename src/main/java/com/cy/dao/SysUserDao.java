package com.cy.dao;

import com.cy.entity.SysUser;
import com.cy.vo.UserDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface SysUserDao {
    /**
     * dao层查询用户
     * @param startIndex
     * @param pageSize
     * @param username
     * @return
     */
    List<UserDeptVo> findUserList(Integer startIndex, Integer pageSize, String username);

    /**
     * 查询用户表中所有记录数
     * @param username
     * @return
     */
    int getRows(String username);

    /**
     * dao层根据id修改valid
     * @param id
     * @param valid
     * @return
     */
    int updateValidById(Integer id, Integer valid);

    /**
     * dao层根据id查询用户
     * @param id
     * @return
     */
    UserDeptVo findUserById(Integer id);

    /**
     * dao层保存用户信息
     * @param sysUser
     * @return
     */
    int saveSysUser(SysUser sysUser);

    /**
     * dao层更新用户信息
     * @param sysUser
     * @return
     */
    int updateSysUser(SysUser sysUser);

    /**
     * dao根据用户名密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    SysUser findUserByUsernameAndPassword(String username);

    /**
     * dao层根据id进行更新用户信息
     * @param sysUser
     * @return
     */
    int updateSysUserById(SysUser sysUser);

    /**
     * dao层根据用户查询数据库
     * @param username
     * @return
     */
    SysUser findUserByUserName(String username);
}
