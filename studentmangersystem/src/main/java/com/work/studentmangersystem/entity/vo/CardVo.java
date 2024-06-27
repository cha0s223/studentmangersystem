package com.work.studentmangersystem.entity.vo;

import lombok.Data;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/9 下午2:56
 */
@Data
public class CardVo {
    private String cardNo;

    private String cardPassword;

    private Float balance;

    /**
     * 0是正常，1是被冻结
     */
    private String cardState;
}
