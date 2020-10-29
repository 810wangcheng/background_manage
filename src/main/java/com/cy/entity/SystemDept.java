package com.cy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemDept extends BaseEntity{

    private static final long serialVersionUID = -6466671047464074338L;
    private String name;
    private Integer parentId;
    private Integer sort;
    private String note;

}
