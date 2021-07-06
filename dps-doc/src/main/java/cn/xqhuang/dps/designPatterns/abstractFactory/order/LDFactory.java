package cn.xqhuang.dps.designPatterns.abstractFactory.order;

import cn.xqhuang.dps.designPatterns.abstractFactory.pizza.LDCheesePizza;
import cn.xqhuang.dps.designPatterns.abstractFactory.pizza.LDPepperPizza;
import cn.xqhuang.dps.designPatterns.abstractFactory.pizza.Pizza;

public class LDFactory implements AbsFactory {
	@Override
	public Pizza createPizza(String orderType) {
		System.out.println("~使用的是抽象工厂模式~");
		Pizza pizza = null;
		if (orderType.equals("cheese")) {
			pizza = new LDCheesePizza();
		} else if (orderType.equals("pepper")) {
			pizza = new LDPepperPizza();
		}
		return pizza;
	}
}