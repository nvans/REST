package com.github.nvans.service;

import com.github.nvans.dao.GroupDao;
import com.github.nvans.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nvans on 02.09.2015.
 *
 * @author Ivan Konovalov
 */
@Service(value = "groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Override
    public Group getDefaultGroup() {
        return groupDao.findById(1);
    }

    @Override
    public Group findById(Integer id) {
        return groupDao.findById(id);
    }

    @Override
    public List<Group> findAllGroups() {
        return groupDao.findAllGroups();
    }

    @Override
    public void save(Group group) {
        groupDao.save(group);
    }
}
