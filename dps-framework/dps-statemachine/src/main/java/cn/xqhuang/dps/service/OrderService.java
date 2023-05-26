package cn.xqhuang.dps.service;

import cn.xqhuang.dps.entity.Order;
import cn.xqhuang.dps.mapper.OrderMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     *
     * @param: order
     * @return:
     */
    public Order create(Order order);
    /**
     * 对订单进行支付
     *
     * @param: id
     * @return:
     */
    Order pay(Long id);

    /**
     * 对订单进行发货
     *
     * @param: id
     * @return:
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