package com.acc.hibernate.demos;

import com.acc.hibernate.repository.entities.UsersEntity;
import com.acc.hibernate.repository.handlers.DbHandlerDemo1;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;

/**
 * <p>
 *     Demo #1
 *
 *     Demonstrate simple CRUD operations: insert, update and delete a simple User record.
 * </p>
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
@Component
public class Demo1 implements IDemos {
    private static final Logger LOGGER = Logger.getLogger(Demo1.class);
    @Autowired
    private DbHandlerDemo1 dbHandler;

    public Demo1() {
        LOGGER.info(this.getClass().getSimpleName()+" created.");
    }

    public void run() {
        LOGGER.info("Demo1 run called");
        Integer id = dbHandler.saveUserRecord();
        UsersEntity entity = dbHandler.getRecord(UsersEntity.class, id);
        UsersEntity entity1 = dbHandler.getRecord(UsersEntity.class, id);
        dbHandler.updateUserRecord(id);
        UsersEntity entity2 = dbHandler.getRecord(UsersEntity.class, id);
        try {
            //trying to save an detached entity will throw an error
            dbHandler.updateUserRecordDetachedEntityWithPersist(entity);
        } catch (PersistenceException ex) {
            LOGGER.error(ex.getMessage());
        }
        dbHandler.updateUserRecordDetachedEntityWithMerge(entity);
        try {
            dbHandler.deleteUserRecordDetachedEntity(entity);
        } catch (IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage());
        }
        dbHandler.deleteUserRecord(id);
    }
}
