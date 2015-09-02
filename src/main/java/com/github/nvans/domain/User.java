package com.github.nvans.domain;

import com.github.nvans.utils.converters.LocalDatePersistenceConverter;
import com.github.nvans.utils.converters.XmlDateConverter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 *
 * User domain
 *
 * @author Ivan Konovalov
 */
@XmlRootElement
@XmlType(propOrder = {"id", "firstname", "lastname", "username", "password", "email", "birthday", "address", "group"})
@Entity
@Table(name = "Users")
public class User implements TimeStamped {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty @Email
    @Column(unique = true)
    private String email;

    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate birthday;

    @XmlAttribute
    private Boolean isActive;

    private Timestamp createTS;

    private Timestamp lastUpdateTS;

    @OneToOne
    @JoinTable(name = "user_address")
    @Cascade(CascadeType.ALL)
    private Address address;

    @OneToOne
    @JoinTable(name = "user_group")
    private Group group;

    // Getters and Setters
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

    @XmlTransient
    @Override
    public Timestamp getCreateTS() {
        return createTS;
    }

    @Override
    public void setCreateTS(Timestamp createTS) {
        this.createTS = createTS;
    }

    @XmlTransient
    @Override
    public Timestamp getLastUpdateTS() {
        return lastUpdateTS;
    }

    @Override
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!firstname.equals(user.firstname)) return false;
        if (!lastname.equals(user.lastname)) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        if (!email.equals(user.email)) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (createTS != null ? !createTS.equals(user.createTS) : user.createTS != null) return false;
        return !(lastUpdateTS != null ? !lastUpdateTS.equals(user.lastUpdateTS) : user.lastUpdateTS != null);

    }

    @Override
    public int hashCode() {
        int result = firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (createTS != null ? createTS.hashCode() : 0);
        result = 31 * result + (lastUpdateTS != null ? lastUpdateTS.hashCode() : 0);
        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println("try");
            throw new Exception("E");
        } catch (Exception e) {
            System.out.println("catch");
        } finally {
            System.out.println("finally before");
            throw new RuntimeException();
        }
    }
}
