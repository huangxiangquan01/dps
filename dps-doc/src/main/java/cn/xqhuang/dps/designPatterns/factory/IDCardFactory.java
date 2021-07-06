package cn.xqhuang.dps.designPatterns.factory;
 
import java.util.ArrayList;
import java.util.List;
 
public class IDCardFactory extends Factory {

    List owners = new ArrayList();

    public Product createProduct(String owner){
        System.out.println("为 "+owner+" 创造产品成功...");
        return new IDCardProduct(owner);
    }
 
    @Override
    public void registerProduct(Product p) {
        String owner=((IDCardProduct)p).getOwner();
        owners.add(owner);
        System.out.println("注册 "+owner+" 的产品成功...");
    }
 
    @Override
    public void getAllProductOwner() {
        for(int i=0;i<owners.size();i++){
            System.out.println("产品用户："+owners.get(i));
        }
    }
}