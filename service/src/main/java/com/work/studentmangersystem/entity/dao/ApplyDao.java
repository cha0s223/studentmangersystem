package com.work.studentmangersystem.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/11/11 下午1:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyDao {
    private String id;
    private String apType;
    private String apData;
}
