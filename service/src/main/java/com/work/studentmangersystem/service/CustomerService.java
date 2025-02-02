package com.work.studentmangersystem.service;



import com.work.studentmangersystem.entity.Card;
import com.work.studentmangersystem.entity.Transaction;
import com.work.studentmangersystem.entity.User;
import com.work.studentmangersystem.entity.dao.CardDao;
import com.work.studentmangersystem.entity.vo.CardVo;
import com.work.studentmangersystem.entity.vo.TransactionVo;

import java.util.List;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/9 上午11:06
 */
public interface CustomerService {
    List<TransactionVo> showTransactions(String cno);
    CardVo showCard(String mobile);
    Boolean updateCard(Card card);
    int insertTransaction(Transaction transaction);

    int addApplyCard(CardDao cardDao);

    int addBindCard(User user);

    List<CardVo> showApplyedCard();
}
