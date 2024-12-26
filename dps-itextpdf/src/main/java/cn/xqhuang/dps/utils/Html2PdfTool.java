package cn.xqhuang.dps.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.text.pdf.PdfWriter;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Objects;

public class Html2PdfTool {
    /**
     * windows运行环境需要处理第一个/,根据自身环境决定是否执行该方法
     */
    public static final String PATH = Objects.requireNonNull(Html2PdfTool.class.getResource("/")).getPath();

    /**
     * @Description    ：htmlFile2pdfFile
     * @Method_Name    ：html2Pdf 
     * @param htmlPath   html文件地址
     * @param pdfPath    pdf文件地址
     */
    public static void html2Pdf(String htmlPath,String pdfPath){
        //读取的html文件地址
        File htmlFile = new File(PATH + htmlPath);
        //生成的pdf地址
        File pdfFile = new File(PATH + pdfPath);
        try {
            //HtmlFile TO PdfFile
            HtmlConverter.convertToPdf(htmlFile,pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description    ：html文件转换为pdf文件
     * @Method_Name    ：html2Pdf 
     * @param htmlPath   html文件地址
     * @param pdfPath    pdf文件地址
     * @param converterProperties   转换时属性设置
     */
    public static void html2Pdf(String htmlPath, String pdfPath, ConverterProperties converterProperties){
        //读取的html文件地址
        File htmlFile = new File(PATH + htmlPath);
        //生成的pdf地址
        File pdfFile = new File(PATH + pdfPath);
        try {
            //HtmlFile TO PdfFile
            HtmlConverter.convertToPdf(htmlFile,pdfFile,converterProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description    ：html字符串转换为pdf文件 
     * @Method_Name    ：htmlStr2Pdf 
     * @param htmlStr    字符串格式的html
     * @param pdfPath    pdf文件地址
     */
    public static void htmlStr2Pdf(String htmlStr, String pdfPath){
        File pdfFile = new File(Html2PdfTool.PATH + pdfPath);
        try(OutputStream outputStream = new FileOutputStream(pdfFile)){
            HtmlConverter.convertToPdf(htmlStr,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description    ：html字符串转换为pdf文件 
     * @Method_Name    ：htmlStr2Pdf 
     * @param htmlStr    字符串格式的html
     * @param pdfPath    pdf文件地址
     * @param converterProperties 
     */
    public static void htmlStr2Pdf(String htmlStr,String pdfPath, ConverterProperties converterProperties){
        File pdfFile = new File(Html2PdfTool.PATH + pdfPath);
        try(OutputStream outputStream = new FileOutputStream(pdfFile)){
            HtmlConverter.convertToPdf(htmlStr, outputStream, converterProperties);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * @Description    ：将html文件转换为Str 
     * @Method_Name    ：html2Str 
     * @param htmlPath   html文件地址
     * @return java.lang.String  
     */
    public static String html2Str(String htmlPath){
        //读取的html文件地址
        File htmlFile = new File(Html2PdfTool.PATH + htmlPath);
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(htmlFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)){
            String htmlStr = bufferedReader.readLine();
            stringBuilder.append(htmlStr);
            while (htmlStr != null){
                stringBuilder.append(htmlStr);
                htmlStr = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * @Description    ：获取设置了中文ttf字体的converterProperties
     */
    public static ConverterProperties getCnConverterProperties(){
        ConverterProperties converterProperties = new ConverterProperties();
        //设置中文字体，ttf文件夹下SimSum-01和Dengb分别支持细字体和粗字体，缺一不可
        FontProvider fontProvider = new FontProvider();
        fontProvider.addDirectory("/Users/huangxiangquan/IdeaProjects/dps/dps-itextpdf/src/main/resources/fonts/");
        converterProperties.setFontProvider(fontProvider);

        return converterProperties;
    }

    public static void htmlStr2Pdf(String htmlStr){
        File pdfFile = new File("/Users/huangxiangquan/Desktop/2.pdf");
        try {
            if (!pdfFile.exists()) {
                pdfFile.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(pdfFile);

            com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(outputStream);
            // 创建PdfDocument实例
            com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            // 设置页面大小
            pdf.setDefaultPageSize(new PageSize( 842.0F, 595.0F));

            HtmlConverter.convertToPdf(htmlStr, pdf, getCnConverterProperties());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getContent(String fileName, Object data){
        try {
            Configuration config = new Configuration(Configuration.VERSION_2_3_25);
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setLogTemplateExceptions(false);
            ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(Html2PdfTool.class, "/template/");
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

    public static void main(String[] args) {
        // 文件路径
        String filePath = "/Users/huangxiangquan/IdeaProjects/dps/dps-itextpdf/src/main/resources/template/0619.html";
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // 循环读取每一行直到文件结束
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            // 处理文件读取异常
            e.printStackTrace();
        }

        Html2PdfTool.htmlStr2Pdf(sb.toString());
    }
}
