package cn.xqhuang.dps.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GrantedMenu {
    @JsonIgnore
    private Long id;
    private String code;
    private String name;
    private String title;
    private String path;
    private String componentUrl;
    private String noCacheFlag;
    private String hiddenFlag;
    private String alwaysShowFlag;
    private String redirectPath;
    private String icon;
    private Integer level;
    private String type;
    @JsonIgnore
    private Long pid;
    private List<GrantedMenu> subMenus = new ArrayList<>();

    private GrantedMenu(Builder grantedMenuBuilder) {
        this.id = grantedMenuBuilder.id;
        this.path = grantedMenuBuilder.path;
        this.componentUrl = grantedMenuBuilder.componentUrl;
        this.noCacheFlag = grantedMenuBuilder.noCacheFlag;
        this.hiddenFlag = grantedMenuBuilder.hiddenFlag;
        this.alwaysShowFlag = grantedMenuBuilder.alwaysShowFlag;
        this.redirectPath = grantedMenuBuilder.redirectPath;
        this.name = grantedMenuBuilder.name;
        this.title = grantedMenuBuilder.title;
        this.icon = grantedMenuBuilder.icon;
        this.level = grantedMenuBuilder.level;
        this.pid = grantedMenuBuilder.pid;
        this.code = grantedMenuBuilder.code;
        this.type = grantedMenuBuilder.type;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getComponentUrl() {
        return componentUrl;
    }

    public String getNoCacheFlag() {
        return noCacheFlag;
    }

    public String getHiddenFlag() {
        return hiddenFlag;
    }

    public String getAlwaysShowFlag() {
        return alwaysShowFlag;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getLevel() {
        return level;
    }

    public Long getPid() {
        return pid;
    }

    public void addSubMenu(GrantedMenu subMenu) {
        subMenus.add(subMenu);
    }

    public List<GrantedMenu> getSubMenus() {
        return subMenus.stream().sorted(Comparator.comparing(GrantedMenu::getId)).collect(Collectors.toList());
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public static class Builder implements InstanceBuilder<GrantedMenu> {
        private Long id;
        private String code;
        private String path;
        private String componentUrl;
        private String noCacheFlag;
        private String hiddenFlag;
        private String alwaysShowFlag;
        private String redirectPath;
        private String name;
        private String title;
        private String icon;
        private Integer level;
        private Long pid;
        private String type;
        private List<GrantedRole> roles;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder componentUrl(String componentUrl) {
            this.componentUrl = componentUrl;
            return this;
        }

        public Builder noCacheFlag(String noCacheFlag) {
            this.noCacheFlag = noCacheFlag;
            return this;
        }

        public Builder hiddenFlag(String hiddenFlag) {
            this.hiddenFlag = hiddenFlag;
            return this;
        }

        public Builder alwaysShowFlag(String alwaysShowFlag) {
            this.alwaysShowFlag = alwaysShowFlag;
            return this;
        }

        public Builder redirectPath(String redirectPath) {
            this.redirectPath = redirectPath;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder level(Integer level) {
            this.level = level;
            return this;
        }

        public Builder pid(Long pid) {
            this.pid = pid;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        @Override
        public GrantedMenu build() {
            return new GrantedMenu(this);
        }
    }
}
