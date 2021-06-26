package cn.xqhuang.dps.excel.style;


import lombok.Getter;
import lombok.Setter;

/**
 *  字段属性
 */
@Getter
@Setter
public class ExcelCell{

    private Integer defaultIndex;

    private Integer index;

    private String fieldName;

    private Boolean isExport=true;

    private String filed;

}
