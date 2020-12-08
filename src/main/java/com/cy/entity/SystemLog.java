package com.cy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemLog implements Serializable {

    private static final long serialVersionUID = -6934142970282005474L;
    private Integer id;
    private String username;
    private String operation;
    private String method;
    private String params;
    private Long time;
    private String ip;
    private Date createdTime;
}
