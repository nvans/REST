package com.github.nvans.service;

import com.github.nvans.dao.AddressDao;
import com.github.nvans.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nvans on 29.08.2015.
 *
 * @author Ivan Konovalov
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;


    @Override
    public Address findById(Long id) {
        return addressDao.findById(id);
    }



    @Override
    public void save(Address address) {
        addressDao.save(address);
    }
}
