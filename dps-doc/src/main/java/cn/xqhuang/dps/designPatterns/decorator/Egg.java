package cn.xqhuang.dps.designPatterns.decorator;

public class Egg extends Decorator {
    public Egg(Snack obj) {
        super(obj);
        setDes(" 鸡蛋 ");
        setPrice(1.0f);
    }
}