package cn.xqhuang.dps.user;


import java.util.List;

public class UserProfile {
    private UserBasicInfo basicInfo;
    private List<GrantedRole> grantedRoles;
    private List<GrantedMenu> grantedMenus;
    private List<GrantedDashboardMenu> grantedDashboardMenus;

    private UserProfile(Builder userProfileBuilder) {
        this.basicInfo = userProfileBuilder.basicInfo;
        this.grantedRoles = userProfileBuilder.grantedRoles;
        this.grantedMenus = userProfileBuilder.grantedMenus;
        this.grantedDashboardMenus = userProfileBuilder.grantedDashboardMenus;
    }

    public UserBasicInfo getBasicInfo() {
        return basicInfo;
    }

    public List<GrantedRole> getGrantedRoles() {
        return grantedRoles;
    }

    public List<GrantedMenu> getGrantedMenus() {
        return grantedMenus;
    }

    public List<GrantedDashboardMenu> getGrantedDashboardMenus() {
        return grantedDashboardMenus;
    }


    public static class Builder implements InstanceBuilder<UserProfile> {
        private UserBasicInfo basicInfo;
        private List<GrantedRole> grantedRoles;
        private List<GrantedMenu> grantedMenus;
        private List<GrantedDashboardMenu> grantedDashboardMenus;

        public Builder basicInfo(UserBasicInfo basicInfo) {
            this.basicInfo = basicInfo;
            return this;
        }

        public Builder grantedRoles(List<GrantedRole> grantedRoles) {
            this.grantedRoles = grantedRoles;
            return this;
        }

        public Builder grantedMenus(List<GrantedMenu> grantedMenus) {
            this.grantedMenus = grantedMenus;
            return this;
        }

        public Builder grantedDashboardMenus(List<GrantedDashboardMenu> grantedDashboardMenus) {
            this.grantedDashboardMenus = grantedDashboardMenus;
            return this;
        }

        @Override
        public UserProfile build() {
            return new UserProfile(this);
        }
    }
}
