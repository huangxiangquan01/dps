package cn.xqhuang.dps.designPatterns.abstractFactory.order;

import cn.xqhuang.dps.designPatterns.abstractFactory.pizza.BJCheesePizza;
import cn.xqhuang.dps.designPatterns.abstractFactory.pizza.BJPepperPizza;
import cn.xqhuang.dps.designPatterns.abstractFactory.pizza.Pizza;

public class BJFactory implements AbsFactory {

    @Override
    public Pizza createPizza(String orderType) {
        System.out.println("~使用的是抽象工厂模式~");
        Pizza pizza = null;
        if(orderType.equals("cheese")) {
            pizza = new BJCheesePizza();
        } else if (orderType.equals("pepper")){
            pizza = new BJPepperPizza();
        }
        return pizza;

    }
}
