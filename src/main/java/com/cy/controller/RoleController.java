package com.cy.controller;

import com.cy.entity.SystemRole;
import com.cy.service.SysRoleService;
import com.cy.vo.JsonResult;
import com.cy.vo.RoleMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/role/")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("doRoleListUI")
    public String doRoleListUI(){
        return "sys/role_list";
    }

    @RequestMapping("doRoleEditUI")
    public String doRoleEditUI(){
        return "sys/role_edit";
    }

    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindRolePages(String name,Integer pageCurrent){
        return new JsonResult(sysRoleService.findRolePageObjects(name,pageCurrent));
    }

    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindRoleById(Integer id){
        RoleMenuVo role = sysRoleService.findRoleById(id);
        return  new JsonResult(role);
    }

    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SystemRole role,Integer... menuIds){
        return new JsonResult(sysRoleService.saveOrUpdateRole(role,menuIds));
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SystemRole role,Integer... menuIds){
        return new JsonResult("保存或更新角色成功，影响行数："+sysRoleService.saveOrUpdateRole(role,menuIds));
    }

    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteRole(Integer id){
        int row = sysRoleService.deleteRoleById(id);
        return new JsonResult("delete ok,row="+row);
    }

    @RequestMapping("doFindRoles")
    @ResponseBody
    public JsonResult doFindRoles(){
        return new JsonResult(sysRoleService.findRoleList());
    }
}