/*
package cn.xqhuang.dps.pdf;

import cn.xqhuang.dps.utils.FreeMarkerUtil;
import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;


*/
/**
 * @author jjsun7
 * @ClassName PDFKit
 * @Description pdf处理类
 * @Date 2020/12/17 10:58
 **//*


public class PDFKit {


    private String saveFilePath;

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    */
/**
     * @description 创建默认保存路径
     *//*

    private  String  getDefaultSavePath(String fileName){
        String classpath = System.getProperty("user.dir");
        String saveFilePath = classpath + "/file/pdf/" + fileName;
        File f = new File(saveFilePath);
        if(!f.getParentFile().exists()){
            f.mkdirs();
        }
        return saveFilePath;
    }

    */
/**
     * @description 获取字体设置路径
     *//*

    public static String getFontPath() {
        String classpath = System.getProperty("user.dir");
        String fontpath = classpath + "/file/pdfkit/fonts";
        return fontpath;
    }


    */
/** 每行显示数量 **//*

    public static int num = 27;
    */
/** 换行符 **//*

    public static String LINE_BREAK = "\n";

    */
/**
     * 插入方法
     *
     * @param num
     *            每隔几个字符插入一个字符串（中文字符）
     * @param splitStr
     *            待指定字符串
     * @param str
     *            原字符串
     * @return 插入指定字符串之后的字符串
     * @throws UnsupportedEncodingException
     *//*

    public static String addStr(int num, String splitStr, String str) throws Exception {
        StringBuffer sb = new StringBuffer();
        String temp = str;

        int len = str.length();
        while (len > 0) {
            int idx = getEndIndex(temp, num);
            sb.append(temp.substring(0, idx + 1)).append(splitStr);
            temp = temp.substring(idx + 1);
            len = temp.length();
        }

        return sb.toString();
    }

    */
/**
     * 两个数字/英文
     *
     * @param str
     *            字符串
     * @param num
     *            每隔几个字符插入一个字符串
     * @return int 最终索引
     * @throws UnsupportedEncodingException
     *//*

    public static int getEndIndex(String str, double num) throws Exception {
        int idx = 0;
        double val = 0.00;
        // 判断是否是英文/中文
        for (int i = 0; i < str.length(); i++) {
            if (String.valueOf(str.charAt(i)).getBytes("UTF-8").length >= 3) {
                // 中文字符或符号
                val += 1.00;
            } else {
                // 英文字符或符号
                val += 0.50;
            }
            if (val >= num) {
                idx = i;
                if (val - num == 0.5) {
                    idx = i - 1;
                }
                break;
            }
        }
        if (idx == 0) {
            idx = str.length() - 1;
        }
        return idx;
    }


    */
/**
     * ty生成pdf
     * @param fileName 模板文件名称
     * @param data 数据对象
     * @return
     *//*

    public String exportToPdf(String fileName,Object data){
        String htmlData= FreeMarkerUtil.getContent(fileName, data);
        if(StringUtils.isEmpty(saveFilePath)){
            saveFilePath = getDefaultSavePath(fileName);
        }
        File file=new File(saveFilePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream=null;
        try{
            //设置输出路径
            outputStream = new FileOutputStream(saveFilePath);
            ITextRenderer renderer = new ITextRenderer();
            // 图片base64支持，把图片转换为itext自己的图片对象
            renderer.getSharedContext().setReplacedElementFactory(new B64ImgReplacedElementFactory());
            renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
            renderer.setDocumentFromString(htmlData);
            // 解决中文支持问题  
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(getFontPath()+"/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(outputStream);

            outputStream.close();
        }catch(Exception ex){
            throw new RuntimeException("PDF export to File fail",ex);
        }
        return saveFilePath;
    }

    */
/**
     * 生成pdf
     * @param fileName 模板文件名称
     * @param data 数据对象
     * @return pdf文件路径
     *//*

    public String exportToPdfNew(String fileName, Object data){
        try {
            String htmlData= FreeMarkerUtil.getContent(fileName, data);
            if(StringUtils.isEmpty(saveFilePath)){
                saveFilePath = getDefaultSavePath(fileName);
            }
            File file=new File(saveFilePath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ITextRenderer3 renderer = new ITextRenderer3();
            // 图片base64支持，把图片转换为itext自己的图片对象
            renderer.getSharedContext().setReplacedElementFactory(new B64ImgReplacedElementFactory());
            renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
            renderer.setDocumentFromString(htmlData);

            BaseFont font = BaseFont.createFont(getFontPath()+"/SIMSUN.TTC,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setPdfPageEvent(new PageXofYTest2(font));
            // 解决中文支持问题  
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(getFontPath()+"/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);

            //添加字体库end
            renderer.setDocumentFromString(htmlData);
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
            byte[] buff = os.toByteArray();
            //保存到磁盘上
            this.byte2File(buff,file);
        } catch (Exception e) {
            throw new RuntimeException("PDF export to File fail：",e);
        }
        return saveFilePath;
    }

    */
/**
     * 根据byte数组，生成文件
     *
     * @param bfile    文件数组
     * @param file 文件
     *//*

    public void byte2File(byte[] bfile, File file) {
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
}
*/
