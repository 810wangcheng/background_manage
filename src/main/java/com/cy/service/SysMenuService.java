package com.cy.service;

import com.cy.entity.SystemMenu;
import com.cy.vo.Node;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface SysMenuService {
    /**
     * 查询菜单所有记录
     * @return
     */
    List<Map<String,Object>> findSysMenuList();

    /**
     * 根据id菜单信息
     * @param id
     * @return
     */
    int deleteSysMenuById(Integer id);

    /**
     * service查询节点信息
     * @return
     */
    List<Node> findZtreeMenuNodes();

    /**
     * 保存或更新菜单信息
     * @param menu
     */
    void saveOrUpdateSysMenu(SystemMenu menu);
}
