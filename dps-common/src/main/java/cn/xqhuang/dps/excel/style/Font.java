package cn.xqhuang.dps.excel.style;

import lombok.Getter;
import org.apache.poi.hssf.util.HSSFColor;

import java.util.UUID;


@Getter
public class Font {
    /**
     * 样式唯一标识
     */
    private String id = UUID.randomUUID().toString();
    /**
     * 字体颜色
     */
    private HSSFColor.HSSFColorPredefined color = HSSFColor.HSSFColorPredefined.BLACK;

    private boolean boldWeight = false;
    /**
     * 字体
     */
    private String name = "微软雅黑";

    /**
     * 字体大小
     */
    private int height = 240;

    public Font setId(String id) {
        this.id = id;
        return this;
    }

    public Font setColor(HSSFColor.HSSFColorPredefined color) {
        this.color = color;
        return this;
    }

    public Font setName(String name) {
        this.name = name;
        return this;
    }

    public Font setHeight(int height) {
        this.height = height;
        return this;
    }

    public Font setBoldweight(boolean boldWeight) {
        this.boldWeight = boldWeight;
        return this;
    }
}