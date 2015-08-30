package com.github.nvans.dao;

import com.github.nvans.domain.Group;

import java.util.List;

/**
 * Created by nvans on 30.08.2015.
 *
 * @author Ivan Konovalov
 */
public interface GroupDao {

    Group getDefaultGroup();

    Group findById(Integer id);

    List<Group> findAllGroups();

    void save(Group group);
}
