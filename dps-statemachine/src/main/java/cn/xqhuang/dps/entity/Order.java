package cn.xqhuang.dps.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单
 *
 * @author huangxiangquan@yintatech.com
 * @date 2025/04/14 11:25
 */
@Getter
@Setter
public class Order {

    private Long id;

    private String orderCode;

    private Integer status;
}
