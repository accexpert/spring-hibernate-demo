package com.acc.hibernate.repository.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 *     Users entity
 *
 *     Cacheable(true) - the entity will be cached in L2.
 *     It depends on the entityManagerFactoryBean.setSharedCacheMode() value set here
 *     SharedCacheMode.ALL - means every entity will be cached in L2 no matter if the Cacheable annotation is set or not
 *     SharedCacheMode.ENABLE_SELECTIVE - entity will be cached in L2 only if Cacheable annotation is set to true. All other entities will not be cached.
 *     SharedCacheMode.DISABLE_SELECTIVE - Caching is enabled for all entities except those for which Cacheable(false) is specified. Entities for which
 *                                         Cacheable(false) is specified are not cached.
 *     SharedCacheMode.NONE - Caching is disabled for the persistence unit
 * </p>
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
@Entity
@Table(name = "users")
@Cacheable(true)
public class UsersEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "first_Name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public UsersEntity() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
