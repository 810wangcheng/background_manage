package com.cy.dao;

import com.cy.entity.SystemRole;
import com.cy.vo.RoleMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface SysRoleDao {

    /**
     * dao层定义查询角色列表方法
     * @param name
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<SystemRole> findRoleList(@Param("name") String name,
                                  @Param("startIndex") Integer startIndex,
                                  @Param("pageSize") Integer pageSize);

    /**
     * 获取总记录数
     * @param name
     * @return
     */
    int findRowCount(@Param("name") String name);

    /**
     * dao层保存角色对象
     * @param role
     * @return
     */
    int saveRole(SystemRole role);

    /**
     * dao层保存角色id和菜单id
     * @param roleId
     * @param menuIds
     */
    void saveRoleIdAndMenuId(Integer roleId, Integer... menuIds);

    /**
     * dao层更新角色对象
     * @param role
     * @return
     */
    int updateRole(SystemRole role);

    /**
     * dao层 根据角色id删除sys_role_menus表中的记录
     * @param roleId
     */
    void deleteMenuIdsByRoleId(Integer roleId);

    /**
     * dao层 根据id删除角色记录
     * @param id
     * @return
     */
    int deleteRoleById(Integer id);

    /**
     * dao层根据Id查询角色记录
     * @param id
     * @return
     */
    RoleMenuVo findRoleById(Integer id);

    /**
     * dao层根据用户id查询角色id
     * @param id
     * @return
     */
    List<Integer> findRoleIdByUserId(Integer id);

    /**
     * 查询表中所有角色信息，初始化用户编辑界面角色选择框
     * @return
     */
    List<SystemRole> findRoles();

    /**
     * dao层保存userId,roleId
     * @param userId
     * @param roleIds
     */
    void saveRoleIdAndUserId(Integer userId, Integer[] roleIds);

    /**
     * 根据userId删除roleId
     * @param userId
     */
    void deleteRoleIdByUserId(Integer userId);

    /**
     * 根据角色Id数组查询菜单
     * @param toArray
     * @return
     */
    List<Integer> findMenuIdByRoleIds(Integer[] toArray);
}
