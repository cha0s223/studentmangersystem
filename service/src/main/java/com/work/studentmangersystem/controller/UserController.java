package com.work.studentmangersystem.controller;


import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.work.studentmangersystem.annotation.RecordLog;
import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.LoginDao;
import com.work.studentmangersystem.entity.dao.PasswordDao;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.service.UserService;
import com.work.studentmangersystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/11 下午12:59
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/info")
    public ResultData showUser(@RequestBody JSONObject jsonObject){
        try
        {
            String token = request.getHeader("token");
            Map<String, Claim> map = JwtUtil.verifyToken(token);
            String password= jsonObject.getString("password");

            if (!password.equals(map.get("password").asString())){
                return ResultData.fail("密码错误!");
            }

            User user = userService.findById(map.get("mobile").asString());
            return ResultData.success(user);
        }catch (Exception e){
            log.error( e.getMessage(),e);
            return ResultData.fail("令牌错误！");
        }
    }

    @PutMapping("/mfpwd")
    @RecordLog
    public ResultData modifyPassword(@RequestBody PasswordDao passwordDao){
        if (!passwordDao.getPassword().equals(passwordDao.getPassword2())){
            return ResultData.fail("两次密码不一样");
        }
        try {
            String token = request.getHeader("token");
            Map<String, Claim> map = JwtUtil.verifyToken(token);
            if (!passwordDao.getOldPWD().equals(map.get("password").asString())){
                return ResultData.fail("密码错误!");
            }
            userService.modifyPWD(map.get("mobile").asString(),passwordDao.getPassword());
            User user = userService.findById(map.get("mobile").asString());
            token = JwtUtil.createToken(new LoginDao(user.getMobile(), user.getPassword(), user.getRole()));
            return ResultData.success(token);
        }catch (Exception e){
            log.error( e.getMessage(),e);
            return ResultData.fail("令牌错误");
        }
    }
}
