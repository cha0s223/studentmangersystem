package com.work.studentmangersystem.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.work.studentmangersystem.entity.ApplyTbs;
import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.dao.ApplyDao;
import com.work.studentmangersystem.entity.dao.DateDao;
import com.work.studentmangersystem.entity.vo.ApplyVo;
import com.work.studentmangersystem.entity.vo.CardVo;
import com.work.studentmangersystem.entity.vo.TransactionVo;
import com.work.studentmangersystem.mapper.ApplyTbsMapper;
import com.work.studentmangersystem.mapper.CardMapper;
import com.work.studentmangersystem.mapper.TransactionMapper;
import com.work.studentmangersystem.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.work.studentmangersystem.entity.table.ApplyTbsTableDef.APPLY_TBS;
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

    @Autowired
    private ApplyTbsMapper applyTbsMapper;

    @Override
    public List<CardVo> findAll() {
        List<CardVo> cardVos = QueryChain.of(cardMapper)
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
        queryWrapper = QueryWrapper.create()
                .select(CARD.ALL_COLUMNS)
                .from(CARD)
                .where(CARD.CARD_NO.eq(cno));
        return cardMapper.deleteByQuery(queryWrapper);
    }

    @Override
    public List<ApplyVo> showApply() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(APPLY_TBS.ALL_COLUMNS)
                .from(APPLY_TBS)
                .where(APPLY_TBS.AP_TYPE.eq("card").or(APPLY_TBS.AP_TYPE.eq("userCheckcard")).or(APPLY_TBS.AP_TYPE.eq("bind")));
        List<ApplyTbs> applyTbs = applyTbsMapper.selectListByQuery(queryWrapper);

        List<ApplyVo> applyVos = new ArrayList<>();
        applyTbs.forEach(data -> {
            applyVos.add(new ApplyVo(
                    data.getId(),
                    data.getApType(),
                    data.getApData()
            ));
        });
        return applyVos;
    }

    @Override
    public int deleteApplyCardById(String id) {
        return applyTbsMapper.deleteById(id);
    }

    @Override
    public int upDateApplyCard(ApplyDao applyDao) {
        if (Objects.equals(applyDao.getApType(), "userCheckcardCheck")) {
            Card card = JSONObject.parseObject(applyDao.getApData(), Card.class);
            cardMapper.insert(card);
        }
        return applyTbsMapper.update(new ApplyTbs(
                applyDao.getId(),
                applyDao.getApType(),
                applyDao.getApData()
        ));
    }
}
