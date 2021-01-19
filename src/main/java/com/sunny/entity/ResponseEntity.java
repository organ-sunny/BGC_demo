package com.sunny.entity;

public class ResponseEntity {

    private String message;
    private Integer code;
    private Object data;

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 定义静态方法以便调用，省的每次都要new ResponseEntity()；
     */
    public static ResponseEntity normalReturn(String message,Integer code,Object data){
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMessage(message);
        responseEntity.setCode(code);
        responseEntity.setData(data);
        return responseEntity;
    }
}
