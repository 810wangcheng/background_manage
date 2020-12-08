package com.cy.aspect;

import com.cy.annotation.RequiredLog;
import com.cy.dao.SysLogDao;
import com.cy.entity.SysUser;
import com.cy.entity.SystemLog;
import com.cy.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Administrator
 */
@Aspect
@Service
@Slf4j
public class SysLogAspect {

    @Around("@annotation(com.cy.annotation.RequiredLog)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("开始记录日志");
        //开始时间
        Long t1 = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long t2 = System.currentTimeMillis();
        log.debug("方法执行时间："+(t2 - t1));
        saveObject(joinPoint,(t2 - t1));
        return result;
    }

    @Autowired
    private SysLogDao sysLogDao;

    private void saveObject(ProceedingJoinPoint joinPoint, long l) throws NoSuchMethodException {
        //获取用户信息
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //通过切入点获取签名
        Signature signature =  joinPoint.getSignature();
        log.debug("方法签名为："+signature);
        //将获取的签名转化为方法签名
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取目标类
        Class<?> target = joinPoint.getTarget().getClass();
        //通过目标类获取方法
        Method method = target.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        //获取方法上的注解
        RequiredLog requiredLog = method.getDeclaredAnnotation(RequiredLog.class);
        //获取操作信息
        String operation = requiredLog.value();

        //获取类名
        String clazzName = target.getName();
        //获取方法名
        String methodName = clazzName + "." + method.getName();
        //获取参数
        String args = Arrays.toString(joinPoint.getArgs());

        //保存日志信息
        SystemLog systemLog = new SystemLog();
        systemLog.setUsername(user.getUsername());
        systemLog.setMethod(methodName);
        systemLog.setOperation(operation);
        systemLog.setParams(args);
        systemLog.setIp(IpUtils.getIpAddr());
        systemLog.setTime(l);
        systemLog.setCreatedTime(new Date());
        //保存日志
        sysLogDao.insertSysLog(systemLog);
    }

}
