package com.github.nvans.dao;

import com.github.nvans.domain.Group;

/**
 * Created by nvans on 30.08.2015.
 *
 * @author Ivan Konovalov
 */
public interface GroupDao {

    void save(Group group);

    Group findById(long id);
}
