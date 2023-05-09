package cn.xqhuang.dps.multipleDataSources;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkThread<T> implements Runnable { //建立线程的两种方法 1 实现Runnable 接口 2 继承 Thread 类

    private final BaseMapper<T> mapper;
    private T object;
    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionDefinition transactionDefinition;
    private final CountDownLatch mainThreadLatch;
    private final CountDownLatch rollBackLatch;
    private final AtomicBoolean rollbackFlag;
    private StringBuffer message;

    public WorkThread(BaseMapper<T> mapper,
                      T t,
                      PlatformTransactionManager platformTransactionManager,
                      TransactionDefinition transactionDefinition,
                      CountDownLatch mainThreadLatch,
                      CountDownLatch rollBackLatch,
                      AtomicBoolean rollbackFlag
            , StringBuffer messageFrom) {
        this.mapper = mapper;
        this.object = t;
        this.platformTransactionManager = platformTransactionManager;
        this.transactionDefinition = transactionDefinition;
        this.mainThreadLatch = mainThreadLatch;
        this.rollBackLatch = rollBackLatch;
        this.rollbackFlag = rollbackFlag;
        this.message = messageFrom;
    }

    public void run() {
        try {
            TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
            mapper.insert(object);

            mainThreadLatch.countDown();
            rollBackLatch.await();

            if (rollbackFlag.get()) {
                platformTransactionManager.rollback(transaction);//表示回滚事务；
            } else {
                platformTransactionManager.commit(transaction);//事务提交
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message = message.append(e.getMessage());
            rollbackFlag.set(true);
            mainThreadLatch.countDown();
        }
    }
}