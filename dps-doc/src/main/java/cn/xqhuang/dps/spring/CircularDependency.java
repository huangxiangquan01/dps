package cn.xqhuang.dps.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huangxq
 * @description 循环依赖
 */
public class CircularDependency {

    private static Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public static void loadBeanDefinitions() {
        beanDefinitionMap.put("person", new RootBeanDefinition(Person.class));
        beanDefinitionMap.put("student", new RootBeanDefinition(Student.class));
    }

    public static void main(String[] args) {
        loadBeanDefinitions();

        for (String key: beanDefinitionMap.keySet()) {
            try {
                getBean(key);
            } catch (Exception e) {

            }

        }
    }

    //一级缓存 解决循环依赖
    public static Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    //二级缓存 将成熟的bean和纯净的bean分离，避免读取到不完整的bean
    public static Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    //三级缓存
    public static Map<String, ObjectFactory> singletonFactories = new ConcurrentHashMap<>();

    //循环依赖的标示
    private static Set<String> singletonsCurrennlyCreations = new HashSet<>();

    public static Object getBean(String beanName) throws InstantiationException, IllegalAccessException {
        //
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return singleton;
        }
        if (!singletonsCurrennlyCreations.contains(beanName)) {
            singletonsCurrennlyCreations.add(beanName);
        }

        RootBeanDefinition beanDefinition = (RootBeanDefinition) beanDefinitionMap.get(beanName);
        Class<? extends RootBeanDefinition> clazz = beanDefinition.getClass();
        // 实例化
        Object obj = clazz.newInstance();

        //后置处理器 创建动态代理
        // 创建动态代理 利用后置处理器
        // 只有在循环依赖的情况下在实例化后创建proxy   判断当前是不是循环依赖
        singletonFactories.put(beanName, () ->
                new JdkProxyBeanPostProcessor().getEarlyBeanReference(earlySingletonObjects.get(beanName), beanName)
        );

        // 属性赋值
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (annotation != null) {
                field.setAccessible(true);
                //
                String name = field.getName();
                Object fieldObject = getBean(name);
                field.set(obj, fieldObject);
            }
        }

        //initMethod

        // afterInitializationBeanPostProcessor

        if (earlySingletonObjects.containsKey(beanName)) {
            obj =  earlySingletonObjects.get(beanName);
            earlySingletonObjects.remove(beanName);
        }
        singletonObjects.put(beanName, obj);

        singletonsCurrennlyCreations.remove(beanName);

        return obj;
    }

    public static Object getSingleton(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if (bean == null && singletonsCurrennlyCreations.contains(beanName)) {

            bean = earlySingletonObjects.get(beanName);
            if (bean == null) {
                ObjectFactory objectFactory = singletonFactories.get(beanName);
                if (bean != null) {
                    bean = objectFactory.getObject();
                    //加入到二级缓存
                    earlySingletonObjects.put(beanName, bean);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return bean;
    }
}
