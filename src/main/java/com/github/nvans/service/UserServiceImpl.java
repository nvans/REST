package com.github.nvans.service;

import com.github.nvans.dao.GroupDao;
import com.github.nvans.dao.UserDao;
import com.github.nvans.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupDao groupDao;

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findByFirstname(String firstname) {
        return userDao.findByFirstname(firstname);
    }

    @Override
    public List<User> findByLastname(String lastname) {
        return userDao.findByLastname(lastname);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> findByBirthday(LocalDate birthday) {
        return userDao.findByBirthday(birthday);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public void save(User user) throws IllegalArgumentException {
        if (userDao.findByEmail(user.getEmail()) != null && user.getId() == null) {
            throw new IllegalArgumentException("*** User with this email exists ***");
        }
        else if (user.getId() == null){
            // Set undefined group to new user
            user.setGroup(groupDao.getDefaultGroup());
        }

        userDao.save(user);
    }
}
