package ru.gpb.core;

import java.text.SimpleDateFormat;

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

    public static final SimpleDateFormat DATE_TIME_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd");


    public static final String FILE_CHARSET = "UTF-8";
}
