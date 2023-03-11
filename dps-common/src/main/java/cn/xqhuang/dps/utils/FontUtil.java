package cn.xqhuang.dps.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author huangxq
 * @create 2021-11-01 22:33
 */
public class FontUtil {

    private static  final Map<String,Font> fontCache= Maps.newConcurrentMap();
    /**
     * @description 加载自定义字体
     */
    public static Font loadFont(String fontFileName, int style, float fontSize) throws IOException {
        FileInputStream fis=null;
        String key=fontFileName+"|"+style;
        Font dynamicFont=fontCache.get(key);
        if(dynamicFont==null){
            try{
                File file = new File(fontFileName);
                fis= new FileInputStream(file);
                dynamicFont = Font.createFont(style, fis);
                fontCache.put(key,dynamicFont);

            }catch(Exception e){
                return new Font("宋体", Font.PLAIN, 14);
            }finally{
                fis.close();
            }
        }
        Font dynamicFontPt = dynamicFont.deriveFont(fontSize);

        return dynamicFontPt;
    }
}
