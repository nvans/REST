package com.github.nvans.service;

import com.github.nvans.domain.Address;

/**
 * Created by nvans on 29.08.2015.
 *
 * @author Ivan Konovalov
 */
public interface AddressService {

    Address findById(Long id);

    void save(Address address);
}
