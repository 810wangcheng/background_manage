package com.cy.vo;

import com.cy.entity.SystemDept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDeptVo {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private Integer valid;
    private Date createdTime;
    private Date modifiedTime;
    private SystemDept sysDept;
}
