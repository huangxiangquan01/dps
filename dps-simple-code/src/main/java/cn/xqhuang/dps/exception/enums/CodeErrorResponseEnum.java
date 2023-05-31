package cn.xqhuang.dps.exception.enums;

import cn.xqhuang.dps.exception.asserts.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>代码错误异常,用于检查约定规范不正确的编码情况</p>
 * <p>返回-1</p>
 */
@Getter
@AllArgsConstructor
public enum CodeErrorResponseEnum  implements BusinessExceptionAssert {
    /**
     * 绑定参数校验异常
     */
    CODE_ERROR(-1, "写法不规范-{0}");
    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;
}