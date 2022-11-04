package cn.xqhuang.dps.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GrantedDashboardMenu {
    @JsonIgnore
    private Long id;
    private String name;
    private String title;
    private String path;
    private String memo;

    private GrantedDashboardMenu(GrantedDashboardMenu.Builder grantedDashboardMenuBuilder) {
        this.id = grantedDashboardMenuBuilder.id;
        this.name = grantedDashboardMenuBuilder.name;
        this.title = grantedDashboardMenuBuilder.title;
        this.path = grantedDashboardMenuBuilder.path;
        this.memo = grantedDashboardMenuBuilder.memo;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getMemo() {
        return memo;
    }

    public static class Builder implements InstanceBuilder<GrantedDashboardMenu> {
        private Long id;
        private String path;
        private String name;
        private String title;
        private String memo;

        public GrantedDashboardMenu.Builder id(Long id) {
            this.id = id;
            return this;
        }

        public GrantedDashboardMenu.Builder path(String path) {
            this.path = path;
            return this;
        }

        public GrantedDashboardMenu.Builder memo(String memo) {
            this.memo = memo;
            return this;
        }

        public GrantedDashboardMenu.Builder name(String name) {
            this.name = name;
            return this;
        }

        public GrantedDashboardMenu.Builder title(String title) {
            this.title = title;
            return this;
        }


        @Override
        public GrantedDashboardMenu build() {
            return new GrantedDashboardMenu(this);
        }
    }
}
