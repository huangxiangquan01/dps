package cn.xqhuang.dps.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GocOrder {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("order_id")
    private String orderId;

    @ExcelProperty("original_order_id")
    private String originalOrderId;

    @ExcelProperty("platform_order_no")
    private String platformOrderNo;
}

