package ru.gpb.core;

import java.math.BigDecimal;

import static ru.gpb.core.Constants.DATE_TIME_FORMAT;

/**
 * It encapsulates rules of marshalling and
 * unmarshalling of Row class
 *
 * @see Row
 *
 * @author Danil Popov
 * BadPit@211.ru
 * on 06.07.18
 */
public final class Mappers {
    private Mappers() {
    }

    public static String rawToString(Row raw) {
        return "" +
                raw.getOperationNumber() + " " +
                DATE_TIME_FORMAT.format(raw.getOperationDate()) + " " +
                raw.getSellPoint() + " " +
                raw.getOperationSum();
    }

    public static Row stringToRow(String str) {
        Row row = new Row();
        try {
            String[] values = str.split(" ");
            row.setOperationNumber(Long.parseLong(values[0]));
            row.setOperationDate(DATE_TIME_FORMAT.parse(values[1]));
            row.setSellPoint(values[2]);
            row.setOperationSum(new BigDecimal(values[3]));
        } catch (Exception e) {
            throw new ApplicationException("File corrupted");
        }
        return row;
    }
}
