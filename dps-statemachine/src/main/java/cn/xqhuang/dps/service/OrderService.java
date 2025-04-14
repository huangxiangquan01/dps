package cn.xqhuang.dps.service;

import cn.xqhuang.dps.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {

    Order create(Order order);
    /**
     * 对订单进行支付
     *
     * @param id
     * @return
     */
    Order pay(Long id);
    /**
     * 对订单进行发货
     *
     * @param id
     * @return
     */
    Order deliver(Long id);
    /**
     * 对订单进行确认收货
     *
     * @param id
     * @return
     */
    Order receive(Long id);
}
