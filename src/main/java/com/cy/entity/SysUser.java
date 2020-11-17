package com.cy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends BaseEntity{
    private String username;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    /**新增时默认启用*/
    private Integer valid = 1;
    private Integer deptId;
}
