package com.work.studentmangersystem.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/7 上午10:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {
    private String mobile;
    private String userName;
    private String password;
    private String role;
    private String cno;
}
