package com.github.nvans.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@XmlRootElement
public class Address {

    private String zip;
    private String country;
    private String city;
    private String district;
    private String street;

    /* Getters and Setters */
    // -->
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
}
