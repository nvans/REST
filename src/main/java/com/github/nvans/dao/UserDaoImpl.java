package com.github.nvans.dao;

import com.github.nvans.domain.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Repository
public class UserDaoImpl implements UserDao, InitializingBean {

    private List<User> users = new ArrayList<>();

    @Override
    public List<User> findByFirstname(String firstname) {
        List<User> usersList = new ArrayList<>();

        for (User user: users) {
            if (user.getFirstname().equals(firstname)) {
                usersList.add(user);
            }
        }

        return usersList;
    }

    @Override
    public List<User> findByLastname(String lastname) {
        List<User> usersList = new ArrayList<>();

        for (User user: users) {
            if (user.getLastname().equals(lastname)) {
                usersList.add(user);
            }
        }

        return usersList;
    }

    @Override
    public User findByEmail(String email) {

        User foundedUser = new User();

        for (User user: users) {
            if (user.getEmail().equals(email)) {
                foundedUser = user;
            }
        }

        return foundedUser;
    }

    @Override
    public List<User> findByBirthday(LocalDate birthday) {

        List<User> usersList = new ArrayList<>();

        for (User user: users) {
            if (user.getBirthday().equals(birthday)) {
                usersList.add(user);
            }
        }

        return usersList;
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(1989, Calendar.JUNE, 28);

        User user = new User();
        user.setId(1L);
        user.setFirstname("Ivan");
        user.setLastname("Konovalov");
        user.setBirthday(cal.getTime());
        users.add(user);

        cal.set(1990, Calendar.MAY, 5);

        User user1 = new User();
        user1.setId(2L);
        user1.setFirstname("Petr");
        user1.setLastname("Petrov");
        user1.setBirthday(cal.getTime());
        users.add(user1);
    }
}
