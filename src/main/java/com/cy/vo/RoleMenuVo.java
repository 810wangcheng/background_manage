package com.cy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleMenuVo implements Serializable {

    private static final long serialVersionUID = 7040074271314404437L;

    private Integer id;
    private String name;
    private String note;
    private List<Integer> menuIds;
}
