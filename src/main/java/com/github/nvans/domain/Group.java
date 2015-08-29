package com.github.nvans.domain;

import javax.persistence.*;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Entity
public class Group {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private GroupEnum name;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupEnum getName() {
        return name;
    }

    public void setName(GroupEnum name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private enum GroupEnum {
        UNDEFINED,
        MANAGERS,
        DEVELOPERS,
        TESTERS
    }
}
