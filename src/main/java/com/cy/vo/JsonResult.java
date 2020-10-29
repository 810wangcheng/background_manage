package com.cy.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回前端统一对象
 * @author Administrator
 */
@Data
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1522077225994336248L;
    private Integer state = 1; //1:成功 0:失败
    private String message = "OK";  //返回页面信息
    private Object data; //封装数据

    public JsonResult() {
    }

    public JsonResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public JsonResult(String message) {
        this.message = message;
    }

    public JsonResult(Object data) {
        this.data = data;
    }

    public JsonResult(Throwable e) {
        this.state = 0;
        this.message = "error,"+e.getMessage();
    }

}
