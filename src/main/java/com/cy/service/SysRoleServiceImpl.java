package com.cy.service;

import com.cy.annotation.RequiredLog;
import com.cy.common.ServiceException;
import com.cy.dao.SysRoleDao;
import com.cy.entity.SystemRole;
import com.cy.utils.PageUtils;
import com.cy.vo.PageObject;
import com.cy.vo.RoleMenuVo;
import com.sun.rowset.internal.Row;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService{

    @Autowired
    private SysRoleDao sysRoleDao;

    @RequiredLog
    @Override
    public PageObject<SystemRole> findRolePageObjects(String name, Integer pageCurrent) {
        if (pageCurrent == null || pageCurrent <= 0){
            throw new ServiceException("pageCurrent数据不合法！");
        }
        int rowCount = sysRoleDao.findRowCount(name);
        if (rowCount <= 0){
            throw new ServiceException("系统中记录可能不存在，请核实！");
        }
        int pageSize = PageUtils.getPageSize();
        int startIndex = (pageCurrent -1) * pageSize;
        int pageCount = PageUtils.getPageCount(rowCount);
        List<SystemRole> roleList = sysRoleDao.findRoleList(name, startIndex, pageSize);
        if (roleList == null || roleList.size() <= 0){
            throw new ServiceException("没有查询到角色信息！");
        }
        return PageUtils.generatePageObject(pageCurrent,pageSize,pageCount,rowCount,roleList);
    }

    @RequiredLog
    @Override
    public int saveOrUpdateRole(SystemRole role,Integer[] menuIds) {
        log.debug("开始保存或更新对象");
        if (role == null){
            throw new ServiceException("保存对象不能为空！");
        }
        if (menuIds == null || menuIds.length <= 0){
            throw new ServiceException("必须为角色赋予权限");
        }
        if (StringUtils.isEmpty(role.getName())){
            throw new ServiceException("角色名不能为空");
        }
        int row = 0;
        role.setCreatedUser("admin");
        role.setModifiedUser("admin");
        try {
            if (role.getId() == null){
                row = sysRoleDao.saveRole(role);
            }else{
                //更新首先根据角色id删除sys_role_menus表中的记录，然后再重新插入记录
                row = sysRoleDao.updateRole(role);
                sysRoleDao.deleteMenuIdsByRoleId(role.getId());
            }
            sysRoleDao.saveRoleIdAndMenuId(role.getId(),menuIds);
        }catch (Exception e){
            throw new  ServiceException("保存角色对象出错，请联系系统维护人员！");
        }
        log.debug("保存或更新对象成功");
        return row;
    }

    @RequiredLog
    @Override
    public int deleteRoleById(Integer id) {
        if(id == null || id <= 0){
            throw new ServiceException("id不合法，请核实");
        }
        sysRoleDao.deleteRoleById(id);
        return 0;
    }

    @Override
    public RoleMenuVo findRoleById(Integer id) {
        if (id == null || id <= 0){
            throw new ServiceException("根据id查询角色信息，id不合法");
        }
        RoleMenuVo role = sysRoleDao.findRoleById(id);
        if (role == null){
            throw new ServiceException("根据id查询角色失败，请核实");
        }
        return role;
    }

    @Override
    public List<SystemRole> findRoleList() {
        List<SystemRole> roles = sysRoleDao.findRoles();
        if (roles == null || roles.size() <= 0){
            throw new ServiceException("查询角色信息不存在，请核实！");
        }
        return roles;
    }
}
