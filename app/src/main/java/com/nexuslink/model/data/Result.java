package com.nexuslink.model.data;

/**
 * Created by 猿人 on 2017/3/15.
 */

public class Result<T> {
    private int code;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
