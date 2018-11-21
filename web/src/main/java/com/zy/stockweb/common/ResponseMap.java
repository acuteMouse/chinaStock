package com.zy.stockweb.common;

import java.io.Serializable;

/**
 * Created by cgl
 * 返回到前台页面的对象
 */
public class ResponseMap implements Serializable {
    private Integer flag = Integer.valueOf(0);
    private String message;
    private Object result;

    public ResponseMap() {
    }

    public ResponseMap(Integer flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public ResponseMap(Integer flag, Object result) {
        this.flag = flag;
        this.result = result;
    }


    /**
     * 返回结果（1代表成功操作，0未成功）
     *
     * @return
     */
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 提示信息
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 返回数据
     *
     * @return
     */
    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }



    /**
     * 设置请求成功时返回数据
     *
     * @param result
     */
    public void setReturnSuccess(Object result, String message) {
        this.flag = 1;
        this.result = result;
        this.message = message;
    }

    /**
     * 设置请求成功时返回数据
     *
     * @param result
     */
    public void setReturnSuccess(Object result) {
        this.flag = 1;
        this.result = result;
    }

    /**
     * 设置返回失败时返回数据
     *
     * @param message
     */
    public void setReturnFail(String message) {
        this.flag = 0;
        this.message = message;
    }
}
