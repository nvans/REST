package com.github.nvans.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Address domain class.
 *
 * In my opinion, validation is not need for this class in this case.
 *
 * @author Ivan Konovalov
 */
@Entity
@XmlRootElement
public class Address {

    @Id
    @GeneratedValue
    @Cascade(CascadeType.DELETE)
    private Long id;
    private String zip;
    private String country;
    private String city;
    private String district;
    private String street;

    // Getters and Setters
    // -->
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    // <--


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!zip.equals(address.zip)) return false;
        if (!country.equals(address.country)) return false;
        if (!city.equals(address.city)) return false;
        if (!district.equals(address.district)) return false;
        return street.equals(address.street);

    }

    @Override
    public int hashCode() {
        int result = zip.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + district.hashCode();
        result = 31 * result + street.hashCode();
        return result;
    }
}
