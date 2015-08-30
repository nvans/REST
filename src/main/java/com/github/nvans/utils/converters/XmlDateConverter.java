package com.github.nvans.utils.converters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * This adapter just for customizing XML representation of Date object.
 * Use this with XmlJavaTypeAdapter annotation
 * which need to be placed above a necessary getter.
 * (if place this annotation above field - you can get an error)
 *
 * @author Ivan Konovalov
 */
public class XmlDateConverter extends XmlAdapter<String, LocalDate> {

    private String patternRu = "dd-MM-yyyy";

    @Override
    public String marshal(LocalDate date) throws Exception {

        //
        if (Locale.getDefault().toString().equals("ru_RU")) {
            return date.format(DateTimeFormatter.ofPattern(patternRu));
        }

        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public LocalDate unmarshal(String dateString) throws Exception {
        return LocalDate.parse(dateString);
    }

}
