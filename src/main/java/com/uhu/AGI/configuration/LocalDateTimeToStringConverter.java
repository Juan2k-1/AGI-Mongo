package com.uhu.AGI.configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author juald
 */
public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String>
{

    @Override
    public String convert(LocalDateTime source)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return source.format(formatter);
    }
}
