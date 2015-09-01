package com.github.nvans.dao;

import com.github.nvans.domain.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by nvans on 29.08.2015.
 *
 * @author Ivan Konovalov
 */
@Repository(value = "addressDao")
public class AddressDaoImpl implements AddressDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     *
     * Retrieve address object by id
     *
     * @param id
     * @return
     */
    @Override
    public Address findById(Long id) {
        Session session = null;
        Address address = null;
        try {
            session = sessionFactory.openSession();
            address = (Address) session.get(Address.class, id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return address;
    }



    /**
     *
     * Save/update object
     *
     * @param address
     */
    @Override
    public void save(Address address) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.saveOrUpdate(address);

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

    /**
     * Delete object
     *
     * @param address
     */
    @Override
    public void delete(Address address) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.delete(address);
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
