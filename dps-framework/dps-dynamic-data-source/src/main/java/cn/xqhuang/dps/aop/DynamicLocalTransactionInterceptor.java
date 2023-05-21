/*
 * Copyright © 2018 organization baomidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xqhuang.dps.aop;

import cn.xqhuang.dps.annotation.DSTransactional;
import cn.xqhuang.dps.transaction.TransactionalExecutor;
import cn.xqhuang.dps.transaction.TransactionalInfo;
import cn.xqhuang.dps.transaction.TransactionalTemplate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author huangxq
 */
@Slf4j
public class DynamicLocalTransactionInterceptor {
  private final TransactionalTemplate transactionalTemplate = new TransactionalTemplate();

    @Pointcut("@annotation(cn.xqhuang.dps.annotation.DSTransactional)")
    private void pointcut(){

    }
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        DSTransactional annotation = null;
        Signature signature = joinPoint.getSignature();

        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            if (method != null) {
                annotation = method.getAnnotation(DSTransactional.class);
            }
        }
        final DSTransactional dsTransactional = annotation;
        TransactionalExecutor transactionalExecutor = new TransactionalExecutor() {
            @Override
            public Object execute() throws Throwable {
                return joinPoint.proceed();
            }

            @Override
            public TransactionalInfo getTransactionInfo() {
                TransactionalInfo transactionInfo = new TransactionalInfo();
                if (Objects.nonNull(dsTransactional)) {
                    transactionInfo.setPropagation(dsTransactional.propagation());
                    transactionInfo.setNoRollbackFor(dsTransactional.noRollbackFor());
                    transactionInfo.setRollbackFor(dsTransactional.rollbackFor());
                }
                return transactionInfo;
            }
        };
        return transactionalTemplate.execute(transactionalExecutor);
    }
}
