package com.cy.utils;

import com.cy.vo.PageObject;

import java.util.List;

/**
 * 工具类：将页面显示对象封装成分页对象
 */
public class PageUtils {

    private static Integer pageSize = 8;

    public static Integer getPageSize(){
        return pageSize;
    }

    public static <T>PageObject<T> generatePageObject(Integer pageCurrent, Integer pageSize, Integer pageCount, Integer rowCount, List<T> data){
        PageObject<T> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setPageCount(pageCount);
        pageObject.setRowCount(rowCount);
        pageObject.setRecords(data);
        return pageObject;
    }

    public static int getPageCount(int rowCount) {
        return (rowCount-1)/pageSize + 1;
    }
}
