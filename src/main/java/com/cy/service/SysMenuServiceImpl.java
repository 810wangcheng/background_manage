package com.cy.service;

import com.cy.common.ServiceException;
import com.cy.dao.SysMenuDao;
import com.cy.entity.SystemMenu;
import com.cy.vo.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public List<Map<String, Object>> findSysMenuList() {
        List<Map<String,Object>> sysMenuList = sysMenuDao.findSysMenuList();
        if (sysMenuList == null || sysMenuList.size() <= 0) {
            throw new ServiceException("系统中记录不存在，请核实！");
        }
        return sysMenuList;
    }

    @Override
    public int deleteSysMenuById(Integer id) {
        //需要判断是否有子菜单，有不允许删除
        List<SystemMenu> menuList = sysMenuDao.findChildMenuById();
        if (menuList == null || menuList.size() <= 0){
            throw new ServiceException("菜单下存在子菜单，请先删除子菜单");
        }
        int row = sysMenuDao.deleteSysMenuById(id);
        if (row < 0) {
            throw new ServiceException("删除失败，系统中可能没有数据！");
        }
        return row;
    }

    @Override
    public List<Node> findZtreeMenuNodes() {
        List<Node> nodeList = sysMenuDao.findZtreeMenuNodes();
        if(nodeList == null || nodeList.size() <= 0) {
            throw new ServiceException("系统中没有查询到节点数据，请核实！");
        }
        return nodeList;
    }

    @Override
    public void saveOrUpdateSysMenu(SystemMenu menu) {

        if (menu == null) {
            throw new ServiceException("菜单对象不能为空！");
        }
        if (StringUtils.isEmpty(menu.getName())) {
            throw new ServiceException("菜单名称不能为空！");
        }
        if (StringUtils.isEmpty(menu.getPermission())) {
            throw new ServiceException("菜单权限不能为空！");
        }

        //根据id是否存在，进行更新或保存操作
        if (menu.getId() == null || menu.getId() < 0){
            sysMenuDao.updateSysMenu(menu);
        }else{
            menu.setCreatedUser("admin");
            menu.setModifiedUser("admin");
            sysMenuDao.saveSysMenu(menu);
        }
    }

}
