package com.cy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1829981571083687538L;
    private Integer id;

    private Date createdTime;

    private Date modifiedTime;

    private String createdUser;

    private String modifiedUser;
}
