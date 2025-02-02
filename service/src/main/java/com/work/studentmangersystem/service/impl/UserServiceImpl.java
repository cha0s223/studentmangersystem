package com.work.studentmangersystem.service.impl;


import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.work.studentmangersystem.entity.ApplyTbs;
import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.ApplyDao;
import com.work.studentmangersystem.entity.dao.LoginDao;
import com.work.studentmangersystem.entity.dao.UserDao;
import com.work.studentmangersystem.entity.vo.ApplyVo;
import com.work.studentmangersystem.entity.vo.UserVo;
import com.work.studentmangersystem.mapper.ApplyTbsMapper;
import com.work.studentmangersystem.mapper.CardMapper;
import com.work.studentmangersystem.mapper.TransactionMapper;
import com.work.studentmangersystem.mapper.UserMapper;
import com.work.studentmangersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.work.studentmangersystem.entity.table.ApplyTbsTableDef.APPLY_TBS;
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

    @Autowired
    private ApplyTbsMapper applyTbsMapper;

    @Override
    public LoginDao userLogin(String mobile) {
        User user = userMapper.selectOneById(mobile);
        return new LoginDao(user.getMobile(), user.getPassword(), user.getRole());
    }

    @Override
    public User findById(String mobile) {
        return userMapper.selectOneById(mobile);
    }

    @Override
    public List<UserVo> findAll() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(USER.ALL_COLUMNS)
                .from(USER);
        return userMapper.selectListByQueryAs(queryWrapper, UserVo.class);
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
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.MOBILE.eq(mobile));
        User user = userMapper.selectOneByQuery(queryWrapper);
        if (user.getCno() == null) {
            return userMapper.deleteById(mobile);
        }
        queryWrapper = QueryWrapper.create()
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
        User user = new User();
        user.setMobile(userDao.getMobile());
        user.setUserName(userDao.getUserName());
        user.setPassword(userDao.getPassword());
        user.setRole(userDao.getRole());
        user.setCno(userDao.getCno());
        if (userDao.getCno() == null) {
            User user1 = UpdateEntity.of(User.class, userDao.getMobile());
            user1.setCno(null);
            userMapper.update(user1);
        }
//        boolean update = UpdateChain.of(User.class)
//                .set(User::getUserName, user.getUserName())
//                .setRaw(User::getPassword, user.getPassword())
//                .setRaw(User::getRole, user.getRole())
//                .setRaw(User::getCno, user.getCno())
//                .where(User::getMobile).eq(user.getMobile())
//                .update();
        return userMapper.update(user);
    }

    @Override
    public List<String> showCardsNo() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(CARD.CARD_NO)
                .from(CARD)
                .leftJoin(USER).on(CARD.CARD_NO.eq(USER.CNO))
                .where(USER.CNO.isNull());
        return cardMapper.selectListByQueryAs(queryWrapper, String.class);
    }

    @Override
    public List<ApplyVo> showApply() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(APPLY_TBS.ALL_COLUMNS)
                .from(APPLY_TBS)
                .where(APPLY_TBS.AP_TYPE.eq("user").or(APPLY_TBS.AP_TYPE.eq("cardCheckuser")).or(APPLY_TBS.AP_TYPE.eq("bindCheckuser")));
        List<ApplyTbs> applyTbs = applyTbsMapper.selectListByQuery(queryWrapper);
        List<ApplyVo> applyUserVos = new ArrayList<>();
        applyTbs.forEach(data -> {
            applyUserVos.add(new ApplyVo(
                    data.getId(),
                    data.getApType(),
                    data.getApData()
                    ));
        });
        return applyUserVos;
    }

    @Override
    public int deleteApplyUserById(String id) {
        return applyTbsMapper.deleteById(id);
    }

    @Override
    public int addApplyUser(UserDao userDao) {
        return applyTbsMapper.insert(new ApplyTbs(LocalDateTime.now().toString(), "card", JSONObject.toJSONString(userDao)));
    }

    @Override
    public int updateApplyUser(ApplyDao applyDao) {
        if (applyDao.getApType().equals("cardCheckuserCheck")){
            User user = JSONObject.parseObject(applyDao.getApData(), User.class);
            userMapper.insert(user);
        }
        if (applyDao.getApType().equals("bindCheckuserCheck")){
            User user = JSONObject.parseObject(applyDao.getApData(), User.class);
            userMapper.update(user);
        }
        return applyTbsMapper.update(new ApplyTbs(
                applyDao.getId(),
                applyDao.getApType(),
                applyDao.getApData()
                )
        );
    }
}
