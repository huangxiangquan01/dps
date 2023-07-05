package cn.xqhuang.dps.factory;

import cn.xqhuang.dps.model.MessageModel;
import com.lmax.disruptor.EventFactory;

public class HelloEventFactory implements EventFactory<MessageModel> {
    @Override
    public MessageModel newInstance() {
        return new MessageModel();
    }
}