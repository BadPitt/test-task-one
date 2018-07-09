package ru.gpb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gpb.core.Mappers;
import ru.gpb.core.Row;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ru.gpb.core.Constants.FILE_CHARSET;

/**
 * Main class for business process
 *
 * @author Danil Popov
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final String HELP = "Arguments:\n" +
            "the first - sell points file name\n" +
            "the second - quantity of rows\n" +
            "the third - output file name\n" +
            "\n" +
            "For example:\n" +
            "java -jar task1.jar offices.txt 90000 operations.txt";

    public static void main(String[] args) {
        try {
            // validate args
            InputValidator.validate(args);

            Options options = Options.parseOptions(args);

            if (options.getIsHelp()) {
                System.out.println(HELP);
                return;
            }

            List<String> sellPoints =
                    Files.readAllLines(Paths.get(options.getSellPointsFileName()));

            // generate every raw
            List<Row> rows = new ArrayList<>();
            for (long i = 0; i < options.getQuantityOfOperations(); i++) {
                Row raw = new Row();
                raw.setOperationNumber(i);
                raw.setOperationDate(Main.generateDate());
                raw.setSellPoint(
                        sellPoints.stream()
                                .skip(
                                        ThreadLocalRandom.current().nextLong(sellPoints.size())
                                )
                                .findFirst()
                                .get()
                );
                raw.setOperationSum(
                        new BigDecimal(
                                ThreadLocalRandom.current().nextInt(10_000, 100_001)
                        )
                );
                rows.add(raw);
            }

            // format
            String result = rows.stream()
                    .map(Mappers::rawToString)
                    .reduce((s, s2) -> s + "\n" + s2)
                    .get();
            result+="\n";

            // write to output
            Files.write(
                    Paths.get(options.getOutputFileName()),
                    result.getBytes(FILE_CHARSET),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
            );
        } catch (IOException ioe) {
            System.out.println("troubles with FS working: " + ioe.getMessage());
            LOGGER.error("IO troubles", ioe);
        } catch (Exception e) {
            System.out.println("something went wrong: " + e.getMessage());
            LOGGER.error("Unhandled exception", e);
        }
    }

    static LocalDateTime generateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);

        int days = getLastYearDaysCount();

        calendar.set(Calendar.DAY_OF_YEAR, ThreadLocalRandom.current().nextInt(days) + 1);

        calendar.set(Calendar.HOUR_OF_DAY, ThreadLocalRandom.current().nextInt(24));
        calendar.set(Calendar.MINUTE, ThreadLocalRandom.current().nextInt(60));
        calendar.set(Calendar.SECOND, ThreadLocalRandom.current().nextInt(60));
        calendar.set(Calendar.MILLISECOND, ThreadLocalRandom.current().nextInt(1000));

        return calendar.getTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    static int getLastYearDaysCount() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.MONTH, 0);
        now.add(Calendar.DAY_OF_MONTH, -1);
        return now.get(Calendar.DAY_OF_YEAR);
    }
}
