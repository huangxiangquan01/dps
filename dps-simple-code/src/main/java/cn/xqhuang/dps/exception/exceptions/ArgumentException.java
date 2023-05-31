package cn.xqhuang.dps.exception.exceptions;

import cn.xqhuang.dps.exception.BaseException;
import cn.xqhuang.dps.exception.enums.IResponseEnum;

public class ArgumentException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ArgumentException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public ArgumentException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }

}