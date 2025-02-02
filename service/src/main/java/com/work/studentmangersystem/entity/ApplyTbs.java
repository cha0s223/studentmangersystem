package com.work.studentmangersystem.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;

/**
 * 申请表 实体类。
 *
 * @author chaos
 * @since 2024-11-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("apply_tbs")
public class ApplyTbs implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 申请id
     */
    @Id
    private String id;

    /**
     * 类型
     */
    private String apType;

    /**
     * 申请数据
     */
    private String apData;

}
