package com.acc.hibernate.demos;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     Demo #4
 *
 *     Demonstrate search
 * </p>
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
@Component
public class Demo4 implements IDemos {
    private static final Logger LOGGER = Logger.getLogger(Demo4.class);

    public Demo4() {
        LOGGER.info(this.getClass().getSimpleName()+" created");
    }

    public void run() {
        LOGGER.info("Demo4 run called");

        LOGGER.info("End demo4");
    }
}
