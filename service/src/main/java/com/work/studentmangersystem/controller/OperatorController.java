package com.work.studentmangersystem.controller;


import com.work.studentmangersystem.annotation.RecordLog;
import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.dao.ApplyDao;
import com.work.studentmangersystem.entity.dao.CardDao;
import com.work.studentmangersystem.entity.dao.DateDao;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.entity.vo.ApplyVo;
import com.work.studentmangersystem.entity.vo.CardVo;
import com.work.studentmangersystem.entity.vo.TransactionVo;
import com.work.studentmangersystem.service.CustomerService;
import com.work.studentmangersystem.service.OperatorService;
import com.work.studentmangersystem.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/6 下午4:07
 */
@RestController
@RequestMapping("/operator")
@Slf4j
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/info")
    public ResultData<List<CardVo>> showCard(){
        List<CardVo> cards = operatorService.findAll();
        return ResultData.success(cards);
    }

    @PostMapping("/add")
    @RecordLog
    public ResultData addCard(@RequestBody CardDao cardDao){
        Card card = new Card();
        BeanUtils.copyProperties(cardDao, card);
        operatorService.insert(card);
        return ResultData.success("添加成功!");
    }

    @PutMapping("/update")
    @RecordLog
    public ResultData updateCard(@RequestBody CardDao cardDao){
        Card card = new Card();
        BeanUtils.copyProperties(cardDao, card);
        operatorService.update(card);
        return ResultData.success("修改成功");
    }

    @DeleteMapping("/delete/{cno}")
    @RecordLog
    public ResultData deleteCardById(@PathVariable("cno") String cno){
        List<TransactionVo> transactionVos = customerService.showTransactions(cno);
        if(!transactionVos.isEmpty()){
            for(TransactionVo transactionVo : transactionVos){
                redisUtil.del(transactionVo.getCno()+transactionVo.getTTime());
            }
        }
        int i = operatorService.deleteCardById(cno);
        log.info("成功删除{}条记录!",i);
        return ResultData.success("删除成功!");
    }

    @GetMapping("/getAllTransactions")
    public ResultData findAllTransactions(){
        List<TransactionVo> transactionVos = operatorService.selectAllTransaction();
        return ResultData.success(transactionVos);
    }

    @PostMapping("/findTransaction")
    public ResultData findTransaction(DateDao dateDao){
        Long start=System.currentTimeMillis();
        TransactionVo transactionVo = (TransactionVo) redisUtil.get(dateDao.getCno() + dateDao.getDate());
        if (transactionVo == null){
            transactionVo = operatorService.findTransaction(dateDao);
            if (transactionVo == null){
                return ResultData.success(null);
            }else {
                redisUtil.set(dateDao.getCno() + dateDao.getDate(), transactionVo);
                Long end=System.currentTimeMillis();
                log.info("花费时间{}",start-end);
                return ResultData.success(transactionVo);
            }
        }else {
            Long end=System.currentTimeMillis();
            log.info("花费时间{}",start-end);
            return ResultData.success(transactionVo);
        }
    }

    @GetMapping("/getApplyCard")
    public ResultData<List<ApplyVo>> showApplyCard(){
        return ResultData.success(operatorService.showApply());
    }

    @PostMapping("/updateApplyCard")
    public ResultData updateApplyCard(@RequestBody ApplyDao applyDao){
        return ResultData.success(operatorService.upDateApplyCard(applyDao));
    }

    @DeleteMapping("/deleteApplyCardById/{id}")
    public ResultData deleteApplyCard(@PathVariable("id") String id){
        return ResultData.success(operatorService.deleteApplyCardById(id));
    }
}
