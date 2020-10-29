package com.cy.service;

import com.cy.entity.SystemDept;
import com.cy.vo.Node;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface SysDeptService {

    /**
     * 服务层查询所有数据
     * @return
     */
    List<Map<String,Object>> findObjects();

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    int deleteSysDept(Integer id);

    /**
     * 查询节点数据
     * @return
     */
    List<Node> findNode0SysDept();

    /**
     * service层保存数据
     * @param dept
     */
    void saveSysDept(SystemDept dept);

    /**
     * service层更新数据
     * @param dept
     */
    void updateSysDept(SystemDept dept);
}
