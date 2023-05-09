package cn.xqhuang.dps.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author: ZH
 * @create: 2021-12-03 16:30
 */
public class DownloadProcessor {
    private final HttpServletResponse response;

    private final ExcelWriter writer;

    private final Workbook workbook;

    public DownloadProcessor(HttpServletResponse response, ExcelWriter writer, String fileName) throws Exception {
        fileName = fileName + System.currentTimeMillis();
        this.response = response;
        this.response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        this.response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        this.response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        this.writer = writer;
        this.workbook = writer.getWorkbook();
    }


    public void processData(Map<String, Object> dataMap) {
        this.writer.write(CollUtil.newArrayList(dataMap));
    }

    public void moreSheetProcessData(Object[] datas,String sheetName) {
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int i = 0; i < datas.length; i++) {
                Object value = datas[i];
                if (value instanceof Double){
                    row.createCell(i).setCellValue((Double) value);
                }else if (value instanceof String){
                    row.createCell(i).setCellValue((String) value);
                }else if (value instanceof Integer){
                    row.createCell(i).setCellValue((Integer) value);
                }else if (value instanceof Date){
                    row.createCell(i).setCellValue((Date) value);
                }else if (value instanceof LocalDateTime){
                    // row.createCell(i).setCellValue((LocalDateTime) value);
                }else if (value instanceof LocalDate){
                    // row.createCell(i).setCellValue((LocalDate) value);
                }else if (value instanceof Calendar){
                    row.createCell(i).setCellValue((Calendar) value);
                }else if (value instanceof RichTextString){
                    row.createCell(i).setCellValue((RichTextString) value);
                }else if (value instanceof Boolean){
                    row.createCell(i).setCellValue((Boolean) value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
