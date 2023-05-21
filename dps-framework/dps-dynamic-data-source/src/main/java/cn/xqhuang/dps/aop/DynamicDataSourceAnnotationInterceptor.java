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

import cn.xqhuang.dps.annotation.DS;
import cn.xqhuang.dps.holder.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Core Interceptor of Dynamic Datasource
 *
 * @author huangxq
 * @since 1.2.0
 */
@Aspect
@Component
public class DynamicDataSourceAnnotationInterceptor{

    @Pointcut("@annotation(cn.xqhuang.dps.annotation.DS)")
    private void pointcut(){
    }
    @Around("pointcut()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        DS annotation = null;

        Signature signature = joinPoint.getSignature();
        String dsKey = "";
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            if (method != null) {
                annotation = method.getAnnotation(DS.class);
                dsKey = annotation.value();
            }
        }
        DynamicDataSourceContextHolder.push(dsKey);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

}
