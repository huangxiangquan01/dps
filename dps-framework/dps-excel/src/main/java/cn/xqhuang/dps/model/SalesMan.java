package cn.xqhuang.dps.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesMan {

    @ExcelProperty("销售员工号")
    private String accountName;

    @ExcelProperty("主项目组")
    private String group;
}

