package cn.xqhuang.dps.designPatterns.abstractFactory.order;

import cn.xqhuang.dps.designPatterns.abstractFactory.pizza.Pizza;

public interface AbsFactory {

    Pizza createPizza(String type);
}
