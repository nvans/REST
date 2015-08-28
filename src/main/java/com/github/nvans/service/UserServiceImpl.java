package com.github.nvans.service;

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
    public void save(User user) {

//        Date date = new  Date();
//
//        if (user.getCreateTS() == null) {
//            user.setCreateTS(date);
//        }
//
//        user.setLastUpdateTS(date);
//
//        System.out.println(user.getCreateTS().toString());
//        System.out.println(user.getLastUpdateTS().toString());

        userDao.save(user);
    }
}