package com.work.studentmangersystem.service;

import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.LoginDao;
import com.work.studentmangersystem.entity.dao.UserDao;
import com.work.studentmangersystem.entity.vo.UserVo;

import java.util.List;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/10 下午8:00
 */
public interface UserService {
    LoginDao userLogin(String mobile);
    User findById(String id);

    List<UserVo> findAll();

    int addUser(UserDao userDao);


    int deleteUser(String id);

    Boolean modifyPWD(String mobile, String pwd);

    int updateUser(UserDao userDao);
}
