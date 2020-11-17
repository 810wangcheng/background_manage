package com.cy.controller;

import com.cy.dao.SysUserDao;
import com.cy.entity.SysUser;
import com.cy.service.SysUserService;
import com.cy.vo.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("doUserListUI")
    public String doUserListUI(){
        return "sys/user_list";
    }

    @RequestMapping("doUserEditUI")
    public String doUserEditUI(){
        return "sys/user_edit";
    }

    @RequestMapping("doPwdEditUI")
    public String doPwdEditUI(){
        return "sys/pwd_edit";
    }

    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(Integer pageCurrent,String username){
        return new JsonResult(sysUserService.findPageObject(pageCurrent,username));
    }

    @RequestMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer id,Integer valid){
        return new JsonResult(sysUserService.updateValidById(id,valid));
    }

    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(sysUserService.findUserRoleMapById(id));
    }

    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysUser sysUser,Integer... roleIds){
        return new JsonResult("保存成功，影响行数："+sysUserService.saveOrUpdateRole(sysUser,roleIds));
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysUser sysUser,Integer... roleIds){
        return new JsonResult("保存成功，影响行数："+sysUserService.saveOrUpdateRole(sysUser,roleIds));
    }

    @RequestMapping("doUpdatePassword")
    @ResponseBody
    public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd){
        return new JsonResult(sysUserService.updatePassword(pwd,newPwd,cfgPwd));
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public JsonResult doLogin(String username,String password,Boolean isRememberMe){
        //1.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2.封装页面输入用户名密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        //记住我功能实现
        if (isRememberMe){
            usernamePasswordToken.setRememberMe(true);
        }
        //3.subject对象将token提交给securityManager
        subject.login(usernamePasswordToken);
        return new JsonResult("login ok");
    }
}
