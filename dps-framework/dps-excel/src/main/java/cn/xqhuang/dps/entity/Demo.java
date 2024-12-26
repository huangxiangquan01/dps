package cn.xqhuang.dps.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Demo {

/*    @ExcelProperty("cVenHeadCode")
    private String venHeadCode;*/

    @ExcelProperty("GROUP_CONCAT(id, '')")
    private String level;
}
