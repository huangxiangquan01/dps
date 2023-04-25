package cn.xqhuang.dps.exception;

import cn.xqhuang.dps.constans.ResultCode;

public class CommonException extends Exception{
    public CommonException(String context) {
        super(context);
    }

    public CommonException(ResultCode context) {
        super(context.message());
    }
}