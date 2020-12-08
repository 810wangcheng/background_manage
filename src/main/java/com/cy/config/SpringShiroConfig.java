package com.cy.config;

import com.cy.vo.JsonResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;

/**
 * @author Administrator
 * @Configuration 标明这是一个配置类
 */
@Configuration
public class SpringShiroConfig {

    /**
     * 创建securityManager对象并交给spring进行管理
     * @return
     */
    @Bean
    public SecurityManager newSecurityManager(@Autowired Realm realm,@Autowired CacheManager cacheManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        //设置缓存管理
        securityManager.setCacheManager(cacheManager);
        //设置记住我功能
        securityManager.setRememberMeManager(newRememberMeManager());
        //设置会话管理
        securityManager.setSessionManager(newSessionManager());
        return securityManager;
    }

    /**
     * 创建shiroFilterFactoryBean对象，交给spring进行管理
     * 并设置过滤规则
     * @return
     */
    @Bean("shiroFilterFactory")
    public ShiroFilterFactoryBean newShiroFilterFactoryBean(@Autowired SecurityManager securityManager ){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        //没有登录跳转到登录页面
        filterFactoryBean.setLoginUrl("/doLogin");
        //定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
        LinkedHashMap<String,String> map=
                new LinkedHashMap<>();
        //静态资源允许匿名访问:"anon"
        map.put("/bower_components/**","anon");
        map.put("/build/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        //登录页面允许匿名访问
        map.put("/user/doLogin","anon");
        //退出页面允许匿名访问
        map.put("/doLogout","logout");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        /*map.put("/**","authc");*/
        map.put("/**","user");
        filterFactoryBean.setFilterChainDefinitionMap(map);
        return filterFactoryBean;
    }

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public JsonResult doHandleShiroException(
            ShiroException e) {
        JsonResult r=new JsonResult();
        r.setState(0);
        if(e instanceof UnknownAccountException) {
            r.setMessage("账户不存在");
        }else if(e instanceof LockedAccountException) {
            r.setMessage("账户已被禁用");
        }else if(e instanceof IncorrectCredentialsException) {
            r.setMessage("密码不正确");
        }else if(e instanceof AuthorizationException) {
            r.setMessage("没有此操作权限");
        }else {
            r.setMessage("系统维护中");
        }
        e.printStackTrace();
        return r;
    }

    /**
     * 生命周期管理器
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor
    newLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     *配置代理对象创建器
     * @return
     */
    @DependsOn("lifecycleBeanPostProcessor")
    @Bean
    public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 配置advisor对象
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor
    newAuthorizationAttributeSourceAdvisor(
            @Autowired SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor=
                new AuthorizationAttributeSourceAdvisor();
        return advisor;
    }

    /**
     * 配置缓存Bean对象
     * @return
     */
    @Bean
    public CacheManager newCacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 记住我功能实现
     * @return
     */
    public SimpleCookie newCookie() {
        SimpleCookie c=new SimpleCookie("rememberMe");
        c.setMaxAge(10*60);
        return c;
    }

    public CookieRememberMeManager newRememberMeManager() {
        CookieRememberMeManager cManager=
                new CookieRememberMeManager();
        cManager.setCookie(newCookie());
        return cManager;
    }

    /**
     * 会话管理器配置
     * @return
     */
    public DefaultWebSessionManager newSessionManager() {
        DefaultWebSessionManager sManager=
                new DefaultWebSessionManager();
        sManager.setGlobalSessionTimeout(60*60*1000);
        return sManager;
    }
}
