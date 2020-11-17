package com.cy.service;

import com.cy.common.ServiceException;
import com.cy.dao.SysRoleDao;
import com.cy.dao.SysUserDao;
import com.cy.entity.SysUser;
import com.cy.utils.PageUtils;
import com.cy.vo.PageObject;
import com.cy.vo.UserDeptVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService{
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public PageObject<UserDeptVo> findPageObject(Integer pageCurrent, String username) {
        if (pageCurrent == null || pageCurrent <=0){
            throw new ServiceException("pageCurrent值不合规");
        }
        int pageSize = PageUtils.getPageSize();
        int startIndex = (pageCurrent - 1) * pageSize;
        int rows = sysUserDao.getRows(username);
        List<UserDeptVo> userList = sysUserDao.findUserList(startIndex, pageSize, username);
        PageObject<UserDeptVo> userPageObject = PageUtils.generatePageObject(pageCurrent, pageSize, PageUtils.getPageCount(rows), rows, userList);
        return userPageObject;
    }

    @Override
    public int updateValidById(Integer id, Integer valid) {
        if (id == null || id <= 0){
            throw new ServiceException("id不合规");
        }
        if (valid != 0 && valid != 1){
            throw new ServiceException("valid只能为0或1");
        }
        int row = sysUserDao.updateValidById(id,valid);
        return row;
    }

    @Override
    public Map<String, Object> findUserRoleMapById(Integer id) {
        if (id == null || id <= 0){
            throw new ServiceException("id不合法！");
        }
        UserDeptVo user = sysUserDao.findUserById(id);
        if (user == null){
            throw new ServiceException("没有查询到用户信息！");
        }
        List<Integer> roleIds = sysRoleDao.findRoleIdByUserId(id);
        /*if (roleIds == null || roleIds.size() <=0){
            throw new ServiceException("没有查询到角色ID！");
        }*/
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("roleIds",roleIds);
        return map;
    }

    @Override
    public int saveOrUpdateRole(SysUser sysUser,Integer... roleIds) {
        if (sysUser == null){
            throw new ServiceException("更新保存用户不能为空");
        }
        if (StringUtils.isEmpty(sysUser.getUsername())){
            throw new ServiceException("用户用户名不能为空");
        }
        if (roleIds == null || roleIds.length <= 0){
            throw new ServiceException("必须为用户分配角色");
        }
        sysUser.setCreatedUser("admin");
        sysUser.setModifiedUser("admin");
        int row ;
        if (sysUser.getId() == null){
            if (StringUtils.isEmpty(sysUser.getPassword())){
                throw new ServiceException("用户密码不能为空");
            }
            //利用UUID生成盐值
            String salt = UUID.randomUUID().toString();
            //利用shiro框架的SimpleHash生成密码
            SimpleHash password = new SimpleHash("MD5",sysUser.getPassword(),salt);
            //将密码转化16进制进行保存
            sysUser.setPassword(password.toHex());
            sysUser.setSalt(salt);
            row = sysUserDao.saveSysUser(sysUser);
            sysUser = sysUserDao.findUserByUserName(sysUser.getUsername());
            sysRoleDao.saveRoleIdAndUserId(sysUser.getId(),roleIds);
            log.debug("保存用户成功");
        }else{
           /* UserDeptVo userDo = sysUserDao.findUserById(sysUser.getDeptId());
            sysUser.setSalt(userDo.getSalt());
            //将页面输入密码进行加密同数据库密码进行比较，判断密码是否匹配
            if(StringUtils.equals(new SimpleHash("MD5",sysUser.getPassword(),sysUser.getSalt()).toHex(),userDo.getPassword())){
                throw new ServiceException("用户密码不匹配，请核实");
            }*/
            row = sysUserDao.updateSysUserById(sysUser);
            sysRoleDao.deleteRoleIdByUserId(sysUser.getId());
            sysRoleDao.saveRoleIdAndUserId(sysUser.getId(),roleIds);
            System.out.print(sysUser.getId());
        }
        return row;
    }

    //TODO
    @Override
    public int updatePassword(String pwd, String newPwd, String cfgPwd) {
        return 0;
    }
}
