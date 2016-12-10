package com.acc.hibernate.demos;

import com.acc.hibernate.repository.entities.UsersEntity;
import com.acc.hibernate.repository.handlers.UsersHandlerDemo1;
import org.apache.log4j.Logger;
import org.hibernate.PersistentObjectException;
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
    private UsersHandlerDemo1 usersHandler;

    public Demo1() {
        LOGGER.info(this.getClass().getSimpleName()+" created.");
    }

    public void run() {
        LOGGER.info("Demo1 run called");
        Integer id = usersHandler.saveUserRecord();
        UsersEntity entity = usersHandler.getRecord(UsersEntity.class, id);
        usersHandler.updateUserRecord(id);
        try {
            //trying to save an detached entity will throw an error
            usersHandler.updateUserRecordDetachedEntityWithPersist(entity);
        } catch (PersistenceException ex) {
            LOGGER.error(ex.getMessage());
        }
        usersHandler.updateUserRecordDetachedEntityWithMerge(entity);
        try {
            usersHandler.deleteUserRecordDetachedEntity(entity);
        } catch (IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage());
        }
        usersHandler.deleteUserRecord(id);
    }
}
