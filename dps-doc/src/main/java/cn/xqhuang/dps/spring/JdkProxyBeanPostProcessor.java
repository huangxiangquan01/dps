package cn.xqhuang.dps.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

/**
 *
 */
//@Component
public class JdkProxyBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

	public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {

		// 假设B 被切点命中 需要创建代理
		if(bean instanceof Student/*判断是不是被增强的类，是不是需要创建动态代理*/) {
            JdkDynamicProxy jdkDynimcProxy = new JdkDynamicProxy(bean);
			return  jdkDynimcProxy.getProxy();
		}
		return bean;
	}
}
