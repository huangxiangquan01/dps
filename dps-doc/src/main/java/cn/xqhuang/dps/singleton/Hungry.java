package cn.xqhuang.dps.singleton;

/**
 * @author huangxq
 * @Description
 */
public class Hungry {

    private Hungry(){}

    public final static Hungry singleton = new Hungry();

    public static Hungry instance() {
        return singleton;
    }

}

