package com.work.studentmangersystem.service;

import com.work.studentmangersystem.entity.SysOperLog;
import com.work.studentmangersystem.entity.resp.ResultData;

import java.util.List;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/21 下午12:41
 */
public interface IOperLogService {
    void insertLog(SysOperLog operLog);

    ResultData<List<SysOperLog>> showLogs();
}
