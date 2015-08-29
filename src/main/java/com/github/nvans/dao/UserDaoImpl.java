package com.github.nvans.dao;

import com.github.nvans.domain.User;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User findById(Long id) {
        Session session = null;
        User user = null;

        try {
            session = sessionFactory.openSession();
            user = (User) session.get(User.class, id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public List<User> findByFirstname(String firstname) {
        Session session = null;
        List<User> users = new ArrayList<>();

        try {
            session = sessionFactory.openSession();

            Query query = session.createQuery(
                "SELECT u FROM User u WHERE u.firstname = :firstname"
            ).setString("firstname", firstname);

            users.addAll(query.list());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }

    @Override
    public List<User> findByLastname(String lastname) {

        Session session = null;
        List<User> users = new ArrayList<>();

        try {
            session = sessionFactory.openSession();

            Query query = session.createQuery(
                "SELECT u FROM User u WHERE u.lastname = :lastname"
            ).setString("lastname", lastname);

            users.addAll(query.list());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }

    @Override
    public User findByEmail(String email) {

        Session session = null;
        User user = null;

        try {
            session = sessionFactory.openSession();

            Query query = session.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email"
            ).setString("email", email);



            user = (User) query.uniqueResult();

        } catch (NonUniqueResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public List<User> findByBirthday(LocalDate birthday) {

        Session session = null;
        List<User> users = new ArrayList<>();

        try {
            session = sessionFactory.openSession();

            Query query = session.createQuery(
                    "SELECT u FROM User u WHERE u.birthday = birthday"
            );

            users.addAll(query.list());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }

    @Override
    public List<User> findAllUsers() {
        Session session = null;
        List<User> users = new ArrayList<>();

        try {
            session = sessionFactory.openSession();

            Query query = session.createQuery(
                "SELECT u FROM User u"
            );

            users.addAll(query.list());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }

    /**
     *
     * Persisting user object to db
     *
     * @param user
     */
    @Override
    public void save(User user) {

        Session session = null;
        Transaction tx = null;
        try {
            session = this.sessionFactory.openSession();

            tx = session.beginTransaction();

            if (user.getId() == null) {
                session.persist(user);
            } else {
                session.update(user);
            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!tx.wasCommitted()) {
                tx.rollback();
            }

            session.flush();
            session.close();
        }
    }

}
