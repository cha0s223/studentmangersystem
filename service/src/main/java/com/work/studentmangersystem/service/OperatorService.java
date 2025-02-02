package com.work.studentmangersystem.service;


import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.dao.ApplyDao;
import com.work.studentmangersystem.entity.dao.DateDao;
import com.work.studentmangersystem.entity.vo.ApplyVo;
import com.work.studentmangersystem.entity.vo.CardVo;
import com.work.studentmangersystem.entity.vo.TransactionVo;

import java.util.List;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/9 上午10:54
 */
public interface OperatorService {
    List<CardVo> findAll();
    int insert(Card card);
    int update(Card card);

    List<TransactionVo> selectAllTransaction();
    TransactionVo findTransaction(DateDao dateDao);

    int deleteCardById(String cno);

    List<ApplyVo> showApply();

    int deleteApplyCardById(String id);

    int upDateApplyCard(ApplyDao applyDao);
}
