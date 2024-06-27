package com.work.studentmangersystem.service.impl;

import com.mybatisflex.core.query.QueryChain;

import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.Transaction;
import com.work.studentmangersystem.entity.vo.CardVo;
import com.work.studentmangersystem.entity.vo.TransactionVo;
import com.work.studentmangersystem.mapper.CardMapper;
import com.work.studentmangersystem.mapper.TransactionMapper;
import com.work.studentmangersystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    public CustomerServiceImpl( CardMapper cardMapper, TransactionMapper transactionMapper) {
        this.cardMapper = cardMapper;
        this.transactionMapper = transactionMapper;
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


}
