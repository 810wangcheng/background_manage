package com.cy.service;

import com.cy.entity.SysUser;
import com.cy.vo.PageObject;
import com.cy.vo.UserDeptVo;

import java.util.Map;

/**
 * @author Administrator
 */
public interface SysUserService {

    /**
     * service层查询用户信息，并封装为pageObject对象
     * @param pageCurrent
     * @param username
     * @return
     */
    PageObject<UserDeptVo> findPageObject(Integer pageCurrent, String username);

    /**
     * 根据id修改valid，决定角色是否启用
     * @param id
     * @param valid
     * @return
     */
    int updateValidById(Integer id, Integer valid);

    /**
     * 根据id查询用户跟角色
     * @param id
     * @return
     */
    Map<String,Object> findUserRoleMapById(Integer id);

    /**
     * service层保存用户角色信息
     * @param sysUser
     * @param roleIds
     * @return
     */
    int saveOrUpdateRole(SysUser sysUser,Integer... roleIds);

    /**
     * 修改用户密码
     * @param pwd
     * @param newPwd
     * @param cfgPwd
     * @return
     */
    int updatePassword(String pwd, String newPwd, String cfgPwd);
}
