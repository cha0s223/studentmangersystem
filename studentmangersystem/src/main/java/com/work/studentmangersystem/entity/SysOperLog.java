package com.work.studentmangersystem.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;

/**
 * 操作日志记录 实体类。
 *
 * @author chaos
 * @since 2024-06-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_oper_log")
public class SysOperLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志序号
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 操作员id
     */
    private String operId;

    /**
     * 操作员权限
     */
    private Integer operRole;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求类名
     */
    private String className;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 0是正常，1是错误
     */
    private Integer status;

    private String errorMsg;

    /**
     * 响应数据
     */
    private String responseResult;

    /**
     * 操作时间
     */
    private Timestamp operTime;

    /**
     * 方法执行耗时（单位：毫秒）
     */
    private Long takeTime;

}
