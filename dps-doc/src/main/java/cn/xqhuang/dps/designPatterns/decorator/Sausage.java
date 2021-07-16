package cn.xqhuang.dps.designPatterns.decorator;
 
public class Sausage extends Decorator  {
    public Sausage(Snack obj) {
        super(obj);
        setDes(" 烤肠 ");
        setPrice(2.0f);
    }
}