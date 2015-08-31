package com.github.nvans.utils.interceptors;

import com.github.nvans.domain.TimeStamped;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by nvans on 31.08.2015.
 *
 * @author Ivan Konovalov
 */
public class AuditInterceptor extends EmptyInterceptor {

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {



        if (entity instanceof TimeStamped) {
            int indexOf = Arrays.asList(propertyNames).indexOf("lastUpdateTS");
            currentState[indexOf] = Timestamp.valueOf(LocalDateTime.now());
            return true;
        }

        return false;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        if (entity instanceof TimeStamped) {
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());

            int indexOfCreate = Arrays.asList(propertyNames).indexOf("createTS");
            int indexOfUpdate = Arrays.asList(propertyNames).indexOf("lastUpdateTS");

            state[indexOfCreate] = ts;
            state[indexOfUpdate] = ts;

            return true;
        }

        return false;
    }
}
