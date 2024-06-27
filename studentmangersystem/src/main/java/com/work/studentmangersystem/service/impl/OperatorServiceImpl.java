package com.work.studentmangersystem.service.impl;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.dao.DateDao;
import com.work.studentmangersystem.entity.vo.CardVo;
import com.work.studentmangersystem.entity.vo.TransactionVo;
import com.work.studentmangersystem.mapper.CardMapper;
import com.work.studentmangersystem.mapper.TransactionMapper;
import com.work.studentmangersystem.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.work.studentmangersystem.entity.table.CardTableDef.CARD;
import static com.work.studentmangersystem.entity.table.TransactionTableDef.TRANSACTION;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/9 上午10:56
 */
@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public List<CardVo> findAll() {
        List<CardVo> cardVos= QueryChain.of(cardMapper)
                .select(CARD.ALL_COLUMNS)
                .from(CARD)
                .listAs(CardVo.class);
        return cardVos;
    }

    @Override
    public int insert(Card card) {
        return cardMapper.insert(card);
    }

    @Override
    public int update(Card card) {
        return cardMapper.update(card);
    }

    @Override
    public List<TransactionVo> selectAllTransaction() {
        return QueryChain.of(transactionMapper)
                        .select(TRANSACTION.ALL_COLUMNS)
                                .from(TRANSACTION)
                                        .listAs(TransactionVo.class);
    }

    @Override
    public TransactionVo findTransaction(DateDao dateDao) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(TRANSACTION.ALL_COLUMNS)
                .from(TRANSACTION)
                .where(TRANSACTION.CNO.eq(dateDao.getCno()).and(TRANSACTION.T_TIME.eq(dateDao.getDate())));
        return transactionMapper.selectOneByQueryAs(queryWrapper, TransactionVo.class);
    }

    @Override
    @Transactional
    public int deleteCardById(String cno) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                        .select(TRANSACTION.ALL_COLUMNS)
                                .from(TRANSACTION)
                                        .where(TRANSACTION.CNO.eq(cno));
        transactionMapper.deleteByQuery(queryWrapper);
        queryWrapper=QueryWrapper.create()
                .select(CARD.ALL_COLUMNS)
                .from(CARD)
                .where(CARD.CARD_NO.eq(cno));
        return cardMapper.deleteByQuery(queryWrapper);
    }
}
