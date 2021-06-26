package cn.xqhuang.dps.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;

public class ExcelReport implements AutoCloseable {
    private final Workbook workbook;
    private final File file;

    public ExcelReport(String path) throws IOException {
        this(new File(path));
    }

    public ExcelReport(File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        this.workbook = new HSSFWorkbook();
        this.file = file;
    }

    public ExcelReport(FileInputStream inputStream) throws IOException {
        this.workbook = new HSSFWorkbook(inputStream);
        this.file = null;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    public ExcelWorkSheet createSheet(String sheetName) {
        return new ExcelWorkSheet(workbook.createSheet(sheetName));
    }

    public ExcelWorkSheet getSheet(String sheetName){
        return  new ExcelWorkSheet(workbook.getSheet(sheetName));
    }

    public ExcelWorkSheet getSheet(Integer index){
        return  new ExcelWorkSheet(workbook.getSheetAt(index));
    }


    public ExcelReport output() throws IOException {
        try (final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            output(bos);
        }
        return this;
    }

    public ExcelReport output(OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
        return this;
    }


    @Override
    public void close() throws Exception {

    }
}
