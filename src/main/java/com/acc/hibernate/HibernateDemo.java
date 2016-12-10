package com.acc.hibernate;

import com.acc.hibernate.config.ApplicationConfig;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
public class HibernateDemo {
    private static final Logger LOGGER = Logger.getLogger(HibernateDemo.class);

    public static void main(String[] args) {
        new HibernateDemo().start();
    }

    private void start() {
        try {
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
            ctx.registerShutdownHook();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
