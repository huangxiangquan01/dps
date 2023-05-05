package cn.xqhuang.dps.multipleDataSources;

import cn.xqhuang.dps.mapper.SystemUserMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class EntryController {

    @Resource
    private PlatformTransactionManager platformTransactionManager;
    @Resource
    private TransactionDefinition transactionDefinition;
    @Resource
    private SystemUserMapper systemUserMapper;

    public void importData(){
        try {
            //子线程
            CountDownLatch rollBackLatch  = new CountDownLatch(1);

            //主线程
            CountDownLatch mainThreadLatch = new CountDownLatch(2);

            AtomicBoolean rollbackFlag = new AtomicBoolean(false);
            StringBuffer message = new StringBuffer();
            message.append("报错信息：");

            //子线程 这里的count代表有几个线程
            for(int i=0;i< 20 ;i++) {


                Thread g = new Thread(new WorkThread<>(systemUserMapper, null,
                        platformTransactionManager, transactionDefinition,
                        mainThreadLatch,rollBackLatch,rollbackFlag, message));
                g.start();
            }
//            for(Thread thread:list){
//                System.out.println(thread.getState());
//                thread.join();//是等待这个线程结束;
//            }
            mainThreadLatch.await();
            //所有等待的子线程全部放开
            rollBackLatch.countDown();

            //是主线程等待子线程的终止。也就是说主线程的代码块中，如果碰到了t.join()方法，此时主线程需要等待（阻塞），等待子线程结束了(Waits for this thread to die.),才能继续执行t.join()之后的代码块。
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}