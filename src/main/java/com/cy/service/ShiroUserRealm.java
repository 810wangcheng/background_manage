package com.cy.service;

import com.cy.dao.SysMenuDao;
import com.cy.dao.SysRoleDao;
import com.cy.dao.SysUserDao;
import com.cy.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator 继承autorizingRealm
 */
@Service
public class ShiroUserRealm  extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysMenuDao sysMenuDao;


    /**
     * 设置凭证匹配器
     * 1.创建认证匹配对象
     * 2.设置加密方法
     * 3.设置加密次数
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //1.构建凭证匹配对象 hashedCredentialsMatcher
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //2.设置加密算法
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //3.设置加密次数
        hashedCredentialsMatcher.setHashIterations(1);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    /**
     * 获取授权对象
     * 1.获取登录用户id
     * 2.根据userId查询角色Id
     * 3.根据roleId查询菜单Id
     * 4.根据menuId查询菜单表
     * 5.获取菜单权限
     * 6.封装成授权对象并返回
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1.获取当前登录用户
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        Integer userId = user.getId();
        //数组将list转化为数组
        Integer[] array = {};
        //2.根据用户id查询角色id
        List<Integer> roleIds = sysRoleDao.findRoleIdByUserId(userId);
        if (roleIds == null || roleIds.size() <= 0){
            throw new AuthorizationException();
        }
        //3.根据角色id查询菜单id
        List<Integer> menuIds = sysRoleDao.findMenuIdByRoleIds(roleIds.toArray(array));
        if (menuIds == null || menuIds.size() <= 0){
            throw new AuthorizationException();
        }
        //4.根据菜单id查询权限
        List<String> permissionList = sysMenuDao.findPermissionsByMenuIds(menuIds.toArray(array));
        //5.将权限信息存到set中，不重复
        Set<String> permissionSet = new HashSet<>();
        for (String s : permissionList) {
            if (!StringUtils.isEmpty(s)){
                permissionSet.add(s);
            }
        }
        //6.创建授权对象，添加权限并返回
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 获取认证对象
     * 1.获取界面输入用户名密码
     * 2.根据用户名查询数据库
     * 3.判断用户状态
     * 4.byteSource获取凭证盐值
     * 5.创建认证对象并返回
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户名密码（页面输入）
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        //2.根据用户查询数据库
        SysUser sysUser = sysUserDao.findUserByUserName(username);
        //3.判断用户是否存在，不存在抛出不知道账户异常
        if (sysUser == null){
            throw new UnknownAccountException();
        }
        //4.判断用户是否被禁用，被禁用抛出账户锁定异常
        if (sysUser.getValid() == 0){
            throw new LockedAccountException();
        }
        //5.封装盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(sysUser.getSalt());
        //6.封装用户信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),credentialsSalt,sysUser.getUsername());
        return info;
    }
}
