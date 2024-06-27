package com.work.studentmangersystem.entity.dao;

import lombok.Data;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/9 上午11:03
 */
@Data
public class CardDao {
    private String cardNo;
    private String cardPassword;
    private Float balance;
    private String cardState;
}
