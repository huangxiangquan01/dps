package cn.xqhuang.dps.designPatterns.decorator;

public class GrilledColdNoodles extends Snack {

    public GrilledColdNoodles() {
        setPrice(4.0f);
        setDes(" 烤冷面 "+cost());
    }
    @Override
    public float cost() {
        return super.getPrice();
    }
}