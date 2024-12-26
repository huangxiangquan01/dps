package cn.xqhuang.dps.utils;

import com.itextpdf.layout.properties.Underline;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;

public class LabelUtils {

    public static void main(String[] args) {
        String sku = "IMMHWW-1029-UK";
        createMathLabel(sku, "/Users/huangxiangquan/IdeaProjects/dps/dps-itextpdf/src/main/resources/fonts1/consola.ttf");
    }

    public static void createMathLabel(String sku, String fontPath) {
        Document document = null;
        PdfWriter writer = null;

        File file = new File("/Users/huangxiangquan/Desktop/1.pdf");
        String[] split = sku.split("-");
        try {
            // 设置基本字体
            BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // 文件内容
            document = new Document(new Rectangle(283, 283), 0, 0, 0, 0);
            // 写入器
            writer = PdfWriter.getInstance(document, Files.newOutputStream(file.toPath()));
            document.open();

            // 单元格中添加表格
            PdfPTable pTable = new PdfPTable(1);
            pTable.setTotalWidth(283);
            pTable.setLockedWidth(true);

            Chunk chunkFirst = new Chunk(split[0], new Font(baseFont, 75, Font.BOLD));
            chunkFirst.setCharacterSpacing(-1f);
            Paragraph p1 = new Paragraph(chunkFirst);
            PdfPCell cellFirst = new PdfPCell(p1);
            cellFirst.setBorder(PdfPCell.NO_BORDER);
            cellFirst.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellFirst.setPaddingTop(split.length == 3 ? 15 : 42);
            pTable.addCell(cellFirst);

            Chunk chunk = new Chunk(split[1], new Font(baseFont, split[1].length() == 4 ? 120 : 100, Font.BOLD));
            chunk.setCharacterSpacing(0);
            chunk.setUnderline(15, -11f);
            Paragraph p2 = new Paragraph(chunk);
            PdfPCell cellSecond = new PdfPCell(p2);
            cellSecond.setBorder(PdfPCell.NO_BORDER);
            cellSecond.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSecond.setPaddingTop(split[1].length() == 4 ? -38 : -40);
            pTable.addCell(cellSecond);
            if (split.length == 3) {
                Chunk chunk3 = new Chunk(split[2], new Font(baseFont, 60, Font.BOLD));
                chunk3.setCharacterSpacing(0);
                Paragraph p3 = new Paragraph(chunk3);
                PdfPCell cell3 = new PdfPCell(p3);
                cell3.setBorder(PdfPCell.NO_BORDER);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setPaddingTop(1);
                pTable.addCell(cell3);
            }

            document.add(pTable);
        } catch (Exception e) {
        } finally {
            if (document != null) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
}
