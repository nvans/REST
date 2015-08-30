package com.github.nvans.utils;

import com.github.nvans.dao.GroupDao;
import com.github.nvans.domain.GroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nvans on 30.08.2015.
 *
 * @author Ivan Konovalov
 */
public class StaticDataInitializer {

    @Autowired
    private GroupDao groupDao;

    public void initialize() {
        createGroups();
    }

    @Transactional
    private void createGroups() {
        for (GroupType groupType : GroupType.values()) {
            System.out.println(groupType.asGroup());
            groupDao.save(groupType.asGroup());
        }
    }
}
