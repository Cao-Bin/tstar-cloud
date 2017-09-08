package com.tiza.process.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Description: SpringUtil
 * Author: DIYILIU
 * Update: 2016-03-18 10:07
 */
public class SpringUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     *
     * 通过传递applicationContext参数初始化成员变量applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        SpringUtil.applicationContext = applicationContext;
    }

    /**
     * 获取spring上下文
     * @return
     */
    public static ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    /**
     * 实例化bean
     * @param name
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(String name) throws BeansException {

        return (T) applicationContext.getBean(name);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type){

        return applicationContext.getBeansOfType(type);
    }


    public static void init(){
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    public static void init(String name){
        new ClassPathXmlApplicationContext("classpath:" + name);
    }
}