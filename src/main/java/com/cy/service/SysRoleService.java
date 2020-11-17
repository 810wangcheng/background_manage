package com.cy.service;

import com.cy.entity.SystemRole;
import com.cy.vo.PageObject;
import com.cy.vo.RoleMenuVo;

import java.util.List;

/**
 * @author Administrator
 */
public interface SysRoleService {

    /**
     * 查询角色，进行分页显示
     * @param name
     * @param pageCurrent
     * @return
     */
    PageObject<SystemRole> findRolePageObjects(String name,Integer pageCurrent);

    /**
     * 保存更新角色
     * @param role
     * @param menuIds
     * @return
     */
    int saveOrUpdateRole(SystemRole role,Integer... menuIds);

    /**
     * 删除角色
     * @param id
     * @return
     */
    int deleteRoleById(Integer id);

    /**
     * 根据Id查询角色记录
     * @param id
     * @return
     */
    RoleMenuVo findRoleById(Integer id);

    /**
     * 查询所有角色信息，初始化用户编辑界面选择框
     * @return
     */
    List<SystemRole> findRoleList();
}
