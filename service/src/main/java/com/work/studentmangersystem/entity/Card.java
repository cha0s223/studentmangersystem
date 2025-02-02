package com.work.studentmangersystem.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 校园卡表 实体类。
 *
 * @author chaos
 * @since 2024-06-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("card")
public class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String cardNo;

    private String cardPassword;

    private Float balance;

    /**
     * 0是正常，1是被冻结
     */
    private String cardState;

}
