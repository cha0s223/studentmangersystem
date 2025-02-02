package com.work.studentmangersystem.controller;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.work.studentmangersystem.annotation.RecordLog;
import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.Transaction;
import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.*;
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

    private final CustomerService customerService;
    private final HttpServletRequest request;

    @Autowired
    public CustomerController(CustomerService customerService, HttpServletRequest request) {
        this.customerService = customerService;
        this.request = request;
    }

    @PostMapping("/transactionInfo")
    public ResultData showAllTransactions(@RequestBody JSONObject jsonObject) {
        String token = request.getHeader("token");
        CardVo cardVo = verifyCard(token,jsonObject.getString("password"));
        if (!Objects.isNull(cardVo)) {
            return ResultData.success(customerService.showTransactions(cardVo.getCardNo()));
        }
        return ResultData.fail("卡验证失败！");
    }

    @PostMapping("/cardInfo")
    public ResultData showMyCard(@RequestBody JSONObject jsonObject) {
        String token = request.getHeader("token");
        System.out.println(jsonObject.getString("password"));
        CardVo cardVo = verifyCard(token,jsonObject.getString("password"));
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
            return ResultData.success("修改成功！");
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

    @PostMapping("/addApplyCard")
    public ResultData addApplyCard(@RequestBody CardDao cardDao) {
        int i = customerService.addApplyCard(cardDao);
        return i>0? ResultData.success("申请成功"):ResultData.fail("申请失败");
    }

    @GetMapping("/showApplyedCard")
    public ResultData showApplyedCard(){
        return ResultData.success(customerService.showApplyedCard());
    }

    @PostMapping("/applyBindCard")
    public ResultData applyBindCard(@RequestBody String cardNo){
        String token = request.getHeader("token");
        String mobile = JwtUtil.verifyToken(token).get("mobile").asString();
        String password= JwtUtil.verifyToken(token).get("password").asString();
        cardNo=cardNo.substring(0,cardNo.length()-1);
        CardVo cardVo = verifyCard(token,password);
        if (Objects.isNull(cardVo)) {
            User user=new User();
            user.setMobile(mobile);
            user.setCno(cardNo);
            customerService.addBindCard(user);
            return ResultData.success("申请绑定成功");
        }
        return ResultData.fail("此用户已经有校园卡了");
    }

    private CardVo verifyCard(String token,String password) {
        Map<String, Claim> map = JwtUtil.verifyToken(token);
        String mobile = map.get("mobile").asString();
        CardVo cardVo = customerService.showCard(mobile);
        if (Objects.isNull(cardVo))
        {
            return null;
        }
        if (!Objects.equals(cardVo.getCardPassword(), password)) {
            return null;
        }
        return cardVo;
    }
}
