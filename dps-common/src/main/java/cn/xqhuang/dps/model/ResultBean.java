package cn.xqhuang.dps.model;


import lombok.Getter;

public class ResultBean {

    @Getter
    public static class Success<T> {
        private int status = 1;
        private T content;
        private String msg;

        private Success(T content, String msg) {
            this.content = content;
            this.msg = msg;

        }

        public static <T> Success of(T content, String msg) {
            return new Success<>(content, msg);
        }
    }

    @Getter
    public static class Failure {
        private int status = 0;
        private int errorCode;
        private String errorMsg;

        private Failure(int errorCode, String errorMsg) {
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
        }

        public static Failure of(int errorCode, String errorMsg) {
            return new Failure(errorCode, errorMsg);
        }
    }
}
