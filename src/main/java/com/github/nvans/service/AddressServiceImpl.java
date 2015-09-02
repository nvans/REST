package com.github.nvans.service;

import com.github.nvans.dao.AddressDao;
import com.github.nvans.dao.UserDao;
import com.github.nvans.domain.Address;
import com.github.nvans.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nvans on 29.08.2015.
 *
 * @author Ivan Konovalov
 */
@Service(value = "addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Address findById(Long id) {
        return addressDao.findById(id);
    }

    @Override
    public void save(Address address) {
        addressDao.save(address);
    }

    @Override
    public void delete(Address address) {

        User user = userDao.findByAddress(address);
        user.setAddress(null);
        userDao.save(user);

        addressDao.delete(address);
    }
}
