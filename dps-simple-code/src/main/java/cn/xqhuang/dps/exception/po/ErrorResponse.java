package cn.xqhuang.dps.exception.po;

public class ErrorResponse extends BaseResponse {
    public ErrorResponse() {
    }
    public ErrorResponse(int code, String message) {
        super(code, message);
    }
}