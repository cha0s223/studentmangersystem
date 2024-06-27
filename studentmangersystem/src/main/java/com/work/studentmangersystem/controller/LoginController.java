package com.work.studentmangersystem.controller;

import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.LoginDao;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.service.UserService;
import com.work.studentmangersystem.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResultData<String> login(@RequestBody LoginDao loginDao) {
        LoginDao loginuser = userService.userLogin(loginDao.getMobile());
        if (loginuser != null) {
            String jwt= JwtUtil.createToken(loginDao);
            return ResultData.success(jwt);
        }
        return ResultData.fail("登录错误");
    }
}
