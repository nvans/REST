package com.github.nvans.service;

import com.github.nvans.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Service
public class TestServiceStub implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public String getMessage() {
        return testDao.getMessage();
    }

}
