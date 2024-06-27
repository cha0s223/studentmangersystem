package com.work.studentmangersystem;

import com.work.studentmangersystem.entity.dao.DateDao;
import com.work.studentmangersystem.entity.dao.UserDao;
import com.work.studentmangersystem.entity.vo.TransactionVo;
import com.work.studentmangersystem.service.OperatorService;
import com.work.studentmangersystem.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class StudentmangersystemApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OperatorService operatorService;

    @Test
    void contextLoads() {
    }

    @Test
    void testRedis() {
        String key="k1";
        String value="v1";
        redisUtil.set(key,value);
        Object k1 = redisUtil.get("k1");
        log.info(k1.toString());
    }

    @Test
    void testDelete(){
        String cno="ruike5dai";
        String date="2024-05-12 13:47:47";
        redisUtil.del(cno+date);
    }

    @Test
    void testSelect(){
        String cno="ruike5dai";
        String date="2024-05-12 13:47:47";
        DateDao dateDao=new DateDao();
        dateDao.setCno(cno);
        dateDao.setDate(Timestamp.valueOf(date));
        TransactionVo transaction = operatorService.findTransaction(dateDao);
        redisUtil.set(cno+date,transaction);
        System.out.println(transaction);
        Object o = redisUtil.get(cno + date);
        o.toString();
    }

    @Test
    void testTime(){
        System.out.println(Timestamp.valueOf(LocalDateTime.now()));
    }

}
