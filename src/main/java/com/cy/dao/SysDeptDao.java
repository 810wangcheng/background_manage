package com.cy.dao;

import com.cy.entity.SystemDept;
import com.cy.vo.Node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Mapper
public interface SysDeptDao {

    /**
     * 查询所有数据库所有数据
     * @return
     */
    List<Map<String, Object>> findSysDeptList();

    /**
     * 根据id删除部门记录
     * @param id
     * @return
     */
    int deleteSysDeptById(Integer id);

    /**
     * 查询节点数据
     * @return
     */
    List<Node> findNodeList0SysDept();

    /**
     * dao层保存部门数据
     * @param dept
     */
    void saveSysDept(SystemDept dept);

    /**
     * dao层更新部门数据
     * @param dept
     */
    void updateSysDept(SystemDept dept);

    /**
     * 根据id判断是否存在子部门
     * @param id
     * @return
     */
    List<SystemDept> findChildDept(Integer id);

    /**
     * dao层根据id查询部门信息
     * @param id
     * @return
     */
    SystemDept findDeptById(Integer id);
}
