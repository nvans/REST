package com.github.nvans.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Entity
@Table(name = "Groups")
@XmlRootElement
public class Group {

    @Id
    private Integer id;

    @Column(name = "group_name")
    private String name;

//    @OneToOne
//    @PrimaryKeyJoinColumn(name = "user_id")
//    private User user;

    public Group() {

    }

    public Group(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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

}
