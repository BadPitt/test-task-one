package ru.gpb.core;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

/**
 * Constants for task one and task two
 *
 * @author Danil Popov
 * BadPit@211.ru
 * on 06.07.18
 */
public final class Constants {
    private Constants() {
    }

    public static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static final DateTimeFormatter  DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static final Charset FILE_CHARSET = StandardCharsets.UTF_8;
}
