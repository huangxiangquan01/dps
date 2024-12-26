package cn.xqhuang.dps.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundOrder {

    @ExcelProperty("order_id")
    private String orderId;

    @ExcelProperty("refund_time")
    private String refundTime;
}
