package com.github.nvans.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This adapter just for customizing XML representation of Date object.
 * Use this with XmlJavaTypeAdapter annotation
 * which need to be placed above a necessary getter.
 * (if place this annotation above field - you can get an error)
 *
 * @author Ivan Konovalov
 */
public class XmlDateAdapter extends XmlAdapter<String, Date> {

    private String pattern = "dd-MM-yyyy";

    @Override
    public String marshal(Date date) throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    @Override
    public Date unmarshal(String dateString) throws Exception {
        return new SimpleDateFormat(pattern).parse(dateString);
    }
}
