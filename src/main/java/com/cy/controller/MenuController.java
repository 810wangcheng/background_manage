package com.cy.controller;

import com.cy.entity.SystemMenu;
import com.cy.service.SysMenuService;
import com.cy.vo.JsonResult;
import com.cy.vo.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/menu/")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("doMenuListUI")
    public String doMenuListUI(){
        return "sys/menu_list";
    }

    @RequestMapping("doMenuEditUI")
    public String doMenuEditUI(){
        return "sys/menu_edit";
    }

    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        return new JsonResult(sysMenuService.findSysMenuList());
    }

    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteSysLog(Integer id){
        int row = sysMenuService.deleteSysMenuById(id);
        return new JsonResult("delete success,row="+row);
    }

    @RequestMapping("doFindZtreeMenuNodes")
    @ResponseBody
    public JsonResult doFindZtreeMenuNodes(){
        List<Node> nodeList =  sysMenuService.findZtreeMenuNodes();
        return new JsonResult(nodeList);
    }

    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveSysMenu(SystemMenu menu){
        sysMenuService.saveOrUpdateSysMenu(menu);
        return new JsonResult("save success");
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateSysMenu(SystemMenu menu){
        sysMenuService.saveOrUpdateSysMenu(menu);
        return new JsonResult("save success");
    }
}
