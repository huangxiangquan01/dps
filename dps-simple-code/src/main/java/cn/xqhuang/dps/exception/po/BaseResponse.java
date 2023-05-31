package cn.xqhuang.dps.exception.po;

import cn.xqhuang.dps.exception.enums.CommonResponseEnum;
import cn.xqhuang.dps.exception.enums.IResponseEnum;
import lombok.Data;

@Data
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String msg;

    public BaseResponse() {
        this(CommonResponseEnum.SUCCESS);
    }

    public BaseResponse(IResponseEnum iResponseEnum) {
        this(iResponseEnum.getCode(), iResponseEnum.getMessage());
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.msg = message;
    }

}