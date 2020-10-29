package com.cy.service;

import com.cy.common.ServiceException;
import com.cy.dao.SysDeptDao;
import com.cy.entity.SystemDept;
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
public class SysDeptServiceImpl implements SysDeptService{

    @Autowired
    private SysDeptDao sysDeptDao;

    @Override
    public List<Map<String, Object>> findObjects() {
        List<Map<String,Object>> sysDeptList = sysDeptDao.findSysDeptList();
        return sysDeptList;
    }

    @Override
    public int deleteSysDept(Integer id) {
        if (id <= 0) {
            throw new ServiceException("id数据不合法，请核实！");
        }
        //需判断是否有子部门
        List<SystemDept> childDeptList = sysDeptDao.findChildDept(id);
        if (childDeptList.size() <=0 || childDeptList == null){
            throw new ServiceException("该部门下存在子部门，请先删除子部门");
        }
        int row = sysDeptDao.deleteSysDeptById(id);
        return row;
    }

    @Override
    public List<Node> findNode0SysDept() {
        List<Node> nodeList = sysDeptDao.findNodeList0SysDept();
        return nodeList;
    }

    @Override
    public void saveSysDept(SystemDept dept) {
        if (StringUtils.isEmpty(dept.getName())) {
            throw new ServiceException("部门名称不能为空");
        }
        if (dept.getId() <= 0){
            throw new ServiceException("id数据不合法");
        }

        dept.setCreatedUser("admin");
        dept.setModifiedUser("admin");

        sysDeptDao.saveSysDept(dept);
    }

    @Override
    public void updateSysDept(SystemDept dept) {

        if (StringUtils.isEmpty(dept.getName())) {
            throw new ServiceException("部门名称不能为空");
        }
        if (dept.getId() <= 0){
            throw new ServiceException("id数据不合法");
        }

        dept.setCreatedUser("admin");
        dept.setModifiedUser("admin");

        sysDeptDao.updateSysDept(dept);
    }
}
