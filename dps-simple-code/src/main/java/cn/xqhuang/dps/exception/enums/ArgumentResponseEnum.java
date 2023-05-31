package cn.xqhuang.dps.exception.enums;

import cn.xqhuang.dps.exception.asserts.ArgumentExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>参数校验异常返回结果</p>
 * 入参校验异常  6000
 * 入参逻辑判断异常  7000
 */
@Getter
@AllArgsConstructor
public enum ArgumentResponseEnum implements ArgumentExceptionAssert {
    VALID_ERROR(6000, "参数校验异常"),
    INDEX_NOT_EXIST(6000, "提交数据主键不存在"),
    DATA_NOT_NULL(6000, "{0}不能为空"),
    DADA_FORMAT_ERR(6000, "{0}提交数据格式错误"),
    DADA_REPEAT(7000, "{0}已经存在"),
    DADA_RELATION_ERR(7000, "{0}数据关系错误");
    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;

}