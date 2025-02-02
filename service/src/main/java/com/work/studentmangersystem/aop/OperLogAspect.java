package com.work.studentmangersystem.aop;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.work.studentmangersystem.entity.SysOperLog;
import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.service.IOperLogService;
import com.work.studentmangersystem.service.OperatorService;
import com.work.studentmangersystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/21 下午12:40
 */
@Component
@Aspect
public class OperLogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IOperLogService iOperLogService;

    @Pointcut("@annotation(com.work.studentmangersystem.annotation.RecordLog)")
    public void operLogPoinCut() {
    }

    @Pointcut("execution(* com.work.studentmangersystem.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
    }

    @Around(value = "operLogPoinCut()")
    public Object saveOperLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String jwt=request.getHeader("token");
        Map<String, Claim> map = JwtUtil.verifyToken(jwt);
        SysOperLog sysOperLog=new SysOperLog();
        sysOperLog.setOperId(map.get("mobile").asString());
        sysOperLog.setOperRole(Integer.parseInt(map.get("role").asString())+1);
        sysOperLog.setOperTime(Timestamp.valueOf(LocalDateTime.now()));
        sysOperLog.setMethod(proceedingJoinPoint.getSignature().getName());
        sysOperLog.setClassName(proceedingJoinPoint.getTarget().getClass().getName());
        sysOperLog.setRequestUrl(request.getRequestURI());
        sysOperLog.setRequestMethod(request.getMethod());
        sysOperLog.setRequestParam(argsArrayToString(proceedingJoinPoint.getArgs()));
        sysOperLog.setStatus(0);
        long begin = System.currentTimeMillis();
        //原始方法运行
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        sysOperLog.setTakeTime(end - begin);
        sysOperLog.setResponseResult(JSONObject.toJSONString(result));
        iOperLogService.insertLog(sysOperLog);
        return result;
    }

    @AfterThrowing(value = "operExceptionLogPoinCut()",throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e){
        String jwt=request.getHeader("token");
        Map<String, Claim> map = JwtUtil.verifyToken(jwt);
        SysOperLog sysOperLog=new SysOperLog();
        sysOperLog.setOperId(map.get("mobile").asString());
        sysOperLog.setOperRole(Integer.parseInt(map.get("role").asString())+1);
        sysOperLog.setOperTime(Timestamp.valueOf(LocalDateTime.now()));
        sysOperLog.setMethod(joinPoint.getSignature().getName());
        sysOperLog.setClassName(joinPoint.getTarget().getClass().getName());
        sysOperLog.setRequestUrl(request.getRequestURI());
        sysOperLog.setRequestMethod(request.getMethod());
        sysOperLog.setRequestParam(argsArrayToString(joinPoint.getArgs()));
        sysOperLog.setStatus(1);
        sysOperLog.setErrorMsg(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
        iOperLogService.insertLog(sysOperLog);
    }

    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (Object o : paramsArray)
            {
                if (o != null)
                {
                    try
                    {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.trim();
    }
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        message = substring(message,0 ,2000);
        return message;
    }
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }
            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }
                return str.substring(start, end);
            }
        }
    }


}
