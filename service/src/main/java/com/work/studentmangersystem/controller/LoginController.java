package com.work.studentmangersystem.controller;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.LoginDao;
import com.work.studentmangersystem.entity.dao.UserDao;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.entity.vo.UserVo;
import com.work.studentmangersystem.service.UserService;
import com.work.studentmangersystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/10 下午7:37
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;


    @PostMapping
    public ResultData<String> login(@RequestBody LoginDao loginDao) {
        LoginDao loginuser = userService.userLogin(loginDao.getMobile());
        if (loginuser != null) {
            if (loginuser.getPassword().equals(loginDao.getPassword())&&loginuser.getRole().equals(loginDao.getRole()))
            {
                String jwt = JwtUtil.createToken(loginuser);
                return ResultData.success(jwt);
            }else {
                return ResultData.fail("用户信息错误！");
            }
        }
        return ResultData.fail("登录错误");
    }

    @PostMapping("/addApplyUser")
    public ResultData addApplyUser(@RequestBody UserDao userDao) {
        int i = userService.addApplyUser(userDao);
        return i>0? ResultData.success("申请成功"):ResultData.fail("申请失败");
    }

    @GetMapping("/auth")
    public ResultData<String> authJwt(){
        String jwt = request.getHeader("token");
        if (!StringUtils.hasLength(jwt)){

            return ResultData.fail("NOT_LOGIN");
        }
        Map<String, Claim> map;
        try {
            map = JwtUtil.verifyToken(jwt);
        } catch (JWTDecodeException j) {//jwt解析失败
            return ResultData.fail("jwt令牌错误！");
        }
        String mobile = map.get("mobile").asString();
        String password = map.get("password").asString();
        String role= map.get("role").asString();
        User user = userService.findById(mobile);
        if (user == null) {
            return ResultData.fail("没有该用户！");
        }
        if (!user.getPassword().equals(password)||!user.getRole().equals(role)) {
            return ResultData.fail("用户信息错误");
        }
        return ResultData.success("验证成功");
    }

}
