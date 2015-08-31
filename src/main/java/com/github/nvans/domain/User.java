package com.github.nvans.domain;

import com.github.nvans.utils.converters.LocalDatePersistenceConverter;
import com.github.nvans.utils.converters.XmlDateConverter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@XmlRootElement
@Entity
@Table(name = "Users")
@PrimaryKeyJoinColumn(name = "user_id")
public class User implements TimeStamped {

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

    private Timestamp createTS;

    private Timestamp lastUpdateTS;

    @OneToOne
    @JoinTable(name = "user_address")
    private Address address;

    @OneToOne
    @JoinTable(name = "user_group")
    private Group group;

//    @PreUpdate
//    public void onUpdate() {
//        lastUpdateTS = new Date();
//    }

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

    public Timestamp getCreateTS() {
        return createTS;
    }

    public void setCreateTS(Timestamp createTS) {
        this.createTS = createTS;
    }

    public Timestamp getLastUpdateTS() {
        return lastUpdateTS;
    }

    public void setLastUpdateTS(Timestamp lastUpdateTS) {
        this.lastUpdateTS = lastUpdateTS;
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


    public static void main(String[] args) {

    }



}
