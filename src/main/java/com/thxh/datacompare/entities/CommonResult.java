package com.thxh.datacompare.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YYY
 * @date 2022年01月18日16:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;
    private String message;
    private T data;
    private Integer total;

    public CommonResult(Integer code,String message){
        this.code = code;
        this.message = message;
        this.data = null;
        this.total = 0;
    }
}
