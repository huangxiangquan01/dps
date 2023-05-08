package cn.xqhuang.dps.excel.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CellGroup {
    /**
     * 单元格对应的组名
     *
     * @return:
     */
    String name();

    /**
     * 单元格对应的标题
     *
     * @return:
     */
    String title() default "";

    /**
     * 列位置(1开始）
     *
     * @return:
     */
    int index() default -1;
}
