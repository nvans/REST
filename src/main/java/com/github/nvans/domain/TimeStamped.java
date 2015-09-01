package com.github.nvans.domain;

import java.sql.Timestamp;

/**
 * Interface for timestamps managing
 * Using with ChangeInterceptor.
 *
 * @see com.github.nvans.utils.interceptors.ChangeInterceptor
 *
 * @author Ivan Konovalov
 */
public interface TimeStamped {
    Timestamp getCreateTS();
    void setCreateTS(Timestamp date);
    Timestamp getLastUpdateTS();
    void setLastUpdateTS(Timestamp date);

}
