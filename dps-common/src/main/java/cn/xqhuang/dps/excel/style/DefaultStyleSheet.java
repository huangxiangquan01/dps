package cn.xqhuang.dps.excel.style;

import cn.xqhuang.dps.excel.annotation.CellGroup;
import cn.xqhuang.dps.excel.annotation.RowCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Objects;

public class DefaultStyleSheet implements StyleSheet {
    private static final String LEFT_RED = "leftRed";
    private static final String TITLE = "title";
    private static final String MEMO = "memo";
    private static final String HEAD = "head";
    private static final String CONTENT = "content";
    private static final String TITLE_1 = "title_1";

    private static final Font LEFT_RED_FONT;
    private static final Font TITLE_FONT;
    private static final Font TITLE_FONT_1;
    private static final Font MEMO_FONT;
    private static final Font HEAD_FONT;
    private static final Font CONTENT_FONT;

    private static final RowStyle LEFT_RED_STYLE;
    private static final RowStyle TITLE_ROW_STYLE;
    private static final RowStyle TITLE_ROW_STYLE_1;
    private static final RowStyle MEMO_ROW_STYLE;
    private static final RowStyle HEAD_ROW_STYLE;
    private static final RowStyle CONTENT_ROW_STYLE;

    private static final RowCellStyle LEFT_RED_ROWCELL_STYLE;
    private static final RowCellStyle TITLE_ROWCELL_STYLE;
    private static final RowCellStyle TITLE_ROWCELL_STYLE_1;
    private static final RowCellStyle MEMO_ROWCELL_STYLE;
    private static final RowCellStyle HEAD_ROWCELL_STYLE;
    private static final RowCellStyle CONTENT_ROWCELL_STYLE;

    static {
        LEFT_RED_FONT = new Font().setId(LEFT_RED).setColor(HSSFColor.HSSFColorPredefined.RED).setHeight(180);
        TITLE_FONT = new Font().setId(TITLE).setHeight(500);
        TITLE_FONT_1 = new Font().setId(TITLE_1).setHeight(320);
        MEMO_FONT = new Font().setId(MEMO).setColor(HSSFColor.HSSFColorPredefined.RED).setHeight(180);
        HEAD_FONT = new Font().setHeight(180).setId(HEAD).setBoldweight(true);
        CONTENT_FONT = new Font().setHeight(180).setId(CONTENT);

        LEFT_RED_STYLE = new RowStyle().setId(LEFT_RED);
        TITLE_ROW_STYLE = new RowStyle().setId(TITLE).setHeight(800);
        TITLE_ROW_STYLE_1 = new RowStyle().setId(TITLE_1).setHeight(800);
        MEMO_ROW_STYLE = new RowStyle().setId(MEMO).setHeight(1000);
        HEAD_ROW_STYLE = new RowStyle().setId(HEAD);
        CONTENT_ROW_STYLE = new RowStyle().setId(CONTENT);

        LEFT_RED_ROWCELL_STYLE = new RowCellStyle().setId(LEFT_RED).setBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT).setAlignment(HorizontalAlignment.LEFT);
        TITLE_ROWCELL_STYLE = new RowCellStyle().setId(TITLE);
        TITLE_ROWCELL_STYLE_1 = new RowCellStyle().setId(TITLE_1);
        HEAD_ROWCELL_STYLE = new RowCellStyle().setId(HEAD).setBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT);
        MEMO_ROWCELL_STYLE = new RowCellStyle().setId(MEMO).setWrapText(true);
        CONTENT_ROWCELL_STYLE = new RowCellStyle().setId(CONTENT);
    }

    @Override
    public Font headFont(Class<?> clazz) {
        return HEAD_FONT;
    }

    @Override
    public Font headFont(Class<?> clazz, RowCell rowCell) {
        if (Objects.nonNull(rowCell) && rowCell.isRed()) {
            return LEFT_RED_FONT;
        }
        return HEAD_FONT;
    }

    @Override
    public RowStyle headRowStyle(Class<?> clazz) {
        return HEAD_ROW_STYLE;
    }

    @Override
    public RowCellStyle headCellStyle(Class<?> clazz, RowCell rowCell) {
        return headCellStyle(clazz, rowCell, null);
    }

    @Override
    public RowCellStyle headCellStyle(Class<?> clazz, RowCell rowCell, CellGroup cellGroup) {
        if (Objects.nonNull(rowCell) && rowCell.isRed()) {
            return LEFT_RED_ROWCELL_STYLE;
        }
        return HEAD_ROWCELL_STYLE;
    }

    @Override
    public Font contentFont(Class<?> clazz, RowCell cell, Object data) {
        return contentFont(clazz, cell, null, data);
    }

    @Override
    public Font contentFont(Class<?> clazz, RowCell cell, CellGroup cellGroup, Object data) {
        return CONTENT_FONT;
    }

    @Override
    public RowStyle contentRowStyle(Class<?> clazz, Object data) {
        return CONTENT_ROW_STYLE;
    }

    @Override
    public RowCellStyle contentCellStyle(Class<?> clazz, RowCell cell, Object data) {
        return contentCellStyle(clazz, cell, null, data);
    }

    @Override
    public RowCellStyle contentCellStyle(Class<?> clazz, RowCell cell, CellGroup cellGroup, Object data) {
        return CONTENT_ROWCELL_STYLE;
    }

    @Override
    public Font font(String aliasName) {
        if (Objects.equals(aliasName, HEAD)) {
            return HEAD_FONT;
        } else if (Objects.equals(aliasName, MEMO)) {
            return MEMO_FONT;
        } else if (Objects.equals(aliasName, TITLE)) {
            return TITLE_FONT;
        } else if (Objects.equals(aliasName, TITLE_1)) {
            return TITLE_FONT_1;
        } else if (Objects.equals(aliasName, LEFT_RED)) {
            return LEFT_RED_FONT;
        } else {
            return CONTENT_FONT;
        }
    }

    @Override
    public Font setFont(HSSFColor.HSSFColorPredefined colorPredefined) {
        return new Font().setId(colorPredefined.name()).setColor(colorPredefined).setHeight(180);
    }

    @Override
    public RowStyle rowStyle(String aliasName) {
        if (Objects.equals(aliasName, HEAD)) {
            return HEAD_ROW_STYLE;
        } else if (Objects.equals(aliasName, MEMO)) {
            return MEMO_ROW_STYLE;
        } else if (Objects.equals(aliasName, TITLE)) {
            return TITLE_ROW_STYLE;
        } else if (Objects.equals(aliasName, TITLE_1)) {
            return TITLE_ROW_STYLE_1;
        } else if (Objects.equals(aliasName, LEFT_RED)) {
            return LEFT_RED_STYLE;
        } else {
            return CONTENT_ROW_STYLE;
        }
    }

    @Override
    public RowCellStyle cellStyle(String aliasName) {
        if (Objects.equals(aliasName, HEAD)) {
            return HEAD_ROWCELL_STYLE;
        } else if (Objects.equals(aliasName, MEMO)) {
            return MEMO_ROWCELL_STYLE;
        } else if (Objects.equals(aliasName, TITLE)) {
            return TITLE_ROWCELL_STYLE;
        } else if (Objects.equals(aliasName, TITLE_1)) {
            return TITLE_ROWCELL_STYLE_1;
        } else if (Objects.equals(aliasName, LEFT_RED)) {
            return LEFT_RED_ROWCELL_STYLE;
        }  else {
            return CONTENT_ROWCELL_STYLE;
        }
    }
}