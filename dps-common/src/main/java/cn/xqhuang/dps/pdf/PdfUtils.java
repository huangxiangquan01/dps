package cn.xqhuang.dps.pdf;

import cn.xqhuang.dps.utils.DateUtil;
import com.alibaba.fastjson.JSON;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.net.FileRetrieve;
import com.itextpdf.tool.xml.net.ReadingProcessor;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.*;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.xhtmlrenderer.context.StyleReference;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author huangxq
 * @create 2021-11-01 22:33
 * @date 2022/11/1 22:21
 */
public class PdfUtils {

    public static void html2pdf(String fileName, Object data)  {
        // step 1
        ByteArrayOutputStream os = null;
        try {
            String content = getContent(fileName, data);
            System.out.println(content);
            File file = new File("/Users/huangxiangquan/Desktop/2.pdf");
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            // String url = new File(content).toURI().toURL().toString();
            // os = new FileOutputStream(pdfFile);
            os = new ByteArrayOutputStream();

            ITextRenderer3 renderer = new ITextRenderer3(new ITextRenderer());
            renderer.getSharedContext().setReplacedElementFactory(new B64ImgReplacedElementFactory());
            renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
            renderer.setDocumentFromString(content);

            // BaseFont font = BaseFont.createFont("/fonts/simsun.ttc, 0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // renderer.setPdfPageEvent(new PageXofYTest2(font));
            // 解决中文不显示问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("/fonts/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os, true);

            renderer.finishPDF();
            byte[] buff = os.toByteArray();

            byte2File(buff, file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void html2pdf1(String fileName, Object data)  {
        // step 1
        try {
            String purchaseOrder = getContent(fileName, data);

            // 创建Document对象
            Document document = new Document(new RectangleReadOnly(842.0F, 595.0F));
            // 创建PdfWriter对象
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("/Users/huangxiangquan/Desktop/1.pdf")));

            // 添加页眉页脚

            // 设置字体
            //BaseFont base = BaseFont.createFont("classpath:/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // 打开Document
            document.open();
            // 加载HTML文件
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(purchaseOrder.getBytes(StandardCharsets.UTF_8)),
                    PdfUtils.class.getResourceAsStream(""), StandardCharsets.UTF_8,
                    new XMLWorkerFontProvider() {
                        @Override
                        public Font getFont(final String fontName, final String encoding, final boolean embedded, final float size, final int style, final BaseColor color) {
                            BaseFont bf = null;
                            try {
                                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
                                Font font = new Font(bf, size, style, color);
                                font.setColor(color);
                                // log.info("PDF文档字体初始化完成!");
                                return font;
                            } catch (Exception e) {

                            }
                            return null;
                        }
                    });

            // 关闭Document
            document.close();
            System.out.println("PDF转换完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据byte数组，生成文件
     *
     * @param bfile    文件数组
     * @param file 文件
     */
    public static void byte2File(byte[] bfile, File file) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            throw new RuntimeException("根据byte数组，生成文件 fail：",e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                throw new RuntimeException("关闭流异常：",e);
            }

        }
    }

    public static String getContent(String fileName, Object data){
        try {
            Configuration config = new Configuration(Configuration.VERSION_2_3_25);
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setLogTemplateExceptions(false);
            ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(PdfUtils.class, "/template/");
            config.setTemplateLoader(classTemplateLoader);
            Template template = config.getTemplate(fileName + ".ftl");
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            writer.flush();
            return writer.toString();
        } catch (Exception ex) {
            throw new RuntimeException("FreeMarkerUtil process fail", ex);
        }
    }
}
