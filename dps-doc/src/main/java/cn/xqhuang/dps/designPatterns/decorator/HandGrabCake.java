package cn.xqhuang.dps.designPatterns.decorator;

public class HandGrabCake extends Snack {
    public HandGrabCake() {
        setPrice(5.0f);
        setDes(" 手抓饼 "+ cost());
    }
 
    @Override
    public float cost() {
        return super.getPrice();
    }
}