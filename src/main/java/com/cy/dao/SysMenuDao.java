package com.cy.dao;

import com.cy.entity.SystemMenu;
import com.cy.vo.Node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Mapper
public interface SysMenuDao {
    /**
     * 查询所有日志记录
     * @return
     */
    List<Map<String, Object>> findSysMenuList();

    /**
     * g根据id删除菜单信息
     * @param id
     * @return
     */
    int deleteSysMenuById(@Param("id") Integer id);

    /**
     * 查询节点数据
     * @return
     */
    List<Node> findZtreeMenuNodes();

    /**
     * dao层更新菜单数据
     * @param menu
     */
    void updateSysMenu(SystemMenu menu);

    /**
     * dao层保存菜单数据
     * @param menu
     */
    void saveSysMenu(SystemMenu menu);

    /**
     * 根据id查询子菜单
     * @return
     */
    List<SystemMenu> findChildMenuById();
}
