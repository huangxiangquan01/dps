package cn.xqhuang.dps.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单位转换
 *
 * @author huangxiangquan@yintatech.com
 * @date 2024/01/03 16:06
 */
@Slf4j
public class UnitConvertUtil {
    /**
     * 单位转换类型
     *
     * @author huangxiangquan@yintatech.com
     * @date 2024/01/03 16:07
     */
    public enum UnitConvertType {

        /**
         * 精度
         */
        R,
        /**
         * 万元
         */
        B,
        /**
         * 百分
         */
        PERCENTAGE,
        /**
         * 千分
         */
        PERMIL
    }

    public static <T> void unitMapConvert(List<T> list, Map<String, UnitConvertType> propertyMap) {
        //遍历list
        for (T t : list) {
            Field[] declaredFields = t.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                //匹配map中的列按单位转换
                if (propertyMap.keySet().stream().anyMatch(x -> x.equals(declaredField.getName()))) {
                    try {
                        declaredField.setAccessible(true);
                        Object o = declaredField.get(t);
                        UnitConvertType unitConvertType = propertyMap.get(declaredField.getName());
                        if (o != null) {
                            if (unitConvertType.equals(UnitConvertType.PERCENTAGE)) {
                                BigDecimal bigDecimal = ((BigDecimal) o).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                declaredField.set(t, bigDecimal);
                            }
                            if (unitConvertType.equals(UnitConvertType.PERMIL)) {
                                BigDecimal bigDecimal = ((BigDecimal) o).multiply(new BigDecimal(1000)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                declaredField.set(t, bigDecimal);
                            }
                            if (unitConvertType.equals(UnitConvertType.B)) {
                                BigDecimal bigDecimal = ((BigDecimal) o).divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                declaredField.set(t, bigDecimal);
                            }
                            if (unitConvertType.equals(UnitConvertType.R)) {
                                BigDecimal bigDecimal = ((BigDecimal) o).setScale(2, BigDecimal.ROUND_HALF_UP);
                                declaredField.set(t, bigDecimal);
                            }
                        }
                    } catch (Exception ex) {
                        log.error("转换异常", ex);
                    }

                }
            }
        }
    }
}
