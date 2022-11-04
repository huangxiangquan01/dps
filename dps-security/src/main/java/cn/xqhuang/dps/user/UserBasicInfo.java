package cn.xqhuang.dps.user;

import lombok.Getter;


@Getter
public class UserBasicInfo {
    private Long id;
    private String loginName;
    private String email;
    private String mobile;
    private String type;
    private String redirectUrl;

    private UserBasicInfo(Builder userBasicInfoBuilder) {
        this.id = userBasicInfoBuilder.id;
        this.loginName = userBasicInfoBuilder.loginName;
        this.email = userBasicInfoBuilder.email;
        this.mobile = userBasicInfoBuilder.mobile;
        this.type = userBasicInfoBuilder.type;
        this.redirectUrl = userBasicInfoBuilder.redirectUrl;
    }

    public String toString() {
        return "{loginName: " + loginName + ",email: " + email + ",mobile: "
                + mobile + " }";
    }

    public static class Builder implements InstanceBuilder<UserBasicInfo> {
        private Long id;
        private String loginName;
        private String email;
        private String mobile;
        private String type;
        private String redirectUrl;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder loginName(String loginName) {
            this.loginName = loginName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        @Override
        public UserBasicInfo build() {
            return new UserBasicInfo(this);
        }
    }
}
