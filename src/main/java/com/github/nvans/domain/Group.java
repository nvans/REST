package com.github.nvans.domain;

import java.util.List;

/**
 * Created by nvans on 27.08.2015.
 *
 * @author Ivan Konovalov
 */
public class Group {

    private Long id;

    private String name;

    // @OneToMany
    private List<User> users;
}
