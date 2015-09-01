package com.github.nvans.dao;

import com.github.nvans.domain.Address;

/**
 * Created by nvans on 29.08.2015.
 *
 * @author Ivan Konovalov
 */
public interface AddressDao {

    Address findById(Long id);

    void save(Address address);

    void delete(Address address);
}
