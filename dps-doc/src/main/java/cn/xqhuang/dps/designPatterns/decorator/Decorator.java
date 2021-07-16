package cn.xqhuang.dps.designPatterns.decorator;

public class Decorator extends Snack {
    private Snack obj;
    public Decorator(Snack obj) { //组合
        this.obj = obj;
    }
    @Override
    public float cost() {
        return super.getPrice() + obj.cost();
    }
 
    @Override
    public String getDes() {
        // obj.getDes() 输出被装饰者的信息
        return des + " " + getPrice() + " && " + obj.getDes();
    }
}