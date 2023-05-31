package cn.xqhuang.dps.exception.enums;

import cn.xqhuang.dps.exception.BaseException;
import cn.xqhuang.dps.exception.asserts.CommonExceptionAssert;
import cn.xqhuang.dps.exception.po.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>通用返回结果</p>
 */
@Getter
@AllArgsConstructor
public enum CommonResponseEnum implements CommonExceptionAssert {
    /**
     * 成功
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * 服务器繁忙，请稍后重试
     */
    SERVER_BUSY(9999, "服务器繁忙"),
    /**
     * 服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出
     */
    SERVER_ERROR(9999, "网络异常"),
    TOKEN_EXPIRED(9999, "登录超时"),
    CANOT_CONTINUE (7000, "不能继续，{0}。"),

    QUERY_ERROR(7000,"查询异常，{0}");

    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;

    /**
     * 校验返回结果是否成功
     *
     * @param response 远程调用的响应
     */
    public static void assertSuccess(BaseResponse response) {
        SERVER_ERROR.assertNotNull(response);
        int code = response.getCode();
        if (CommonResponseEnum.SUCCESS.getCode() != code) {
            String msg = response.getMsg();
            throw new BaseException(code, msg);
        }
    }
}