package cn.xqhuang.dps.excel.style;


import cn.xqhuang.dps.excel.annotation.CellGroup;
import cn.xqhuang.dps.excel.annotation.RowCell;
import org.apache.poi.hssf.util.HSSFColor;

public interface StyleSheet {
    Font headFont(Class<?> clazz);

    Font headFont(Class<?> clazz, RowCell rowCell);

    RowStyle headRowStyle(Class<?> clazz);

    RowCellStyle headCellStyle(Class<?> clazz, RowCell rowCell);

    RowCellStyle headCellStyle(Class<?> clazz, RowCell rowCell, CellGroup cellGroup);

    Font contentFont(Class<?> clazz, RowCell cell, Object data);

    Font contentFont(Class<?> clazz, RowCell cell, CellGroup cellGroup, Object data);

    RowStyle contentRowStyle(Class<?> clazz, Object data);

    RowCellStyle contentCellStyle(Class<?> clazz, RowCell cell, Object data);

    RowCellStyle contentCellStyle(Class<?> clazz, RowCell cell, CellGroup cellGroup, Object data);

    Font font(String aliasName);

    Font setFont(HSSFColor.HSSFColorPredefined colorPredefined);

    RowStyle rowStyle(String aliasName);

    RowCellStyle cellStyle(String aliasName);
}

