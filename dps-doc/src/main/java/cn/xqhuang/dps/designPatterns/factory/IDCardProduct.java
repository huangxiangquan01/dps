package cn.xqhuang.dps.designPatterns.factory;
 
public class IDCardProduct extends Product {
    String owner;
 
    public IDCardProduct(String owner) {
        this.owner = owner;
    }
 
    @Override
    public void use() {
        System.out.println("用户 "+owner+" 正在使用产品...");
    }
 
    public String getOwner() {
        return owner;
    }
}