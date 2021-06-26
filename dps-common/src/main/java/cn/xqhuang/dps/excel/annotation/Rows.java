package cn.xqhuang.dps.excel.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Rows {
    Class<?> value();

    /**
     * 单元格所属列对应的分组
     *
     * @return
     */
    CellGroup[] groups() default {};
}
