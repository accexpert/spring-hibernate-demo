package com.acc.hibernate.repository.handlers;

import com.acc.hibernate.config.ApplicationConfig;
import com.acc.hibernate.repository.entities.UsersEntity;
import com.acc.hibernate.repository.handlers.clients.Demo2DbHandler1;
import com.acc.hibernate.repository.handlers.clients.Demo2DbHandler2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DbHandlerDemo2 {
    private static final Logger LOGGER = Logger.getLogger(DbHandlerDemo2.class);
    @PersistenceContext(unitName = ApplicationConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;
    @Autowired
    private Demo2DbHandler1 demo2DbHandler1;
    @Autowired
    private Demo2DbHandler2 demo2DbHandler2;

    public DbHandlerDemo2() {
        LOGGER.info(this.getClass().getSimpleName()+" created");
    }

    /**
     * <p>
     *     Save multiple records in the same transaction.
     *     Initially a new transaction is created when method is called.
     *     The four persist will generate four insert into database (because the PK is db generated).
     *     After returning from the method, the transaction is committed.
     * </p>
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveMultipleRecordsInTheSameTransaction() {
        UsersEntity e1 = new UsersEntity();
        e1.setFirstName("1");
        e1.setLastName("1");
        em.persist(e1);
        UsersEntity e2 = new UsersEntity();
        e2.setFirstName("2");
        e2.setLastName("2");
        em.persist(e2);
        UsersEntity e3 = new UsersEntity();
        e3.setFirstName("3");
        e3.setLastName("3");
        em.persist(e3);
        UsersEntity e4 = new UsersEntity();
        e4.setFirstName("4");
        e4.setLastName("4");
        em.persist(e4);
    }
    //-----------------------------------------------------------------------------------------------------------------

    /**
     * <p>
     *     Scenario:
     *     Save first record then call the second method with REQUIRES_NEW (will force a new transaction) which fails.
     *     Result:
     *     First record is committed, second one is rollback-ed.
     *     IMPORTANT:
     *     The second method MUST be in a different bean in order REQUIRES_NEW to work. If is in the same bean (methods in the same class)
     *     then both transactions will be rollback-ed even if exception is catch.
     * </p>
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRecordFirstNewTransaction() {
        LOGGER.info("Enter first transaction");
        UsersEntity e = new UsersEntity();
        e.setFirstName("1");
        e.setLastName("1");
        em.persist(e);
        LOGGER.info("Call second transaction");
        try {
            demo2DbHandler1.saveRecordSecondNewTransaction();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        LOGGER.info("Exit first transaction");
    }
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * <p>
     *     Scenario:
     *     Save first record then call the second method with REQUIRES (will force a new transaction) which fails.
     *     Result:
     *     Both transactions will be rollback-ed.
     * </p>
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveRecordFirstTransaction() {
        LOGGER.info("Enter first transaction with propagation");
        UsersEntity e = new UsersEntity();
        e.setFirstName("3");
        e.setLastName("3");
        em.persist(e);
        LOGGER.info("Call second transaction with propagation");
        try {
            demo2DbHandler2.saveRecordSecondAttachedTransaction();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        LOGGER.info("Exit first transaction with propagation");
    }
    //-----------------------------------------------------------------------------------------------------------------

}
