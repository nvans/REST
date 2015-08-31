package com.github.nvans.domain;

import java.sql.Timestamp;

/**
 * Created by nvans on 31.08.2015.
 *
 * @author Ivan Konovalov
 */
public interface TimeStamped {
    Timestamp getCreateTS();
    void setCreateTS(Timestamp date);
    Timestamp getLastUpdateTS();
    void setLastUpdateTS(Timestamp date);

}
