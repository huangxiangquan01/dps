package cn.xqhuang.dps.excel.style;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RowStyle {
    /**
     * 样式唯一标识
     */
    private String id = UUID.randomUUID().toString();
    /**
     * 行高
     */
    private int height = 360;

    public RowStyle setId(String id) {
        this.id = id;
        return this;
    }

    public RowStyle setHeight(int height) {
        this.height = height;
        return this;
    }
}