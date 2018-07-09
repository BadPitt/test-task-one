package ru.gpb;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.gpb.core.Mappers;
import ru.gpb.core.Row;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static ru.gpb.core.Constants.DATE_TIME_FORMAT;

public class MainTest {
    private static final String OUTPUT_FILE_NAME = "src/test/resources/test.txt";
    private static final String INPUT_FILE_NAME = "src/test/resources/sellPoints.txt";

    @BeforeClass
    public static void prepare() {
        clear();
    }

    @AfterClass
    public static void rmRf() {
        clear();
    }

    private static void clear() {
        File outputFile = new File(OUTPUT_FILE_NAME);
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }

    @Test
    public void execHelpTest() {
        Main.main(new String[]{"--help"});
    }

    @Test
    public void execMainTest() throws IOException {
        Main.main(new String[]{
                new File(INPUT_FILE_NAME).getPath(),
                "10",
                new File(OUTPUT_FILE_NAME).getPath()
        });

        List<String> sellPoints = Files.readAllLines(Paths.get(INPUT_FILE_NAME));
        List<Row> rows = Files.readAllLines(Paths.get(OUTPUT_FILE_NAME)).stream()
                .map(Mappers::stringToRow)
                .collect(Collectors.toList());

        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);

            assertTrue(sellPoints.contains(row.getSellPoint()));

            Calendar generated = Calendar.getInstance();
            generated.setTime(localDateTimeToDate(row.getOperationDate()));
            Calendar january = getCalendar(2016, 11, 31);
            Calendar december = getCalendar(2018, 0, 1);
            assertTrue(""+i+":"+DATE_TIME_FORMAT.format(dateToLocalDateTime(generated.getTime())),
                    generated.after(january) && generated.before(december));

            assertTrue(row.getOperationSum().compareTo(new BigDecimal(100_001)) < 0);
            assertTrue(row.getOperationSum().compareTo(new BigDecimal(9_999)) > 0);
        }
    }

    @Test
    public void execMainFileNotFoundTest() {
        Main.main(new String[]{
                new File("fileNotFound").getPath(),
                "10",
                new File(OUTPUT_FILE_NAME).getPath()
        });
    }

    @Test
    public void generateDateTest() {
        for (int i = 0; i < 1_000_000; i++) {
            LocalDateTime date = Main.generateDate();
            Calendar generated = Calendar.getInstance();
            generated.setTime(localDateTimeToDate(date));
            Calendar january = getCalendar(2016, 11, 31);
            Calendar december = getCalendar(2018, 0, 1);
            assertTrue(""+i+":"+DATE_TIME_FORMAT.format(dateToLocalDateTime(generated.getTime())),
                    generated.after(january) && generated.before(december));
        }
    }

    private Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar;
    }

    private Date localDateTimeToDate(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        }
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
