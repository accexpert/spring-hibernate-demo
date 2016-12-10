package com.acc.hibernate.repository.handlers.clients;

import com.acc.hibernate.config.ApplicationConfig;
import com.acc.hibernate.repository.entities.UsersEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
@Component
public class Demo2DbHandler1 {
    private static final Logger LOGGER = Logger.getLogger(Demo2DbHandler1.class);
    @PersistenceContext(unitName = ApplicationConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    public Demo2DbHandler1() {
        LOGGER.info(this.getClass().getSimpleName()+" created");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRecordSecondNewTransaction() {
        LOGGER.info("Enter second transaction");
        UsersEntity e = new UsersEntity();
        em.persist(e);
        LOGGER.info("Exit second transaction");
    }

}
