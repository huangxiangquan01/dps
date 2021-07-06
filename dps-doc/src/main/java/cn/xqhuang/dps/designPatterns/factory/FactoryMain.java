package cn.xqhuang.dps.designPatterns.factory;
 
public class FactoryMain {
    public static void main(String[] args) {
        Factory f = new IDCardFactory();
        Product p=f.create("江疏影");
        p.use();
        System.out.println("--------------------");
        p=f.create("邱淑贞");
        p.use();
        System.out.println("--------------------");
        f.getAllProductOwner();
    }
}