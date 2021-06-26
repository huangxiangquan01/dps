package cn.xqhuang.dps.excel.style;

import lombok.Getter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.UUID;

@Getter
public class RowCellStyle {
    private String id=UUID.randomUUID().toString();
    /**
     * 单元格内容水平位置
     */
    private HorizontalAlignment alignment = HorizontalAlignment.CENTER_SELECTION;

    /**
     * 单元格内容垂直位置
     */
    private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;


    /**
     * 单元格背景色
     */
    private HSSFColor.HSSFColorPredefined backgroundColor = HSSFColor.HSSFColorPredefined.WHITE;

    /**
     * 背景填充方式
     */
    private FillPatternType fillPattern = FillPatternType.SOLID_FOREGROUND;

    /**
     * 边框样式
     */
    private BorderStyle borderStyle = BorderStyle.THIN;

    /**
     * 边框颜色
     */
    private IndexedColors borderColor = IndexedColors.BLACK;

    private boolean wrapText = false;

    public RowCellStyle setId(String id) {
        this.id = id;
        return this;
    }

    public RowCellStyle setAlignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public RowCellStyle setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }

    public RowCellStyle setBackgroundColor(HSSFColor.HSSFColorPredefined backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public RowCellStyle setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
        return this;
    }

    public RowCellStyle setBorderColor(IndexedColors borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public RowCellStyle setFillPattern(FillPatternType fillPattern) {
        this.fillPattern = fillPattern;
        return this;
    }

    public RowCellStyle setWrapText(boolean wrapText) {
        this.wrapText = wrapText;
        return this;
    }
}