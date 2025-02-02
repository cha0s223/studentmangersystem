package com.work.studentmangersystem.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/9 下午1:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String mobile;
    private String userName;
    private String password;
    private String role;
    private String cno;
}