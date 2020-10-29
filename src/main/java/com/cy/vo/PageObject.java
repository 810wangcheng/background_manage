package com.cy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> implements Serializable {

    private Integer pageCurrent; //当前页
    private Integer pageSize;  //页面显示条数
    private Integer pageCount; //总页数
    private Integer rowCount;  //总条数
    private List<T> records;    //页面显示对象
}
