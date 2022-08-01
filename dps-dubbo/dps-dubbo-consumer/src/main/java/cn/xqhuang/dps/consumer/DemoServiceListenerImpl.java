package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.service.DemoServiceListener;


public class DemoServiceListenerImpl implements DemoServiceListener {

    @Override
    public void changed(String msg) {
        System.out.println("被回调了："+msg);
    }
}
