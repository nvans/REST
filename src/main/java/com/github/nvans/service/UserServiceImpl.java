package com.github.nvans.service;

import com.github.nvans.dao.UserDao;
import com.github.nvans.domain.Address;
import com.github.nvans.domain.Group;
import com.github.nvans.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupService groupService;

    @Autowired
    private Validator validator;

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
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
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
    public List<User> findUsersByGroup(Group group) {
        return userDao.findByGroup(group);
    }


    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    /**
     *
     * Processing user before saving
     *
     * @param user
     * @throws IllegalArgumentException
     */
    @Override
    public void save(User user) throws IllegalArgumentException {

        // Disable update operation for inactive users
        // -->
        if (!user.isActive() && isChanged(user)) {
            throw new IllegalArgumentException("*** User should be is activated before update ***");
        }
        // <--

        // Bean validation
        // -->
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.size() != 0) {
            throw new IllegalArgumentException(
                        constraintViolations.iterator().next().getMessage());
        }
        // <--


        // Check email existing
        // -->
        if (userDao.findByEmail(user.getEmail()) != null && user.getId() == null) {
            throw new IllegalArgumentException("*** User with this email exists ***");
        }
        // <--

        // Check username existing
        // -->
        else if (userDao.findByUsername(user.getUsername()) != null && user.getId() == null) {
            throw new IllegalArgumentException("*** User with this username exists ***");
        }
        // <--

        // Sets "UNDEFINED" group to new user
        // -->
        else if (user.getId() == null){
            user.setGroup(groupService.getDefaultGroup());
        }
        // <--

        userDao.save(user);

    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User findByAddress(Address address) {
        return userDao.findByAddress(address);
    }


    /**
     * Check changes in user fields
     *
     * @param user
     * @return true if users field changed and false otherwise
     */
    private boolean isChanged(User user) {
        User testUser = findById(user.getId());

        return !user.equals(testUser);
    }


}
