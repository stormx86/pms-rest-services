package pl.kozhanov.projectmanagementsystem.service.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static String localDateTimeToStringConverter(final LocalDateTime localDateTime){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }
}