package com.github.nvans.dao;

import com.github.nvans.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserDaoImplStub implements UserDao, InitializingBean {

    private List<User> users = new ArrayList<>();

    private int counter = 0;

    @Autowired
    SessionFactory sessionFactory;

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
    public void save(User user) {

        System.out.println(user);
        Session session = null;
        Transaction tx = null;
        try {
            session = this.sessionFactory.openSession();
            tx = session.beginTransaction();
            if (counter == 0) {
                session.persist(user);
            } else session.update(user);
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            counter++;
            if (!tx.wasCommitted()) {
                tx.rollback();
            }//not much doing but a good practice
            session.flush(); //this is where I think things will start working.
            session.close();
        }
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
