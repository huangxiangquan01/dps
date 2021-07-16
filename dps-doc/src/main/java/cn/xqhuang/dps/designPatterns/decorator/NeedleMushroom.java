package cn.xqhuang.dps.designPatterns.decorator;

public class NeedleMushroom extends Decorator{
    public NeedleMushroom(Snack obj) {
        super(obj);
        setDes(" 金针菇 ");
        setPrice(2.5f);
    }
}