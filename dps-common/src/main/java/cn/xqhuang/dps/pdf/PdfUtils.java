package cn.xqhuang.dps.pdf;

import cn.xqhuang.dps.utils.DateUtil;
import com.alibaba.fastjson.JSON;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.context.StyleReference;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
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

            File file = new File("/Users/huangxq/Desktop/"+fileName + ".pdf");
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            // url = new File(getContent).toURI().toURL().toString();
            // os = new FileOutputStream(pdfFile);
            os = new ByteArrayOutputStream();

            ITextRenderer3 renderer = new ITextRenderer3(new ITextRenderer());
            renderer.getSharedContext().setReplacedElementFactory(new B64ImgReplacedElementFactory());
            renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
            renderer.setDocumentFromString(content);

            BaseFont font = BaseFont.createFont("/fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setPdfPageEvent(new PageXofYTest2(font));
            // 解决中文不显示问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("/fonts/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);

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
