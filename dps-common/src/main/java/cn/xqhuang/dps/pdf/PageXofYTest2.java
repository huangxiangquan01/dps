package cn.xqhuang.dps.pdf;
 
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
 
import java.io.File;
import java.io.IOException;
 
/**
 * @author huangxq
 * @create 2021-11-01 22:33
 */
 
public class PageXofYTest2 extends PdfPageEventHelper {
    public PdfTemplate total;
 
    public BaseFont bfChinese;
 
    public PageXofYTest2(BaseFont bfChinese) {
        this.bfChinese = bfChinese;
    }
 
    /**
     * 重写PdfPageEventHelper中的onOpenDocument方法
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        // 得到文档的内容并为该内容新建一个模板
        total = writer.getDirectContent().createTemplate(500, 500);
 
    }
 
    /**
     * 重写PdfPageEventHelper中的onEndPage方法
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        //生成页眉
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Paragraph("泛血管评估诊疗报告", new Font(bfChinese, 24)),
                (document.right() + document.left()) / 2, document.top() - 40, 0);

        ColumnText.showTextAligned(writer.getDirectContentUnder(),
                Element.ALIGN_CENTER, new Paragraph("就诊时间：2022-10-17 08:00  打印时间：2022-10-22 08:00", new Font(bfChinese, 12)),
                (document.right() + document.left()) / 2, document.top() - 65,0);

        ColumnText.showTextAligned(writer.getDirectContentUnder(),
                Element.ALIGN_CENTER, new Paragraph("就诊时间：2022-10-17 08:00  打印时间：2022-10-22 08:00", new Font(bfChinese, 12)),
                (document.right() + document.left()) / 2, document.top() - 90,0);
        // 新建获得用户页面文本和图片内容位置的对象
        PdfContentByte pdfContentByte = writer.getDirectContent();
        // 保存图形状态
        pdfContentByte.saveState();
        String text = "第"+writer.getPageNumber() + "页/";
        // 获取点字符串的宽度
        float textSize = bfChinese.getWidthPoint(text, 9);
        pdfContentByte.beginText();
        // 设置随后的文本内容写作的字体和字号
        pdfContentByte.setFontAndSize(bfChinese, 9);
        // 3.计算前半部分的foot1的长度，后面好定位最后一部分的'Y页'这俩字的x轴坐标，字体长度也要计算进去 = len
        float len = bfChinese.getWidthPoint(text, 9);
        // 定位'X/'
        float x = (document.right() + document.left()) / 2 - len;
        //float y = document.bottom() - 20;
        //System.out.println(document.bottom() +20);
        float y =20f;
        pdfContentByte.setTextMatrix(x, y);
        pdfContentByte.showText(text);
        pdfContentByte.endText();
 
        // 将模板加入到内容（content）中- // 定位'Y'
        pdfContentByte.addTemplate(total, x + textSize, y);//最后第几页的页怎么也和前面的//第几对不齐，然后把y改为y-1,把下面onCloseDocument中的total.setTextMatrix(0, 0);改为//total.setTextMatrix(0, 1);
 
        pdfContentByte.restoreState();
    }
 
    /**
     * 重写PdfPageEventHelper中的onCloseDocument方法
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        total.beginText();
        total.setFontAndSize(bfChinese, 9);
        total.setTextMatrix(0, 0);
        // 设置总页数的值到模板上，并应用到每个界面
        total.showText("共"+String.valueOf(writer.getPageNumber() - 1)+"页");
        total.endText();
    }
}