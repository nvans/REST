package com.github.nvans.dao;

import com.github.nvans.domain.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
public interface UserDao {

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String lastname);

    User findByEmail(String email);

    List<User> findByBirthday(LocalDate birthday);

    List<User> findAllUsers();

}
