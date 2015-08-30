package com.github.nvans.domain;

/**
 * Created by nvans on 30.08.2015.
 *
 * @author Ivan Konovalov
 */
public enum GroupType {
    UNDEFINED(1L, "UNDEFINED"),
    MANAGERS(2L, "MANAGERS"),
    DEVELOPERS(3L, "DEVELOPERS"),
    TESTERS(4L, "TESTERS");

    private final Long id;
    private final String groupName;

    GroupType(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public Group asGroup() {
        return new Group(id, groupName);
    }
}
