package cn.xqhuang.dps.excel;

import cn.xqhuang.dps.excel.annotation.CellGroup;
import cn.xqhuang.dps.excel.annotation.RowCell;
import cn.xqhuang.dps.excel.annotation.Rows;
import cn.xqhuang.dps.excel.style.Font;
import cn.xqhuang.dps.excel.style.*;
import cn.xqhuang.dps.utils.DateUtil;
import cn.xqhuang.dps.utils.JsonUtil;
import cn.xqhuang.dps.utils.ReflectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ServiceException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelWorkSheet {
    private final Sheet sheet;

    private StyleSheet styleSheet = new DefaultStyleSheet();

    private final Map<String, CellStyle> rowCellStyleMap = new ConcurrentHashMap<>();
    private final Map<String, org.apache.poi.ss.usermodel.Font> fontStyleMap = new ConcurrentHashMap<>();

    public ExcelWorkSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public ExcelWorkSheet style(StyleSheet styleSheet) {
        this.styleSheet = styleSheet;
        return this;
    }

    public ExcelWorkSheet validationData(List<String> values, int firstRow, int endRow, int firstCell, int endCell) {
        String[] valueAry = new String[values.size()];
        values.toArray(valueAry);
        return validationData(valueAry, firstRow, endRow, firstCell, endCell);
    }

    public ExcelWorkSheet validationData(String[] valueAry, int firstRow, int endRow, int firstCell, int endCell) {
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(valueAry);
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCell, endCell);
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidation);
        return this;
    }

    public ExcelWorkSheet getDataValidationList4Col(List<String> colName,
                                                    String sheetName,
                                                    int sheetNum,
                                                    int firstRow,
                                                    int endRow,
                                                    int firstCol,
                                                    int endCol,
                                                    Workbook workbook) {
        //1.创建隐藏的sheet页。        起个名字吧！叫"hidden"！
        Sheet hidden = workbook.createSheet(sheetName);
        //2.循环赋值（为了防止下拉框的行数与隐藏域的行数相对应，将隐藏域加到结束行之后）
        for (int i = 0, length = colName.size(); i < length; i++) {
            hidden.createRow(i).createCell(0).setCellValue(colName.get(i));
        }
        Name category1Name = workbook.createName();
        category1Name.setNameName(sheetName);
        //3 A1:A代表隐藏域创建第N列createCell(N)时。以A1列开始A行数据获取下拉数组
        category1Name.setRefersToFormula(sheetName + "!$A$1:$A$" + colName.size());
        //4.加载叫做“hidden”这个sheet的数据
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(sheetName);
        //5.起始行 终止行 起始列 终止列
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
        //6.这个就是隐藏sheet的地方咯。
        workbook.setSheetHidden(sheetNum, true);

        //7.最后把数据装到真实sheet的下拉里。就ok咯。
        sheet.addValidationData(validation);
        return this;
    }
    public String readExcel(Integer startRows, final Map<Integer, ExcelCell> cells) {

        return readExcel(startRows,cells,null);
    }

    /**
     * 读取excel文件转成 json
     * @return
     */
    public String readExcel(Integer startRows, final Map<Integer, ExcelCell> cells,Boolean isString) {
        int rows = sheet.getPhysicalNumberOfRows();
        if (Objects.isNull(startRows)){
            startRows=1;
        }
        List<Map<String,Object>> v =Lists.newArrayList();
        for (int i = startRows; i < rows; i++) {
            Row row =   sheet.getRow(i);
            if (Objects.nonNull(row)){
                Map<String,Object> map = Maps.newHashMap();
                int number = row.getPhysicalNumberOfCells();
                for (int j=0;j<number;j++){
                    Cell cell =    row.getCell(j);
                    if (Objects.nonNull(isString)&& isString&&Objects.nonNull(cell)){
                        cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
                    }
                    ExcelCell excelCell =cells.get(j+1);
                    if (Objects.nonNull(cell)){
                        setValue(cell,map,excelCell);
                    }

                }
                v.add(map);
            }
        }
        if (!CollectionUtils.isEmpty(v)){
            return JsonUtil.toJson(v);
        }
        return null;
    }


    private void setValue(Cell cell,Map<String,Object> map,ExcelCell cells){
        if(null == cell){
            return;
        }
        if (cell.getCellTypeEnum() == org.apache.poi.ss.usermodel.CellType.BOOLEAN) {
            // 返回布尔类型的值
            map.put(cells.getFiled(),(Boolean)cell.getBooleanCellValue());
        } else if (cell.getCellTypeEnum() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
            // 返回数值类型的值
            map.put(cells.getFiled(),new BigDecimal(cell.getNumericCellValue()).doubleValue());
        } else {
            // 返回字符串类型的值
            map.put(cells.getFiled(),cell.getStringCellValue());
        }
    }

    public ExcelWorkSheet writeHead(final Class<?> clazz) {
        return writeHead(clazz, null);
    }

    public ExcelWorkSheet writeHead(final Class<?> clazz,
                                    final String groupName) {
        return createHeadCell(null, clazz, ExcelReportUtil.getModelProperties(clazz, groupName));
    }


    public ExcelWorkSheet writeHeadWithCells(Class<?> clazz, final Map<String, ExcelCell> cells) {
        return createHeadCellWithCells(null, clazz, cells);
    }


    public ExcelWorkSheet freeWriteHead(final Class<?> clazz,
                                        final Map<Class<?>, Map<String, Integer>> fieldMap) {
        return createHeadCell(null, clazz, ExcelReportUtil.getModelProperties(clazz, fieldMap));
    }

    public ExcelWorkSheet writeRow(int rowIndex, int cellIndex, String value) {
        return writeRow(rowIndex, cellIndex, value, null);
    }

    public ExcelWorkSheet writeRow(int rowIndex, int cellIndex, String value, String aliasName) {
        Row row = createRow(rowIndex);

        setRowStyle(row, styleSheet.rowStyle(aliasName));

        Cell cell = row.getCell(cellIndex);
        if (Objects.isNull(cell)) {
            cell = row.createCell(cellIndex);
        }

        setCellStyle(cell, null, styleSheet.cellStyle(aliasName), styleSheet.font(aliasName));

        if (StringUtils.isNotBlank(value) && StringUtils.isBlank(aliasName) && value.indexOf("(") > 0) {
            HSSFRichTextString richTextString = new HSSFRichTextString(value);
            richTextString.applyFont(value.indexOf("("), value.indexOf("("), getFont(styleSheet.font("content")));
            richTextString.applyFont(value.indexOf("("), value.length(), getFont(styleSheet.font("memo")));
            cell.setCellValue(richTextString);
        } else {
            cell.setCellValue(Objects.nonNull(value) ? value : "");
        }

        return this;
    }

    public ExcelWorkSheet merge(int firstRow, int lastRow, int firstCell, int lastCell) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCell, lastCell));

        int mergeRows = lastRow - firstRow;
        int mergeCells = lastCell - firstCell;

        if (mergeRows >= 0 && mergeCells >= 0) {
            CellStyle cellStyle = null;
            for (int i = firstRow; i <= lastRow; i++) {
                Row curRow = sheet.getRow(i);
                if (Objects.isNull(curRow)) {
                    curRow = createRow(i);
                }
                for (int j = firstCell; j <= lastCell; j++) {
                    Cell curCell = curRow.getCell(j);
                    if (Objects.isNull(curCell)) {
                        curCell = curRow.createCell(j);
                    }

                    if (i == firstRow && j == firstCell) {
                        cellStyle = curCell.getCellStyle();
                    } else if (Objects.nonNull(cellStyle)) {
                        curCell.setCellStyle(cellStyle);
                    }

                }
            }
        }

        return this;
    }

    public ExcelWorkSheet write(Object data) {
        return write(data, null);
    }

    public ExcelWorkSheet write(Object data, final String groupName) {
        if (Objects.isNull(data)) {
            return this;
        }
        return writeContent(null, data.getClass(), data, ExcelReportUtil.getModelProperties(data.getClass(), groupName));
    }

    public ExcelWorkSheet write(List<?> list) {
        return write(list, null);
    }

    public ExcelWorkSheet writeWithCells(List<?> list, final Map<String, ExcelCell> cells) {
        if (CollectionUtils.isEmpty(list)) {
            return this;
        }
        list.forEach(item -> writeContentWithCells(null, item.getClass(), item, cells));
        return this;
    }


    public ExcelWorkSheet write(List<?> list, final String groupName) {
        if (list.isEmpty()) {
            return this;
        }

        final Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> propertyMap = ExcelReportUtil.getModelProperties(list.get(0).getClass(), groupName);
        list.forEach(item -> writeContent(null, item.getClass(), item, propertyMap));
        return this;
    }

    public ExcelWorkSheet freeWrite(List<?> list,
                                    final Map<Class<?>, Map<String, Integer>> fieldMap) {
        if (CollectionUtils.isEmpty(list)) {
            return this;
        }

        final Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> propertyMap = ExcelReportUtil.getModelProperties(list.get(0).getClass(), fieldMap);
        list.forEach(item -> writeContent(null, item.getClass(), item, propertyMap));
        return this;
    }

    private ExcelWorkSheet createHeadCell(Row row, Class<?> clazz, Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> propertyDefinitionMap) {
        if (propertyDefinitionMap.isEmpty()) {
            return this;
        }

        if (Objects.isNull(row)) {
            row = createRow();
            setHeadRowStyle(row, clazz);
        }

        List<ReportModelPropertyDefinitionWrapper> propertyDefinitionWrappers = propertyDefinitionMap.get(clazz);

        for (ReportModelPropertyDefinitionWrapper property : propertyDefinitionWrappers) {
            CellGroup cellGroup = property.getCellGroup();
            RowCell rowCell = property.getRowCell();
            Rows rows = property.getRows();
            if (Objects.nonNull(rowCell)) {
                int cellIndex = property.getIndex() - 1;
                Cell cell = createAndSetCellValue(row, cellIndex, NewCellType.STRING, null, property.getTitle());
                setHeadCellStyle(cell, clazz, rowCell, cellGroup);
            } else if (Objects.nonNull(rows)) {
                return createHeadCell(row, rows.value(), propertyDefinitionMap);
            }
        }
        return this;
    }


    private ExcelWorkSheet createHeadCellWithCells(Row row, Class<?> clazz, final Map<String, ExcelCell> cells) {
        if (cells.isEmpty()) {
            throw new ServiceException("cells is null");
        }
        List<ReportModelPropertyDefinition> properties = ExcelReportUtil.getModelDefinition(clazz);
        if (CollectionUtils.isEmpty(properties)) {
            return this;
        }
        if (Objects.isNull(row)) {
            row = createRow();
            setHeadRowStyle(row, clazz);
        }
        for (ReportModelPropertyDefinition property : properties) {
            CellGroup cellGroup = property.getCellGroup(null);
            RowCell rowCell = property.getRowCell();
            Rows rows = property.getRows();
            ExcelCell excelCell = null;
            if (Objects.nonNull(property.getField())) {
                excelCell = cells.get(property.getField().getName());
                if (Objects.nonNull(excelCell) && !excelCell.getIsExport()) {
                    continue;
                }
            }
            if (Objects.nonNull(rowCell)) {
                int cellIndex = getCellIndex(rowCell, cellGroup);
                if (Objects.nonNull(excelCell)) {
                    cellIndex = excelCell.getIndex() - 1;
                }
                Cell cell = createAndSetCellValue(row, cellIndex, NewCellType.STRING, null, getCellTitle(rowCell, cellGroup));
                setHeadCellStyle(cell, clazz, rowCell, cellGroup);
            } else if (Objects.nonNull(rows)) {
                return createHeadCellWithCells(row, rows.value(), cells);
            }
        }
        return this;
    }
    private int getCellIndex(RowCell rowCell, CellGroup cellGroup) {
        if (Objects.nonNull(cellGroup)) {
            return cellGroup.index() - 1;
        }
        return rowCell.index() - 1;
    }

    private String getCellTitle(RowCell rowCell, CellGroup cellGroup) {
        if (Objects.nonNull(cellGroup)) {
            if (!StringUtils.isEmpty(cellGroup.title())) {
                return cellGroup.title();
            }
        }
        return rowCell.title();
    }

    private ExcelWorkSheet writeContentWithCells(Row row, final Class<?> clazz, final Object data, final Map<String, ExcelCell> cells) {

        if (cells.isEmpty()) {
            throw new ServiceException("cells is null");
        }

        List<ReportModelPropertyDefinition> properties = ExcelReportUtil.getModelDefinition(clazz);
        if (CollectionUtils.isEmpty(properties)) {
            return this;
        }
        JSONObject jsonObject = JSON.parseObject(JsonUtil.toJson(data));
        HSSFColor.HSSFColorPredefined color = null;
        String rowFontColor = jsonObject.getString("rowFontColor");
        if (StringUtils.isNotBlank(rowFontColor)) {
            color = HSSFColor.HSSFColorPredefined.valueOf(rowFontColor);
        }

        if (Objects.isNull(row)) {
            row = createRow();
            setContentRowStyle(row, clazz, data);
        }

        //先写列表属性
        for (ReportModelPropertyDefinition property : properties) {
            if (Objects.isNull(property.getRows())) {
                continue;
            }
            Rows rows = property.getRows();
            List<?> dataList = (List<?>) ReflectionUtils.getField(property.getField(), data);
            ExcelCell cell;
            if (Objects.nonNull(property.getField())) {
                cell = cells.get(property.getField().getName());
                if (Objects.nonNull(cell) && !cell.getIsExport()) {
                    continue;
                }
            }
            if (CollectionUtils.isEmpty(dataList)) {
                writeContentWithCells(row, rows.value(), null, cells);
            } else {
                Row childRow = row;
                for (int i = 0; i < dataList.size(); i++) {
                    Object childData = dataList.get(i);
                    writeContentWithCells(childRow, rows.value(), childData, cells);
                    if (i < dataList.size() - 1) {
                        childRow = createRow();
                        setContentRowStyle(childRow, rows.value(), childData);
                    }
                }
            }
        }
        //再写普通属性
        int startRowIndex = row.getRowNum();
        int lastRowIndex = sheet.getLastRowNum();
        boolean merge = lastRowIndex - startRowIndex > 0;
        for (ReportModelPropertyDefinition property : properties) {
            if (Objects.isNull(property.getRowCell())) {
                continue;
            }
            ExcelCell excelCell = null;
            if (Objects.nonNull(property.getField())) {
                excelCell = cells.get(property.getField().getName());
                if (Objects.nonNull(excelCell) && !excelCell.getIsExport()) {
                    continue;
                }
            }
            CellGroup cellGroup = property.getCellGroup(null);
            RowCell rowCell = property.getRowCell();
            Field field = property.getField();
            if (Objects.nonNull(rowCell)) {
                Object value = null;
                if (Objects.nonNull(data)) {
                    value = ReflectionUtils.getField(field, data);
                }
                NewCellType newCellType = rowCell.type();
                int cellIndex = getCellIndex(rowCell, cellGroup);
                if (Objects.nonNull(excelCell)) {
                    cellIndex = excelCell.getIndex() - 1;
                }
                Cell cell = createAndSetCellValue(row, cellIndex, newCellType, rowCell.format(), value);
                setContentCellStyle(cell, clazz, rowCell, data, color);
                if (merge) {
                    merge(startRowIndex, lastRowIndex, cellIndex, cellIndex);
                }
            }
        }
        return this;
    }


    private ExcelWorkSheet writeContent(Row row, final Class<?> clazz, final Object data, Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> propertyDefinitionMap) {
        List<ReportModelPropertyDefinitionWrapper> properties = propertyDefinitionMap.get(clazz);
        if (CollectionUtils.isEmpty(properties)) {
            return this;
        }

        JSONObject jsonObject = JSON.parseObject(JsonUtil.toJson(data));
        HSSFColor.HSSFColorPredefined color = null;
        String rowFontColor = jsonObject.getString("rowFontColor");
        if (StringUtils.isNotBlank(rowFontColor)) {
            color = HSSFColor.HSSFColorPredefined.valueOf(rowFontColor);
        }

        if (Objects.isNull(row)) {
            row = createRow();
            setContentRowStyle(row, clazz, data);
        }

        //先写列表属性
        for (ReportModelPropertyDefinitionWrapper property : properties) {
            if (Objects.isNull(property.getRows())) {
                continue;
            }

            Rows rows = property.getRows();
            List<?> dataList = (List<?>) ReflectionUtils.getField(property.getField(), data);
            if (CollectionUtils.isEmpty(dataList)) {
                writeContent(row, rows.value(), null, propertyDefinitionMap);
            } else {
                Row childRow = row;
                for (int i = 0; i < dataList.size(); i++) {
                    Object childData = dataList.get(i);
                    writeContent(childRow, rows.value(), childData, propertyDefinitionMap);
                    if (i < dataList.size() - 1) {
                        childRow = createRow();
                        setContentRowStyle(childRow, rows.value(), childData);
                    }
                }
            }
        }

        //再写普通属性
        int startRowIndex = row.getRowNum();
        int lastRowIndex = sheet.getLastRowNum();
        boolean merge = lastRowIndex - startRowIndex > 0;


        for (ReportModelPropertyDefinitionWrapper property : properties) {
            if (Objects.isNull(property.getRowCell())) {
                continue;
            }
            RowCell rowCell = property.getRowCell();
            Field field = property.getField();
            if (Objects.nonNull(rowCell)) {
                Object value = null;
                if (Objects.nonNull(data)) {
                    value = ReflectionUtils.getField(field, data);
                }

                NewCellType newCellType = rowCell.type();
                int cellIndex = property.getIndex() - 1;
                Cell cell = createAndSetCellValue(row, cellIndex, newCellType, rowCell.format(), value);
                setContentCellStyle(cell, clazz, rowCell, data, color);

                if (merge) {
                    merge(startRowIndex, lastRowIndex, cellIndex, cellIndex);
                }
            }
        }
        return this;
    }

    private Cell createAndSetCellValue(Row row, int cellIndex, NewCellType newCellType, String format, Object cellValue) {
        Cell cell = row.createCell(cellIndex);
        if (NewCellType.NUMERIC == newCellType) {
            cell.setCellType(org.apache.poi.ss.usermodel.CellType.NUMERIC);
            if (Objects.nonNull(cellValue)) {
                cell.setCellValue(Double.valueOf(cellValue.toString()));
            } else {
                cell.setCellValue("");
            }
        } else if (NewCellType.DATE == newCellType) {
            if (Objects.nonNull(cellValue)) {
                Date date = (Date) cellValue;
                format = StringUtils.isEmpty(format) ? DateUtil.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS : format;
                cell.setCellValue(DateUtil.format(date, format));
            }
        } else {
            cell.setCellValue(Objects.nonNull(cellValue) ? cellValue.toString() : "");
        }
        return cell;
    }

    private void setHeadRowStyle(Row row, Class<?> clazz) {
        RowStyle rowStyle = styleSheet.headRowStyle(clazz);
        setRowStyle(row, rowStyle);
    }

    private void setHeadCellStyle(Cell cell, Class<?> clazz, RowCell rowCell, CellGroup cellGroup) {
        RowCellStyle rowCellStyle = styleSheet.headCellStyle(clazz, rowCell);
        Font font = styleSheet.headFont(clazz, rowCell);
        setCellStyle(cell, rowCell, rowCellStyle, font);
    }

    private void setContentRowStyle(Row row, Class<?> clazz, Object data) {
        RowStyle rowStyle = styleSheet.contentRowStyle(clazz, data);
        setRowStyle(row, rowStyle);
    }

    private void setContentCellStyle(Cell cell, Class<?> clazz, RowCell rowCell, Object data, HSSFColor.HSSFColorPredefined color) {
        RowCellStyle rowCellStyle = styleSheet.contentCellStyle(clazz, rowCell, data);
        Font font = styleSheet.contentFont(clazz, rowCell, data);
        if (Objects.nonNull(color)) {
            font = styleSheet.setFont(color);
        }
        setCellStyle(cell, rowCell, rowCellStyle, font);
    }

    private void setRowStyle(Row row, RowStyle rowStyle) {
        if (Objects.nonNull(rowStyle)) {
            row.setHeight((short) rowStyle.getHeight());
        }
    }

    private void setCellStyle(Cell cell, RowCell rowCell, RowCellStyle rowCellStyle, Font font) {
        CellStyle cellStyle = getCellStyle(rowCellStyle, font);

        if (Objects.nonNull(rowCell)) {
            sheet.setColumnWidth(cell.getColumnIndex(), rowCell.width() * 256);
        }

        if (Objects.nonNull(cellStyle)) {
            cell.setCellStyle(cellStyle);
        }
    }

    private Row createRow() {
        int rowIndex = sheet.getLastRowNum();
        if (!Objects.isNull(sheet.getRow(rowIndex))) {
            rowIndex++;
        }
        return createRow(rowIndex);
    }

    private Row createRow(int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (Objects.isNull(row)) {
            row = sheet.createRow(rowIndex);
        }
        return row;
    }

    private CellStyle getCellStyle(RowCellStyle rowCellStyle, Font font) {
        StringBuilder styleKey = new StringBuilder();
        if (Objects.nonNull(rowCellStyle)) {
            styleKey.append(rowCellStyle.getId());
        }

        if (Objects.nonNull(font)) {
            if (styleKey.length() > 0) {
                styleKey.append("-");
            }
            styleKey.append(font.getId());
        }

        CellStyle cellStyle = rowCellStyleMap.get(styleKey.toString());
        if (Objects.isNull(cellStyle)) {
            // log.info(String.format("styleKey-----------> %s", styleKey.toString()));
            cellStyle = buildCellStyle(rowCellStyle, font);
            if (Objects.nonNull(cellStyle)) {
                rowCellStyleMap.put(styleKey.toString(), cellStyle);
            }
        }

        return cellStyle;
    }

    private org.apache.poi.ss.usermodel.Font getFont(Font font) {
        if (Objects.isNull(font)) {
            return null;
        }
        org.apache.poi.ss.usermodel.Font poiFont = fontStyleMap.get(font.getId());
        if (Objects.isNull(poiFont)) {
            poiFont = buildFont(font);
            if (Objects.nonNull(poiFont)) {
                fontStyleMap.put(font.getId(), poiFont);
            }
        }
        return poiFont;
    }

    private CellStyle buildCellStyle(RowCellStyle rowCellStyle, Font font) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        if (Objects.nonNull(rowCellStyle)) {
            cellStyle.setAlignment(rowCellStyle.getAlignment());
            cellStyle.setVerticalAlignment(rowCellStyle.getVerticalAlignment());
            if (Objects.nonNull(rowCellStyle.getBackgroundColor())) {
                cellStyle.setFillForegroundColor(rowCellStyle.getBackgroundColor().getIndex());
            }
            cellStyle.setFillPattern(rowCellStyle.getFillPattern());
            cellStyle.setBorderTop(rowCellStyle.getBorderStyle());
            cellStyle.setBorderBottom(rowCellStyle.getBorderStyle());
            cellStyle.setBorderLeft(rowCellStyle.getBorderStyle());
            cellStyle.setBorderRight(rowCellStyle.getBorderStyle());
            cellStyle.setWrapText(rowCellStyle.isWrapText());

            if (Objects.nonNull(rowCellStyle.getBorderColor())) {
                cellStyle.setTopBorderColor(rowCellStyle.getBorderColor().getIndex());
                cellStyle.setBottomBorderColor(rowCellStyle.getBorderColor().getIndex());
                cellStyle.setLeftBorderColor(rowCellStyle.getBorderColor().getIndex());
                cellStyle.setRightBorderColor(rowCellStyle.getBorderColor().getIndex());
            }
        }

        if (Objects.nonNull(font)) {
            org.apache.poi.ss.usermodel.Font cellFont = getFont(font);
            if (Objects.nonNull(cellFont)) {
                cellStyle.setFont(cellFont);
            }
        }
        return cellStyle;
    }


    private org.apache.poi.ss.usermodel.Font buildFont(Font font) {
        if (Objects.isNull(font)) {
            return null;
        }
        org.apache.poi.ss.usermodel.Font cellFont = sheet.getWorkbook().createFont();
        cellFont.setColor(font.getColor().getIndex());
        cellFont.setFontName(font.getName());
        cellFont.setFontHeight((short) font.getHeight());
        cellFont.setBold(font.isBoldWeight());
        return cellFont;
    }

}
