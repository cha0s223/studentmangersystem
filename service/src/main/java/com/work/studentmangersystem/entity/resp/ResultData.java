package com.work.studentmangersystem.entity.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName studentmangersystem
 * @Description
 * @Author chaos
 * @Date 2024/6/10 下午7:41
 */
@Data
@NoArgsConstructor
public class ResultData<E> {
    private int code;
    private String msg;
    private E data;
    private Long timestamp;
    public static <E> ResultData<E> success(E data) {
        ResultData<E> resultData=new ResultData<E>();
        resultData.setCode(0);
        resultData.setMsg("success");
        resultData.setData(data);
        resultData.setTimestamp(System.currentTimeMillis());
        return resultData;
    }

    public static <E> ResultData<E> fail(String msg) {
        ResultData<E> resultData=new ResultData<E>();
        resultData.setCode(1);
        resultData.setMsg(msg);
        resultData.setTimestamp(System.currentTimeMillis());
        return resultData;
    }
}
