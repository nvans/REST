package com.github.nvans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Group domain.
 *
 * @author Ivan Konovalov
 */
@Entity
@Table(name = "WORK_GROUPS")
@XmlRootElement
public class Group {

    @Id
    private Integer id;

    @Column(name = "group_name")
    private String name;


    public Group() {

    }

    public Group(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
    // -->
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // <--
}
