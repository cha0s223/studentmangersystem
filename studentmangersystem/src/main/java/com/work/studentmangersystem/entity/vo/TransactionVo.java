package com.work.studentmangersystem.entity.vo;


import lombok.Data;

import java.sql.Timestamp;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/11 下午6:45
 */
@Data
public class TransactionVo {
    private String cno;
    private Timestamp tTime;

    private String cThing;

    private Float amount;
}
