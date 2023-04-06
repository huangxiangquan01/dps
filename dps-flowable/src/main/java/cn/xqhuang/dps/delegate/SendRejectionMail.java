package cn.xqhuang.dps.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * @author huangxq
 * @date 2023/04/06 11:23
 */

@Component
public class SendRejectionMail implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("Send Mail "
                + delegateExecution.getVariable("employee"));
    }
}
