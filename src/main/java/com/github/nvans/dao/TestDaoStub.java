package com.github.nvans.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
@Repository
public class TestDaoStub implements TestDao {

    @Override
    public String getMessage() {

        return "Test msg";
    }
}
