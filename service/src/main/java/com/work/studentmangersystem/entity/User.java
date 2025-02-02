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
 * 用户表 实体类。
 *
 * @author chaos
 * @since 2024-06-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String mobile;

    private String userName;

    private String password;

    /**
     * 0是卡用户，1是卡业务员，2是系统管理员
     */
    private String role;

    private String cno;

}
