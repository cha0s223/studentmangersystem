package com.work.studentmangersystem.service.impl;

import com.work.studentmangersystem.entity.SysOperLog;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.mapper.SysOperLogMapper;
import com.work.studentmangersystem.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/21 下午12:42
 */
@Service
public class IOperLogServiceImpl implements IOperLogService {

    @Autowired
    private SysOperLogMapper operLogMapper;

    @Override
    public void insertLog(SysOperLog operLog) {
        operLogMapper.insert(operLog);
    }

    @Override
    public ResultData<List<SysOperLog>> showLogs() {
        return ResultData.success(operLogMapper.selectAll());
    }
}
