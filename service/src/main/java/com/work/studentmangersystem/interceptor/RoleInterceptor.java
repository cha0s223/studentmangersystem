package com.work.studentmangersystem.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/12 下午1:49
 */
@Component
@Slf4j
public class RoleInterceptor implements HandlerInterceptor {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String token = request.getHeader("token");
        String role= JwtUtil.verifyToken(token).get("role").asString();
        if (pathMatcher.match("/manager/**", requestURI)&&role.equals("2")) {
            return true;
        }
        if (pathMatcher.match("/operator/**", requestURI)&&(Integer.parseInt(role)>=1)) {
            return true;
        }
        ResultData resultData=ResultData.fail("权限错误！");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(resultData));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("权限验证");
    }


}
