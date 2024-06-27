package com.work.studentmangersystem.service.impl;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.LoginDao;
import com.work.studentmangersystem.entity.dao.UserDao;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.entity.vo.UserVo;
import com.work.studentmangersystem.mapper.CardMapper;
import com.work.studentmangersystem.mapper.TransactionMapper;
import com.work.studentmangersystem.mapper.UserMapper;
import com.work.studentmangersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.work.studentmangersystem.entity.table.CardTableDef.CARD;
import static com.work.studentmangersystem.entity.table.TransactionTableDef.TRANSACTION;
import static com.work.studentmangersystem.entity.table.UserTableDef.USER;


/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/10 下午8:03
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public LoginDao userLogin(String mobile) {
        User user = userMapper.selectOneById(mobile);
        return new LoginDao( user.getMobile(),user.getPassword(),user.getRole());
    }

    @Override
    public User findById(String mobile) {
        return userMapper.selectOneById(mobile);
    }

    @Override
    public List<UserVo> findAll() {
        QueryWrapper queryWrapper=QueryWrapper.create()
                .select(USER.ALL_COLUMNS)
                .from(USER);
        return userMapper.selectListByQueryAs(queryWrapper,UserVo.class);
    }

    @Override
    public int addUser(UserDao userDao) {
        User user = new User(userDao.getMobile(),
                userDao.getUserName(),
                userDao.getPassword(),
                userDao.getRole(),
                userDao.getCno());
        return userMapper.insert(user);
    }

    @Override
    public int deleteUser(String mobile) {
        QueryWrapper queryWrapper=QueryWrapper.create()
                .select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.MOBILE.eq(mobile));
        User user = userMapper.selectOneByQuery(queryWrapper);
        if (user.getCno()==null) {
            return userMapper.deleteById(mobile);
        }
        queryWrapper=QueryWrapper.create()
                        .select(TRANSACTION.ALL_COLUMNS)
                                .from(TRANSACTION)
                                        .where(TRANSACTION.CNO.eq(user.getCno()));
        transactionMapper.deleteByQuery(queryWrapper);
        userMapper.deleteById(mobile);
        return cardMapper.deleteById(user.getCno());
    }

    @Override
    public Boolean modifyPWD(String mobile, String pwd) {
        User user = UpdateEntity.of(User.class, mobile);
        user.setPassword(pwd);
        userMapper.update(user);
        return true;
    }

    @Override
    public int updateUser(UserDao userDao) {
        User user=new User();
        user.setMobile(userDao.getMobile());
        user.setUserName(userDao.getUserName());
        user.setPassword(userDao.getPassword());
        user.setRole(userDao.getRole());
        user.setCno(userDao.getCno());
        return userMapper.update(user);
    }


}
