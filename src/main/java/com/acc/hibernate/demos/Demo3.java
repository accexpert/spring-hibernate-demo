package com.acc.hibernate.demos;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     Demo #3
 *
 *     Demonstrate HQL, JPQL and Criteria Builder
 * </p>
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
@Component
public class Demo3 implements IDemos {
    private static final Logger LOGGER = Logger.getLogger(Demo3.class);

    public Demo3() {
        LOGGER.info(this.getClass().getSimpleName()+" created");
    }

    public void run() {
        LOGGER.info("Demo3 run called");

        LOGGER.info("End demo3");
    }
}
