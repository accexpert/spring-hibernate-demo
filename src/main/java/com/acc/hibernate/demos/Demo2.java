package com.acc.hibernate.demos;

import com.acc.hibernate.repository.handlers.DbHandlerDemo2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     Demo #2
 *
 *     Demonstrate multiple operations in the same transactions.
 *     Demonstrate transaction propagation.
 * </p>
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
@Component
public class Demo2 implements IDemos {
    private static final Logger LOGGER = Logger.getLogger(Demo2.class);
    @Autowired
    private DbHandlerDemo2 dbHandler;

    public Demo2() {
        LOGGER.info(this.getClass().getSimpleName()+" created");
    }

    public void run() {
        LOGGER.info("Demo2 run called");
        dbHandler.saveMultipleRecordsInTheSameTransaction();
        try {
            dbHandler.saveRecordFirstNewTransaction();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        try {
            dbHandler.saveRecordFirstTransaction();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        LOGGER.info("End demo2");
    }
}
