package org.kuali.ole.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kuali.ole.spring.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Integer> getListFromJSONArray(String content){
        List ops = new ArrayList();
        try {
            ops = new ObjectMapper().readValue(content, new TypeReference<List<ArrayList>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ops;

    }

}
