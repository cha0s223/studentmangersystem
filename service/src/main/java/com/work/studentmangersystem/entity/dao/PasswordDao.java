package com.work.studentmangersystem.entity.dao;

import lombok.Data;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/9 上午11:16
 */
@Data
public class PasswordDao {
    private String oldPWD;
    private String password;
    private String password2;

}
