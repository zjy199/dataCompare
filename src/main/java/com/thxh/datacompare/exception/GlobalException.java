package com.thxh.datacompare.exception;

/**
 * @author YYY
 * @date 2021年12月23日19:25
 */
public class GlobalException extends Exception {

    private String message;

    public GlobalException() {
    }

    public GlobalException(String message) {
        this.message = message;
    }
}
