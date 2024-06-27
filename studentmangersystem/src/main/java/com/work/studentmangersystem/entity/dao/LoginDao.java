package com.work.studentmangersystem.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/6 下午12:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDao {
    private String mobile;
    private String password;
    private String role;
}
