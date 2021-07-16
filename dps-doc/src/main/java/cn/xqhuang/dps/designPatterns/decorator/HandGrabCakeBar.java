package cn.xqhuang.dps.designPatterns.decorator;
 
public class HandGrabCakeBar {
    public static void main(String[] args) {
        // 装饰者模式下的订单：2个蛋+一根烤肠的手抓饼
        // 1. 点一份手抓饼
        Snack order = new HandGrabCake();
        System.out.println("小白手抓饼费用=" + order.cost());
        System.out.println("描述=" + order.getDes());
 
        // 2. order 加入一个鸡蛋
        order = new Egg(order);
 
        System.out.println("手抓饼 加入1个鸡蛋 费用 =" + order.cost());
        System.out.println("手抓饼 加入1个鸡蛋 描述 = " + order.getDes());
 
        // 3. order 加入一个鸡蛋
 
        order = new Egg(order);
 
        System.out.println("手抓饼 加入1个鸡蛋 加入2个鸡蛋 费用 =" + order.cost());
        System.out.println("手抓饼 加入1个鸡蛋 加入2个鸡蛋 描述 = " + order.getDes());
 
        // 3. order 加入一根烤肠
 
        order = new Sausage(order);
 
        System.out.println("手抓饼 加入1个鸡蛋 加入2个鸡蛋 加1根烤肠 费用 =" + order.cost());
        System.out.println("手抓饼 加入1个鸡蛋 加入2个鸡蛋 加1根烤肠 描述 = " + order.getDes());
 
        System.out.println("===========================");
 
        Snack order2 = new GrilledColdNoodles();
 
        System.out.println("考冷面 费用 =" + order2.cost());
        System.out.println("考冷面 描述 = " + order2.getDes());
 
        // 1. order2 加入一袋金针菇
        order2 = new NeedleMushroom(order2);
        System.out.println("考冷面 加入一袋金针菇  费用 =" + order2.cost());
        System.out.println("考冷面 加入一袋金针菇 描述 = " + order2.getDes());
 
    }
}