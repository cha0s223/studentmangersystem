package com.work.studentmangersystem.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.mybatisflex.core.query.QueryChain;

import com.mybatisflex.core.query.QueryWrapper;
import com.work.studentmangersystem.entity.ApplyTbs;
import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.Transaction;
import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.CardDao;
import com.work.studentmangersystem.entity.vo.CardVo;
import com.work.studentmangersystem.entity.vo.TransactionVo;
import com.work.studentmangersystem.mapper.ApplyTbsMapper;
import com.work.studentmangersystem.mapper.CardMapper;
import com.work.studentmangersystem.mapper.TransactionMapper;
import com.work.studentmangersystem.service.CustomerService;
import com.work.studentmangersystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.work.studentmangersystem.entity.table.ApplyTbsTableDef.APPLY_TBS;
import static com.work.studentmangersystem.entity.table.CardTableDef.CARD;
import static com.work.studentmangersystem.entity.table.TransactionTableDef.TRANSACTION;
import static com.work.studentmangersystem.entity.table.UserTableDef.USER;


/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/9 上午11:40
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private CardMapper cardMapper;
    private TransactionMapper transactionMapper;
    private ApplyTbsMapper applyTbsMapper;
    private HttpServletRequest request;

    @Autowired
    public CustomerServiceImpl( CardMapper cardMapper, TransactionMapper transactionMapper, ApplyTbsMapper applyTbsMapper,HttpServletRequest request) {
        this.cardMapper = cardMapper;
        this.transactionMapper = transactionMapper;
        this.applyTbsMapper = applyTbsMapper;
        this.request = request;
    }
    @Override
    public List<TransactionVo> showTransactions(String cno) {
        return QueryChain.of(transactionMapper)
                .select(TRANSACTION.ALL_COLUMNS)
                .from(TRANSACTION)
                .leftJoin(CARD).on(TRANSACTION.CNO.eq(CARD.CARD_NO))
                .where(CARD.CARD_NO.eq(cno))
                .listAs(TransactionVo.class);
    }

    @Override
    public CardVo showCard(String mobile) {
        return QueryChain.of(cardMapper)
                .select(USER.MOBILE,CARD.ALL_COLUMNS)
                .from(CARD)
                .leftJoin(USER).on(CARD.CARD_NO.eq(USER.CNO))
                .where(USER.MOBILE.eq(mobile))
                .listAs(CardVo.class).stream().findFirst().orElse(null);
    }

    @Override
    public Boolean updateCard(Card card) {
        cardMapper.update(card);
        return true;
    }

    @Override
    public int insertTransaction(Transaction transaction) {
        return transactionMapper.insert(transaction);
    }

    @Override
    public int addApplyCard(CardDao cardDao) {
        String token = request.getHeader("token");
        String mobile = JwtUtil.verifyToken(token).get("mobile").asString();
        return applyTbsMapper.insert(new ApplyTbs(mobile+"!"+DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()),"user", JSONObject.toJSONString(cardDao)));
    }

    @Override
    public int addBindCard(User user) {
        return applyTbsMapper.insert(new ApplyTbs(user.getMobile()+"!"+DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()),"bind", JSONObject.toJSONString(user)));
    }

    @Override
    public List<CardVo> showApplyedCard() {
        String token = request.getHeader("token");
        String mobile = JwtUtil.verifyToken(token).get("mobile").asString();
        QueryWrapper queryWrapper=QueryWrapper.create()
                .select(APPLY_TBS.ALL_COLUMNS)
                .from(APPLY_TBS)
                .where(APPLY_TBS.ID.likeLeft(mobile).and(APPLY_TBS.AP_TYPE.eq("userCheckcardCheck")));
        List<ApplyTbs> applyTbs = applyTbsMapper.selectListByQuery(queryWrapper);
        List<CardVo> cardVos=new ArrayList<>();
        applyTbs.forEach(data->{
            CardVo cardVo=JSONObject.parseObject(data.getApData(),CardVo.class);
            cardVo.setCardPassword("");
            cardVos.add(cardVo);
        });
        return cardVos;
    }
}
