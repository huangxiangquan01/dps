package cn.xqhuang.dps.utils;

import com.google.common.collect.Maps;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;

/**
 * @author huangxq
 * @create 2021-11-01 22:33
 * @description FREEMARKER 模板工具类
 */
public class FreeMarkerUtil {

    private static final String WINDOWS_SPLIT = "\\";

    private static final String UTF_8 = "UTF-8";

    private final static Map<String, FileTemplateLoader> fileTemplateLoaderCache = Maps.newConcurrentMap();

    private final static Map<String, Configuration> configurationCache = Maps.newConcurrentMap();

    /**
     * @description 获取模板
     */
    public static String getContent(String fileName, Object data){
        String templatePath = getPDFTemplatePath(fileName);
        String templateFileName = getTemplateName(templatePath);
        String templateFilePath = getTemplatePath(templatePath);
        if (StringUtils.isEmpty(templatePath)) {
            throw new RuntimeException("templatePath can not be empty!");
        }
        try {
            Template template = getConfiguration(templateFilePath).getTemplate(templateFileName);
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            writer.flush();
            return writer.toString();
        } catch (Exception ex) {
            throw new RuntimeException("FreeMarkerUtil process fail", ex);
        }
    }

    public static Configuration getConfiguration(String templateFilePath) throws Exception {
        if (null != configurationCache.get(templateFilePath)) {
            return configurationCache.get(templateFilePath);
        }
        Configuration config = new Configuration(Configuration.VERSION_2_3_25);
        config.setDefaultEncoding(UTF_8);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        FileTemplateLoader fileTemplateLoader = null;
        if (null != fileTemplateLoaderCache.get(templateFilePath)) {
            fileTemplateLoader = fileTemplateLoaderCache.get(templateFilePath);
        }
        try {
            fileTemplateLoader = new FileTemplateLoader(new File(templateFilePath));
            fileTemplateLoaderCache.put(templateFilePath, fileTemplateLoader);
        } catch (IOException e) {
            throw new Exception("fileTemplateLoader init error!", e);
        }
        config.setTemplateLoader(fileTemplateLoader);
        configurationCache.put(templateFilePath, config);
        return config;

    }

    /**
     * 获取模板路径
     *
     * @param templatePath 模板路径
     * @return String
     */
    private static String getTemplatePath(String templatePath) {
        if (StringUtils.isEmpty(templatePath)) {
            return "";
        }
        if (templatePath.contains(WINDOWS_SPLIT)) {
            return templatePath.substring(0, templatePath.lastIndexOf(WINDOWS_SPLIT));
        }
        return templatePath.substring(0, templatePath.lastIndexOf("/"));
    }

    /**
     * 获取模板文件名
     *
     * @param templatePath 模板路径
     * @return String
     */
    private static String getTemplateName(String templatePath) {
        if (StringUtils.isEmpty(templatePath)) {
            return "";
        }
        if (templatePath.contains(WINDOWS_SPLIT)) {
            return templatePath.substring(templatePath.lastIndexOf(WINDOWS_SPLIT) + 1);
        }
        return templatePath.substring(templatePath.lastIndexOf("/") + 1);
    }

    /**
     * @param fileName PDF文件名
     * @return 匹配到的模板名
     * @description 获取PDF的模板路径,
     * 默认按照PDF文件名匹对应模板
     */
    public static String getPDFTemplatePath(String fileName) {
        String templatePath = System.getProperty("user.dir") + "/file/pdfkit/templates";
        File file = new File(templatePath);
        if (!file.isDirectory()) {
            throw new RuntimeException("PDF模板文件不存在,请检查templates文件夹!");
        }
        String pdfFileName = fileName.substring(0, fileName.lastIndexOf("."));
        File defaultTemplate = null;
        File matchTemplate = null;
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (!f.isFile()) {
                continue;
            }
            String templateName = f.getName();
            if (templateName.lastIndexOf(".ftl") == -1) {
                continue;
            }
            if (defaultTemplate == null) {
                defaultTemplate = f;
            }
            if (StringUtils.isEmpty(fileName)) {
                break;
            }
            templateName = templateName.substring(0, templateName.lastIndexOf("."));
            if (templateName.equalsIgnoreCase(pdfFileName)) {
                matchTemplate = f;
                break;
            }
        }
        if (matchTemplate != null) {
            return matchTemplate.getAbsolutePath();
        }
        if (defaultTemplate != null) {
            return defaultTemplate.getAbsolutePath();
        }
        return null;
    }
}
