package cn.xqhuang.dps.exception;

import cn.xqhuang.dps.exception.enums.IResponseEnum;
import lombok.Getter;

/**
 * @ClassName: BaseException
 * @Author: whp
 * @Description: 基础异常类
 * @Date: 2022/3/4 11:20
 * @Version: 1.0
 */
@Getter
public class BaseException extends RuntimeException{

    protected IResponseEnum responseEnum;
    protected Object[] args;
 
    public BaseException(IResponseEnum responseEnum){
        super(responseEnum.getMessage());
        this.responseEnum=responseEnum;
    }
 
    public BaseException(int code,String msg){
        super(msg);
        this.responseEnum = new IResponseEnum() {
            @Override
            public int getCode() {
                return code;
            }
 
            @Override
            public String getMessage() {
                return msg;
            }
        };
    }
 
    public BaseException(IResponseEnum responseEnum,Object[] args,String message){
        super(message);
        this.responseEnum=responseEnum;
        this.args=args;
    }
 
    public BaseException(IResponseEnum responseEnum,Object[] args,String message,Throwable cause){
        super(message,cause);
        this.responseEnum=responseEnum;
        this.args=args;
    }
}