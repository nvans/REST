package com.github.nvans.dao;

import com.github.nvans.domain.Group;
import com.github.nvans.utils.exceptions.TransactionFailException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nvans on 30.08.2015.
 *
 * @author Ivan Konovalov
 */
@Repository("groupDao")
public class GroupDaoImpl implements GroupDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Group findById(Integer id) {
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

    @Override
    public List<Group> findAllGroups() {
        Session session = null;
        List<Group> groups = new ArrayList<>();

        try {
            session = sessionFactory.openSession();

            Query query = session.createQuery(
                    "SELECT g FROM Groups g"
            );

            groups.addAll(query.list());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return groups;
    }

    @Override
    public void save(Group group) throws TransactionFailException {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.save(group);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (!tx.wasCommitted()) {
                tx.rollback();

                session.close();
                throw new TransactionFailException();
            }

            session.close();
        }
    }

    @Override
    public void delete(Group group) throws TransactionFailException {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.delete(group);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!tx.wasCommitted()) {
                tx.rollback();

                session.close();

                throw new TransactionFailException();
            }

            session.close();
        }
    }
}
