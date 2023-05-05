package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.entity.SystemUsers;
import cn.xqhuang.dps.mapper.SystemUserMapper;
import cn.xqhuang.dps.multipleDataSources.WorkThread;
import cn.xqhuang.dps.service.SystemUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 后台用户 Service 实现类
 *
 * @author huangxq
 */
@Service("systemUserService")
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Resource
    private PlatformTransactionManager platformTransactionManager;
    @Resource
    private TransactionDefinition transactionDefinition;

    @Override
    public SystemUsers getUser(Long id) {
        return systemUserMapper.selectById(id);
    }

    @Override
    public void updateListById(List<SystemUsers> users) {
        try {
            //子线程
            CountDownLatch rollBackLatch = new CountDownLatch(1);

            //主线程
            CountDownLatch mainThreadLatch = new CountDownLatch(users.size());

            AtomicBoolean rollbackFlag = new AtomicBoolean(false);
            StringBuffer message = new StringBuffer();
            message.append("报错信息：");

            //子线程 这里的count代表有几个线程
            for (SystemUsers user : users) {
                user.setRemark("2");
                Thread g = new Thread(new WorkThread<>(systemUserMapper, user,
                        platformTransactionManager, transactionDefinition,
                        mainThreadLatch, rollBackLatch, rollbackFlag, message));
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
