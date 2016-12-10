package com.acc.hibernate.repository.handlers;

import com.acc.hibernate.config.ApplicationConfig;
import com.acc.hibernate.repository.entities.UsersEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>
 *
 * </p>
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
@Component
public class DbHandlerDemo1 {
    private static final Logger LOGGER = Logger.getLogger(DbHandlerDemo1.class);
    @PersistenceContext(unitName = ApplicationConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    public DbHandlerDemo1() {
        LOGGER.info(this.getClass().getSimpleName()+" created");
    }

    /**
     * <p>
     *      Create a new entity object and persisting it into database.
     *      The persist() method will do:
     *      <ul>
     *          <li>insert a new record in the database</li>
     *          <li>attach the object to the entity manager</li>
     *      </ul>
     *      The persist() method is useful when:
     *      <ul>
     *          <li>insert a new record in the database (is more efficient then merge() method)</li>
     *          <li>it doesn't duplicate the original object (@see updateUserRecordDetachedEntityWithMerge())</li>
     *      </ul>
     *      IMPORTANT: If the key is generated database side then after persist() the entity is not stored into cache L2.
     *      It will be stored only after first find()
     * </p>
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer saveUserRecord() {
        LOGGER.info("Start saving user record");
        UsersEntity entity = new UsersEntity();
        entity.setFirstName("test first");
        entity.setLastName("test last");
        em.persist(entity);
        LOGGER.info("User record persisted");
        Integer id = entity.getUserId();
        LOGGER.info("User record id retrieved");
        return id;
    }

    /**
     * <p>
     *     Get an entity by id
     * </p>
     * @param cls
     * @param id
     * @param <T>
     * @return
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public <T> T getRecord(Class<T> cls, Integer id) {
        LOGGER.info("Start retrieving with find() user record with id: "+id+"; Class: "+cls.getSimpleName());
        T val = em.find(cls, id);
        LOGGER.info("Finish retrieving with find() user record with id: "+id+"; Class: "+cls.getSimpleName());
        return val;
    }

    /**
     * <p>
     *     Get a reference
     * </p>
     * @param cls
     * @param id
     * @param <T>
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T> T getReferenceRecord(Class<T> cls, Integer id) {
        LOGGER.info("Start retrieving with getReference() user record with id: "+id+"; Class: "+cls.getSimpleName());
        T val = em.getReference(cls, id);
        LOGGER.info("Finish retrieving with getReference() user record with id: "+id+"; Class: "+cls.getSimpleName());
        return val;
    }

    /**
     * <p>
     *     Update an entity record with id (PK) received as parameter.
     *     The entity is first retrieved and then updated.
     *     Because we are in the same transactional state the entity is updated
     *     without an explicit persist() or merge()
     * </p>
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUserRecord(Integer id) {
        LOGGER.info("Start updating user record with id: "+id);
        UsersEntity entity = em.find(UsersEntity.class, id);
        entity.setFirstName("test first updated");
        entity.setLastName("test last updated");
        LOGGER.info("Finish updating user record with id: "+id+"; entity: "+entity.toString());
    }

    /**
     * <p>
     *      Update an entity record received as parameter outside transactional scope.
     *      The entity is in detached state and the persist method will throw an exception.
     *      The entity is in detached state because was returned previously from a transactional scope
     *      after commit.
     *      The persist() method is useful when:
     *      <ul>
     *          <li>insert a new record in the database (is more efficient then merge())</li>
     *          <li>it doesn't duplicate the original object (@see updateUserRecordDetachedEntityWithMerge())</li>
     *      </ul>
     *
     * </p>
     * @param entity
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUserRecordDetachedEntityWithPersist(UsersEntity entity) {
        LOGGER.info("Start updating user record with persist() and a detached entity: "+entity.toString());
        entity.setFirstName("test first updated");
        entity.setLastName("test last updated");
        em.persist(entity);
        LOGGER.info("Finish updating user record with persist() and a detached entity. THIS LINE SHOULD NOT BE PRINTED BECAUSE THE METHOD SHOULD THROW AN ERROR");
    }

    /**
     * <p>
     *     Update an entity record received as parameter outside transactional scope.
     *     The entity is in detached state.
     *     The merge() method will do:
     *     <ul>
     *         <li>if the entity is a detached entity, then the entity will be copied into the pre-existing entity with the same id</li>
     *         <li>if the entity is a new instance, then a new managed entity is created and values from the received entity are copied into the new entity</li>
     *         <li>if the entity is a deleted entity then an IllegalArgumentException exception will be thrown</li>
     *         <li>if entity is already a managed entity then the merge operation will be ignored</li>
     *     </ul>
     * </p>
     * @param entity
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUserRecordDetachedEntityWithMerge(UsersEntity entity) {
        LOGGER.info("Start updating user record with merge() and a detached entity: "+entity.toString());
        entity.setFirstName("test first updated with merge");
        entity.setLastName("test last updated with merge");
        //a new object entity is created
        UsersEntity newUserEntity = em.merge(entity);
        LOGGER.info("Received entity object: "+Integer.toHexString(entity.hashCode())+"; new managed entity object: "+Integer.toHexString(newUserEntity.hashCode()));
        LOGGER.info("Finish updating user record with merge() and a detached entity");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUserRecordDetachedEntity(UsersEntity entity) {
        LOGGER.info("Start removing entity "+entity.toString()+"; entity is in detached state.");
        em.remove(entity);
        LOGGER.info("Finish removing entity "+entity.toString()+"; THIS LINE SHOULD NOT BE PRINTED BECAUSE THE METHOD SHOULD THROW AN ERROR");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUserRecord(Integer id) {
        LOGGER.info("Start removing entity with id "+id);
        UsersEntity entity = em.find(UsersEntity.class, id);
        em.remove(entity);
        LOGGER.info("Finish removing entity with id "+id);
    }
}
