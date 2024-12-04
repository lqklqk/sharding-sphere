package com.lqk.shardingjdbc.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * @author liqiankun
 * @date 2024/11/29 15:25
 * @description
 **/
@Component
public class AppContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
    /**
     * @param baseClass Class
     * @param <T> Object
     * @return Object
     */
    public static <T> T getBeanByClass(Class<T> baseClass) {
        if (context == null) {
            return null;
        } else {
            return context.getBean(baseClass);
        }
    }

    /**
     * 通过Bean名称获取Bean
     * @param name Bean容器名称
     * @return Bean实例对象
     */
    public static Object getBeanByName(String name){
        if (context == null) {
            return null;
        } else {
            return context.getBean(name);
        }
    }

    /**
     * 获取当前环境
     *
     * @return String
     */
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }
}
