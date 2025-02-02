package com.work.studentmangersystem.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/10 下午7:50
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的url
        String url = request.getRequestURI().toString();
        log.info("请求的url：{}",url);

        String jwt = request.getHeader("token");

        //判断令牌是否存在
        if (!StringUtils.hasLength(jwt)){
            ResultData error = ResultData.fail("NOT_LOGIN");
            //手动转对象 json----->fastjson
            String notLogin = JSONObject.toJSONString(error);
            //直接响应给浏览器未登录
            response.getWriter().write(notLogin);
            return false;
        }

        //解析token，如果解析失败，返回错误结果（未登录）
        try {
            JwtUtil.verifyToken(jwt);
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("解析令牌错误，放回未登录信息");
            ResultData error = ResultData.fail("未登录!");
            String notLogin = JSONObject.toJSONString(error);

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(notLogin);
            return false;
        }

        //放行
        log.info("令牌合法，放行");
        return true;
    }

    @Override//目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("令牌验证");
    }

}
