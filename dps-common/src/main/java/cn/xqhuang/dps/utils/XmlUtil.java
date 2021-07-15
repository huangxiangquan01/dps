package cn.xqhuang.dps.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

/**
 * XML与Java实体类转换工具
 *
 * @author huangxq
 * @date 2021-07-15
 *
 */
public class XmlUtil {

    public static XmlMapper xmlMapper;

    static {
        xmlMapper = new XmlMapper();
        // SimpleModule module = new SimpleModule();
        // xmlMapper.registerModule(module);

        //反序列化时，若实体类没有对应的属性，是否抛出JsonMappingException异常，false忽略掉
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //序列化是否绕根元素，true，则以类名为根元素
        xmlMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        //忽略空属性
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //XML标签名:使用骆驼命名的属性名，
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        //设置转换模式
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
    }

    /**
     * @param object
     * @return
     * @Description 将java对象转化为xml字符串
     * @Title bean2Xml
     */
    public static String bean2Xml(Object object) {
        try {
            return xmlMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Java对象转换为Xml失败", e);
        }
    }

    /**
     * @param xml
     * @param clazz
     * @return
     * @Description 将xml字符串转化为java对象
     * @Title xml2Bean
     * */
    public static <T> T xml2Bean(String xml, Class<T> clazz) {
        try {
            return xmlMapper.readValue(xml, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Xml转换为Java对象失败", e);
        }
    }
}
