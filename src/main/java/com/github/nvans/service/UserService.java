package com.github.nvans.service;

import com.github.nvans.domain.Address;
import com.github.nvans.domain.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
public interface UserService {

    User findById(Long id);

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String lastname);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByBirthday(LocalDate birthday);


    List<User> findAllUsers();

    void save(User user) throws IllegalArgumentException;

    void delete(User user);

    void deleteById(Long id);
}
