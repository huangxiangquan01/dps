package cn.xqhuang.dps.designPatterns.abstractFactory;

import cn.xqhuang.dps.designPatterns.abstractFactory.order.BJFactory;
import cn.xqhuang.dps.designPatterns.abstractFactory.order.OrderPizza;

public class PizzaStore {
	public static void main(String[] args) {
		new OrderPizza(new BJFactory());
	}
}