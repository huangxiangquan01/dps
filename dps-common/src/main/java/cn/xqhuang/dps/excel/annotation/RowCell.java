package cn.xqhuang.dps.excel.annotation;


import cn.xqhuang.dps.excel.NewCellType;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RowCell {
    /**
     * 单元格对应的标题
     *
     * @return
     */
    String title();

    /**
     * 列位置(1开始）
     *
     * @return
     */
    int index();

    /**
     * 单元格宽度
     *
     * @return
     */
    int width() default 20;

    /**
     * 单元格类型
     *
     * @return
     */
    NewCellType type() default NewCellType.STRING;

    /**
     * 日期格式化字符串
     *
     * @return
     */
    String format() default "";

    /**
     * 单元格所属列对应的分组
     *
     * @return
     */
    CellGroup[] groups() default {};

    /**
     * 标题字体红颜色
     */
    boolean isRed() default false;
}
