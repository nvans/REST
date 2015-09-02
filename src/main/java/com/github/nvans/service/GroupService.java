package com.github.nvans.service;

import com.github.nvans.domain.Group;

import java.util.List;

/**
 * Created by nvans on 02.09.2015.
 *
 * @author Ivan Konovalov
 */
public interface GroupService {

    Group getDefaultGroup();

    Group findById(Integer id);

    List<Group> findAllGroups();

    void save(Group group);
}
