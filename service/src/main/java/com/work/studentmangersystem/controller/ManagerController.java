package com.work.studentmangersystem.controller;

import com.auth0.jwt.interfaces.Claim;
import com.work.studentmangersystem.annotation.RecordLog;
import com.work.studentmangersystem.entity.SysOperLog;
import com.work.studentmangersystem.entity.dao.ApplyDao;
import com.work.studentmangersystem.entity.dao.UserDao;
import com.work.studentmangersystem.entity.resp.ResultData;
import com.work.studentmangersystem.entity.vo.ApplyVo;
import com.work.studentmangersystem.entity.vo.UserVo;
import com.work.studentmangersystem.service.IOperLogService;
import com.work.studentmangersystem.service.UserService;
import com.work.studentmangersystem.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/11 下午12:30
 */
@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IOperLogService iOperLogService;


    @GetMapping("/info")
    public ResultData<List<UserVo>> showUsersInfo(){
        return ResultData.success(userService.findAll());
    }

    @PostMapping("/add")
    @RecordLog
    public ResultData addUser(@RequestBody UserDao userDao){
        int i= userService.addUser(userDao);
        log.info("成功插入{}条记录",i);
        return ResultData.success("用户添加成功!");
    }

    @PutMapping("/update")
    @RecordLog
    public ResultData updateUser(@RequestBody UserDao userDao){
        int i=userService.updateUser(userDao);
        log.info("成功修改{}条记录",i);
        return ResultData.success("用户修改成功");
    }


    @DeleteMapping("/delete/{mobile}")
    @Transactional
    @RecordLog
    public ResultData deleteUserById(@PathVariable(value = "mobile") String mobile){
        String token=request.getHeader("token");
        if (mobile.equals("admin")){
            return ResultData.fail("不能删除admin管理员");
        }
        Map<String, Claim> map = JwtUtil.verifyToken(token);
        String mobile1 = map.get("mobile").asString();
        if (mobile1.equals(mobile)){
            return ResultData.fail("不能删除当前用户！");
        }
        int i= userService.deleteUser(mobile);

        log.info("成功删除{}条记录",i);
        return ResultData.success("成功删除成员!");
    }

    @GetMapping("/cardsNo")
    public ResultData<List<String>> showCardsNo(){
        List<String> cardsNo= userService.showCardsNo();
        return ResultData.success(cardsNo);
    }


    @GetMapping("/logs")
    public ResultData<List<SysOperLog>> showLogs(){
        return iOperLogService.showLogs();
    }

    @GetMapping("/getApplyUser")
    public ResultData<List<ApplyVo>> showApplyUser(){
        return ResultData.success(userService.showApply());
    }

    @PostMapping("/updateApplyUser")
    public ResultData updateApplyUser(@RequestBody ApplyDao applyDao){
        return ResultData.success(userService.updateApplyUser(applyDao));
    }

    @DeleteMapping("/deleteApplyUserById/{id}")
    public ResultData deleteApplyCard(@PathVariable("id") String id){
        return ResultData.success(userService.deleteApplyUserById(id));
    }
}
