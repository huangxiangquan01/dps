package cn.xqhuang.dps.exception.asserts;

import cn.xqhuang.dps.exception.BaseException;
import cn.xqhuang.dps.exception.exceptions.ArgumentException;
import cn.xqhuang.dps.exception.enums.IResponseEnum;

import java.text.MessageFormat;

public interface CommonExceptionAssert extends IResponseEnum, Assert {
    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ArgumentException(this, args, msg, t);
    }
}