package com.work.studentmangersystem.controller;

import com.auth0.jwt.interfaces.Claim;
import com.work.studentmangersystem.annotation.RecordLog;
import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.Transaction;
import com.work.studentmangersystem.entity.dao.ConsumeDao;
import com.work.studentmangersystem.entity.dao.RechargeDao;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.entity.vo.CardVo;
import com.work.studentmangersystem.service.CustomerService;
import com.work.studentmangersystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;


/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/11 下午4:11
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    private HttpServletRequest request;

    @Autowired
    public CustomerController(CustomerService customerService, HttpServletRequest request) {
        this.customerService = customerService;
        this.request = request;
    }

    @PostMapping("/transactionInfo")
    public ResultData showAllTransactions(String password) {
        String token = request.getHeader("token");
        CardVo cardVo = verifyCard(token,password);
        if (!Objects.isNull(cardVo)) {
            return ResultData.success(customerService.showTransactions(cardVo.getCardNo()));
        }
        return ResultData.fail("卡验证失败！");
    }

    @PostMapping("/cardInfo")
    public ResultData showMyCard(String password) {
        String token = request.getHeader("token");
        CardVo cardVo = verifyCard(token,password);
        if (Objects.isNull(cardVo)) {
            return ResultData.fail("卡验证失败！");
        }
        return ResultData.success(cardVo);
    }

    @PutMapping("/recharge")
    @Transactional
    @RecordLog
    public ResultData rechargeCard(@RequestBody RechargeDao amountDao) {
        String token = request.getHeader("token");
        CardVo cardVo = verifyCard(token,amountDao.getPassword());
        if (Objects.isNull(cardVo)) {
            return ResultData.fail("账户未绑定卡！");
        }
        if (!cardVo.getCardPassword().equals(amountDao.getPassword())) {
            return ResultData.fail("卡密码错误！");
        }
        if (!cardVo.getCardState().equals("1"))
        {
            Card card = new Card(cardVo.getCardNo(),cardVo.getCardPassword(),cardVo.getBalance()+amountDao.getAmount(),cardVo.getCardState());
            customerService.updateCard(card);
            Transaction transaction = new Transaction(card.getCardNo(), new Timestamp(System.currentTimeMillis()), "充值", amountDao.getAmount());
            customerService.insertTransaction(transaction);
            return ResultData.fail("修改成功！");
        }
        return ResultData.fail("卡已被挂失！无法操作");
    }

    @PutMapping("/consume")
    @Transactional
    @RecordLog
    public ResultData consumeCard(@RequestBody ConsumeDao amountDao) {
        String token = request.getHeader("token");
        CardVo cardVo = verifyCard(token,amountDao.getPassword());
        if (Objects.isNull(cardVo)) {
            return ResultData.fail("该账户未绑定账户！");
        }
        if (!cardVo.getCardPassword().equals(amountDao.getPassword())) {
            return ResultData.fail("卡密码错误！");
        }
        if (cardVo.getBalance()<amountDao.getCamount()){
            return ResultData.fail("余额不足,请及时充值！");
        }
        if (!cardVo.getCardState().equals("1")) {
            Card card = new Card(cardVo.getCardNo(), cardVo.getCardPassword(), cardVo.getBalance() - amountDao.getCamount(), cardVo.getCardState());
            customerService.updateCard(card);
            Transaction transaction = new Transaction(card.getCardNo(), new Timestamp(System.currentTimeMillis()), amountDao.getCobject(), amountDao.getCamount());
            customerService.insertTransaction(transaction);
            return ResultData.success("添加成功");
        }
        return ResultData.fail("卡已被挂失！无法操作");
    }

    private CardVo verifyCard(String token,String password) {
        Map<String, Claim> map = JwtUtil.verifyToken(token);
        String mobile = map.get("mobile").asString();
        CardVo cardVo = customerService.showCard(mobile);
        if (!Objects.equals(cardVo.getCardPassword(), password)){
            return null;
        }
        return cardVo;
    }
}
