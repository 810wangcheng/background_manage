package com.cy.controller;

import com.cy.entity.SystemLog;
import com.cy.service.SysLogService;
import com.cy.vo.JsonResult;
import com.cy.vo.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/log/")
public class LogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("doLogListUI")
    public String doLogListUI(){
        return "sys/log_list";
    }

    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(Integer pageCurrent,String username){
        PageObject<SystemLog> pageObject = sysLogService.findPageObject(pageCurrent, username);
        return new JsonResult("success",pageObject);
    }

    @RequestMapping("doDeleteObjects")
    @ResponseBody
    public JsonResult doDeleteObjects(Integer... ids){
        int row = sysLogService.deleteSysLogs(ids);
        return new JsonResult("delete success,row="+row);
    }
}
