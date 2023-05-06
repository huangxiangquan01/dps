package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResult<T> {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /** 是否成功 */
    private String success;
    /** 消息 */
    private String msg;
    /** 响应代码 0失败 1成功 */
    private int code;

    private T data;

    /* 跟踪id */
    private String traceId;

    public static <T> BaseResult<T> newInstance() {
        return new Builder<T>().build();
    }


    public void setResult(T result) {
        setData(result);
    }

    public BaseResult() {}

    private BaseResult(Builder<T> builder){
        this.success = builder.success;
        this.msg = builder.msg;
        this.code = builder.code;
        this.data = builder.data;
        this.traceId = builder.traceId;
    }

    public static class Builder<T> {
        /** 是否成功 */
        private String success;
        /** 消息 */
        private String msg;
        /** 响应代码 0失败 1成功 */
        private int code;
        private T data;
        private String traceId;

        public Builder<T> success(String success){
            this.success = success;
            return this;
        }

        public Builder<T> msg(String msg){
            this.msg = msg;
            return this;
        }

        public Builder<T> code(int code){
            this.code = code;
            return this;
        }

        public Builder<T> data(T data){
            this.data = data;
            return this;
        }

        public Builder<T> traceId(String traceId){
            this.traceId = traceId;
            return this;
        }

        public BaseResult<T> build(){
            return new BaseResult<>(this);
        }
    }
}
