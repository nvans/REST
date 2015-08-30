package com.github.nvans.utils;

import com.github.nvans.dao.GroupDao;
import com.github.nvans.domain.GroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring bean.
 *
 * Save predefined groups to database
 * at start of application.
 *
 * @author Ivan Konovalov
 */
public class StaticDataInitializer {

    @Autowired
    private GroupDao groupDao;


    /**
     * Init method.
     */
    public void initialize() {
        createGroups();
    }

    /**
     * Save all predefined groups
     */
    @Transactional
    private void createGroups() {
        for (GroupType groupType : GroupType.values()) {
            System.out.println(groupType.asGroup());
            groupDao.save(groupType.asGroup());
        }
    }
}
