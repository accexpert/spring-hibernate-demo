package com.acc.hibernate;

import com.acc.hibernate.config.ApplicationConfig;
import com.acc.hibernate.demos.IDemos;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.acc.hibernate.utils.ProfilesUtils.*;

/**
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
public class HibernateDemo {
    private static final Logger LOGGER = Logger.getLogger(HibernateDemo.class);
    private static final String DEMO_BEAN_PREFIX = "demo";
    private static final String DEMO_NUMBER = "2";

    public static void main(String[] args) {
        new HibernateDemo().start();
    }

    private void start() {
        try {
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
            switch(DEMO_NUMBER) {
                case "1":
                    ctx.getEnvironment().setActiveProfiles(PROFILE_HIBERNATE_MYSQL, PROFILE_HIBERNATE_USE_CACHE_LEVEL_2, PROFILE_HIBERNATE_USE_QUERY_CACHE);
                    break;
                case "2":
                    ctx.getEnvironment().setActiveProfiles(PROFILE_HIBERNATE_MYSQL, PROFILE_HIBERNATE_USE_CACHE_LEVEL_2, PROFILE_HIBERNATE_USE_QUERY_CACHE);
                    break;
                case "3":
                    ctx.getEnvironment().setActiveProfiles(PROFILE_HIBERNATE_MYSQL, PROFILE_HIBERNATE_USE_CACHE_LEVEL_2, PROFILE_HIBERNATE_USE_QUERY_CACHE);
                    break;
                case "4":
                    ctx.getEnvironment().setActiveProfiles(PROFILE_HIBERNATE_MYSQL, PROFILE_HIBERNATE_USE_CACHE_LEVEL_2, PROFILE_HIBERNATE_USE_QUERY_CACHE);
                    break;
                default:
                    LOGGER.error("Please select a demo number");
                    return;
            }
            ctx.register(ApplicationConfig.class);
            ctx.refresh();
            ctx.getBean(DEMO_BEAN_PREFIX+DEMO_NUMBER, IDemos.class).run();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
