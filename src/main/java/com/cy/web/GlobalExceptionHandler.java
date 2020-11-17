package com.cy.web;

import com.cy.common.ServiceException;
import com.cy.vo.JsonResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public JsonResult doHandlerShiroException(ShiroException e){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(0);
        if (e instanceof UnknownAccountException){
            jsonResult.setMessage("账户不存在");
        }else if (e instanceof LockedAccountException){
            jsonResult.setMessage("账户被禁用");
        }else if (e instanceof IncorrectCredentialsException){
            jsonResult.setMessage("密码不正确");
        }else if (e instanceof AuthorizationException){
            jsonResult.setMessage("没有操作权限");
        }
        return jsonResult;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public JsonResult doHandlerServiceException(ServiceException e){
        //封装错误详细信息到控制台
        e.printStackTrace();
        return new JsonResult(e);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JsonResult doHandlerRuntimeException(RuntimeException e){
        //封装错误详细信息到控制台
        e.printStackTrace();
        return new JsonResult(e);
    }
}
