package cn.xqhuang.dps.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelToListUtil {
    private static final String FULL_DATA_FORMAT = "yyyy/MM/dd  HH:mm:ss";
    private static final String SHORT_DATA_FORMAT = "yyyy/MM/dd";

    @Getter
    @Setter
    public class ExcelHead {
        //Excel名
        private String excelName;
        //实体类属性名
        private String entityName;
        //值必填
        private boolean required = false;

        public ExcelHead(String excelName, String entityName, boolean required) {
            this.excelName = excelName;
            this.entityName = entityName;
            this.required = required;
        }

        public ExcelHead(String excelName, String entityName) {
            this.excelName = excelName;
            this.entityName = entityName;
        }
    }

    /**
     * Excel表头对应Entity属性 解析封装javabean
     *
     * @param clazz    类
     * @param in         excel流
     * @param fileName   文件名
     * @param excelHeads excel表头与entity属性对应关系
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> readExcelToEntity(Class<T> clazz, InputStream in, String fileName, List<ExcelHead> excelHeads) throws Exception {
        return readExcelToEntity(clazz, in, fileName, excelHeads, 1);
    }
        /**
         * Excel表头对应Entity属性 解析封装javabean
         *
         * @param clazz    类
         * @param in         excel流
         * @param fileName   文件名
         * @param excelHeads excel表头与entity属性对应关系
         * @param <T>
         * @return
         * @throws Exception
         */
    public static <T> List<T> readExcelToEntity(Class<T> clazz, InputStream in, String fileName, List<ExcelHead> excelHeads, int startRow) throws Exception {
        checkFile(fileName);    //是否EXCEL文件
        Workbook workbook = getWorkBoot(in, fileName); //兼容新老版本
        List<T> excelForBeans = readExcel(clazz, workbook, excelHeads, startRow);  //解析Excel
        return excelForBeans;
    }

    /**
     * 解析Excel转换为Entity
     *
     * @param classzz  类
     * @param in       excel流
     * @param fileName 文件名
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> readExcelToEntity(Class<T> classzz, InputStream in, String fileName, int startRow) throws Exception {
        return readExcelToEntity(classzz, in, fileName, null, startRow);
    }

    /**
     * 校验是否是Excel文件
     *
     * @param fileName
     * @throws Exception
     */
    public static void checkFile(String fileName) throws Exception {
        if (!StringUtils.isEmpty(fileName) && !(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))) {
            throw new Exception("不是Excel文件！");
        }
    }

    /**
     * 兼容新老版Excel
     *
     * @param in
     * @param fileName
     * @return
     * @throws IOException
     */
    private static Workbook getWorkBoot(InputStream in, String fileName) throws IOException {
        if (fileName.endsWith(".xlsx")) {
            return new XSSFWorkbook(in);
        } else {
            return new HSSFWorkbook(in);
        }
    }

    /**
     * 解析Excel
     *
     * @param clazz    类
     * @param workbook   工作簿对象
     * @param excelHeads excel与entity对应关系实体
     * @param <T>
     * @return
     * @throws Exception
     */
    private static <T> List<T> readExcel(Class<T> clazz, Workbook workbook, List<ExcelHead> excelHeads, int startRow) throws Exception {
        List<T> beans = new ArrayList<T>();
        int sheetNum = workbook.getNumberOfSheets();
        for (int sheetIndex = 0; sheetIndex < sheetNum; sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            String sheetName = sheet.getSheetName();
            int lastRowNum = sheet.getLastRowNum();
            Row head = sheet.getRow(startRow - 1);
            if (head == null)
                continue;
            short firstCellNum = head.getFirstCellNum();
            short lastCellNum = head.getLastCellNum();
            Field[] fields = clazz.getDeclaredFields();
            for (int rowIndex = startRow; rowIndex <= lastRowNum; rowIndex++) {
                Row dataRow = sheet.getRow(rowIndex);
                if (dataRow == null)
                    continue;
                T instance = clazz.newInstance();
                if (CollectionUtils.isEmpty(excelHeads)) {  //非头部映射方式，默认不校验是否为空，提高效率
                    firstCellNum = dataRow.getFirstCellNum();
                    lastCellNum = dataRow.getLastCellNum();
                }
                for (int cellIndex = firstCellNum; cellIndex < lastCellNum; cellIndex++) {
                    Cell headCell = head.getCell(cellIndex);
                    if (headCell == null)
                        continue;
                    Cell cell = dataRow.getCell(cellIndex);
                    // headCell.setCellType(Cell.CELL_TYPE_STRING);
                    String headName = headCell.getStringCellValue().trim();
                    if (StringUtils.isEmpty(headName)) {
                        continue;
                    }
                    ExcelHead eHead = null;
                    if (!CollectionUtils.isEmpty(excelHeads)) {
                        for (ExcelHead excelHead : excelHeads) {
                            if (headName.equals(excelHead.getExcelName())) {
                                eHead = excelHead;
                                headName = eHead.getEntityName();
                                break;
                            }
                        }
                    }
                    for (Field field : fields) {
                        if (headName.equalsIgnoreCase(field.getName())) {
                            String methodName = MethodUtils.setMethodName(field.getName());
                            Method method = clazz.getMethod(methodName, field.getType());
                            if (isDateFiled(field)) {
                                Date date = null;
                                if (cell != null) {
                                    date = cell.getDateCellValue();
                                }
                                if (date == null) {
                                    volidateValueRequired(eHead, sheetName, rowIndex);
                                    break;
                                }
                                method.invoke(instance, cell.getDateCellValue());
                            } else {
                                String value = null;
                                if (cell != null) {
                                    // cell.setCellType(Cell.CELL_TYPE_STRING);
                                    value = cell.getStringCellValue();
                                }
                                if (StringUtils.isEmpty(value)) {
                                    volidateValueRequired(eHead, sheetName, rowIndex);
                                    break;
                                }
                                method.invoke(instance, convertType(field.getType(), StringUtils.trim(value)));
                            }
                            break;
                        }
                    }
                }
                beans.add(instance);
            }
        }
        return beans;
    }

    /**
     * 是否日期字段
     *
     * @param field
     * @return
     */
    private static boolean isDateFiled(Field field) {
        return (Date.class == field.getType());
    }

    /**
     * 空值校验
     *
     * @param excelHead
     * @throws Exception
     */
    private static void volidateValueRequired(ExcelHead excelHead, String sheetName, int rowIndex) throws Exception {
        if (excelHead != null && excelHead.isRequired()) {
            throw new Exception("《" + sheetName + "》第" + (rowIndex + 1) + "行:\"" + excelHead.getExcelName() + "\"不能为空！");
        }
    }

    /**
     * 类型转换
     *
     * @param clazz
     * @param value
     * @return
     */
    private static Object convertType(Class clazz, String value) {
        if (Integer.class == clazz || int.class == clazz) {
            return Integer.valueOf(value);
        }
        if (Short.class == clazz || short.class == clazz) {
            return Short.valueOf(value);
        }
        if (Byte.class == clazz || byte.class == clazz) {
            return Byte.valueOf(value);
        }
        if (Character.class == clazz || char.class == clazz) {
            return value.charAt(0);
        }
        if (Long.class == clazz || long.class == clazz) {
            return Long.valueOf(value);
        }
        if (Float.class == clazz || float.class == clazz) {
            return Float.valueOf(value);
        }
        if (Double.class == clazz || double.class == clazz) {
            return Double.valueOf(value);
        }
        if (Boolean.class == clazz || boolean.class == clazz) {
            return Boolean.valueOf(value.toLowerCase());
        }
        if (BigDecimal.class == clazz) {
            return new BigDecimal(value);
        }
       /* if (Date.class == clazz) {
            SimpleDateFormat formatter = new SimpleDateFormat(FULL_DATA_FORMAT);
            ParsePosition pos = new ParsePosition(0);
            Date date = formatter.parse(value, pos);
            return date;
        }*/
        return value;
    }

    /**
     * 获取properties的set和get方法
     */
    static class MethodUtils {
        private static final String SET_PREFIX = "set";
        private static final String GET_PREFIX = "get";

        private static String capitalize(String name) {
            if (name == null || name.length() == 0) {
                return name;
            }
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        public static String setMethodName(String propertyName) {
            return SET_PREFIX + capitalize(propertyName);
        }

        public static String getMethodName(String propertyName) {
            return GET_PREFIX + capitalize(propertyName);
        }
    }
}