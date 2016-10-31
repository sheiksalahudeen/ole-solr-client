package org.kuali.ole.util;

import org.kuali.ole.spring.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * Created by sheiks on 28/10/16.
 */
public class HelperUtil {

    public static JpaRepository getRepository(Class clz) {
        ApplicationContext applicationContext = ApplicationContextProvider.getInstance().getApplicationContext();
        return (JpaRepository) applicationContext.getBean(clz);
    }

    public static <T> T getBean(Class<T> requiredType) {
        ApplicationContext applicationContext = ApplicationContextProvider.getInstance().getApplicationContext();
        return applicationContext.getBean(requiredType);
    }

}
