package com.cy.controller;

import com.cy.entity.SystemDept;
import com.cy.service.SysDeptService;
import com.cy.vo.JsonResult;
import com.cy.vo.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/dept/")
public class DeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("doDeptListUI")
    public String doDeptListUI(){
        return "sys/dept_list";
    }

    @RequestMapping("doDeptEditUI")
    public String doDeptEditUI(){
        return "sys/dept_edit";
    }

    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        return new JsonResult(sysDeptService.findObjects());
    }

    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteSysDept(Integer id){
        int row = sysDeptService.deleteSysDept(id);
        return new JsonResult();
    }

    @RequestMapping("doFindZTreeNodes")
    @ResponseBody
    public JsonResult doFindZTreeNodes0SysDept(){
        List<Node> nodeList = sysDeptService.findNode0SysDept();
        return new JsonResult(nodeList);
    }

    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveSysDept(SystemDept dept){
        sysDeptService.saveSysDept(dept);
        return new JsonResult("保存成功");
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateSysDept(SystemDept dept){
        sysDeptService.updateSysDept(dept);
        return new JsonResult("更新成功");
    }

}
