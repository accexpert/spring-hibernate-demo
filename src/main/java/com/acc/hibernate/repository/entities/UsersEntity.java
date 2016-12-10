package com.acc.hibernate.repository.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */
@Entity
@Table(name = "users")
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
