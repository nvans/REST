package com.github.nvans.dao;

import com.github.nvans.domain.Address;
import com.github.nvans.domain.Group;
import com.github.nvans.domain.User;
import com.github.nvans.utils.exceptions.TransactionFailException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
public interface UserDao {

    User findById(Long id);

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String lastname);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByBirthday(LocalDate birthday);

    List<User> findAllUsers();

    void save(User user) throws TransactionFailException;

    void delete(User user) throws TransactionFailException;

    User findByAddress(Address address);

    List<User> findByGroup(Group group);
}
