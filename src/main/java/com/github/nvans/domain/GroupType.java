package com.github.nvans.domain;

/**
 * Predefined groups
 *
 * @author Ivan Konovalov
 */
public enum GroupType {
    UNDEFINED(1, "UNDEFINED"),
    MANAGERS(2, "MANAGERS"),
    DEVELOPERS(3, "DEVELOPERS"),
    TESTERS(4, "TESTERS");

    private final Integer id;
    private final String groupName;

    GroupType(Integer id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Integer getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public Group asGroup() {
        return new Group(id, groupName);
    }
}
