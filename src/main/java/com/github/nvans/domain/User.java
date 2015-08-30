package com.github.nvans.domain;

import com.github.nvans.utils.converters.LocalDatePersistenceConverter;
import com.github.nvans.utils.converters.XmlDateConverter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@XmlRootElement
@Entity
@Table
@PrimaryKeyJoinColumn(name = "user_id")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    @Column(unique = true)
    private String email;

//    @Temporal(TemporalType.DATE)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate birthday;

    private Boolean isActive;

    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date createTS;

    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW() ON UPDATE NOW()")
    private Date lastUpdateTS;

    @OneToOne
    @JoinTable(name = "user_address")
    private Address address;

    @OneToOne
    @JoinTable(name = "user_group")
    private Group group;


    /* Getters and setters */
    // -->
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlJavaTypeAdapter(value = XmlDateConverter.class)
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTS() {
        return createTS;
    }

    public Date getLastUpdateTS() {
        return lastUpdateTS;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    // <--



}
