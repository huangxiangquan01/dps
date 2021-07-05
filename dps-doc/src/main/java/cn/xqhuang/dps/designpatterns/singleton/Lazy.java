package cn.xqhuang.dps.designpatterns.singleton;


/**
 *
 */
public class Lazy {

    private Lazy(){}

    public static volatile Lazy singleton = null;

    public static Lazy instances() {
        if (singleton == null) {   //  效率
            synchronized (Lazy.class) {
                if (singleton == null) {   // 避免同步
                    singleton = new Lazy();
                }
            }
        }
        return singleton;
    }
}
