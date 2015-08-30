package com.github.nvans.dao;

import com.github.nvans.domain.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by nvans on 30.08.2015.
 *
 * @author Ivan Konovalov
 */
@Repository
public class GroupDaoImpl implements GroupDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Group group) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.save(group);

            tx.commit();

        } catch (Exception e) {

            if (!tx.wasCommitted()) {
                tx.rollback();
            }

            e.printStackTrace();

        } finally {
            session.flush();
            session.close();
        }
    }

    @Override
    public Group findById(long id) {
        Session session = null;
        Group group = null;

        try {
            session = sessionFactory.openSession();
            group = (Group) session.get(Group.class, id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return group;
    }
}
