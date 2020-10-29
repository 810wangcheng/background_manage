package com.cy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单编辑页面节点对象
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    private Integer id;
    private String name;
    private Integer parentId;
}
