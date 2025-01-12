package com.uhu.AGI.configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author juald
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime>
{

    @Override
    public LocalDateTime convert(String source)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(source, formatter);
    }
}
