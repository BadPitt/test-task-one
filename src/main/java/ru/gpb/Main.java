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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

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

            generateSellPoints(options);

        } catch (IOException ioe) {
            System.out.println("troubles with FS working: " + ioe.getMessage());
            LOGGER.error("IO troubles", ioe);
        } catch (Exception e) {
            System.out.println("something went wrong: " + e.getMessage());
            LOGGER.error("Unhandled exception", e);
        }
    }

    static void generateSellPoints(Options options) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        List<String> sellPoints =
                Files.readAllLines(Paths.get(options.getSellPointsFileName()));
        Stream.generate(() -> Main.generateRow(counter, sellPoints))
                .limit(options.getQuantityOfOperations())
                .map(Mappers::rawToString)
                .forEach(a -> writeString(a, options.getOutputFileName()));
    }

    static Row generateRow(AtomicLong counter, List<String> sellPoints) {
        Row raw = new Row();
        raw.setOperationNumber(counter.incrementAndGet());
        raw.setOperationDate(Main.generateDate());
        raw.setSellPoint(Main.getRandomSellPoint(sellPoints));
        raw.setOperationSum(
                new BigDecimal(
                        ((int) (Math.random() * 90_001) + 10_000)
                )
        );
        return raw;
    }

    static String getRandomSellPoint(List<String> sellPoints) {
        return sellPoints.stream()
                .skip(
                        ((long) (Math.random() * sellPoints.size()))
                )
                .findFirst()
                .get();
    }

    static void writeString(String stringRow, String fileName) {
        try {
            Files.write(
                    Paths.get(fileName),
                    stringRow.getBytes(FILE_CHARSET),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            LOGGER.error("error occurred", e);
        }
    }

    static LocalDateTime generateDate() {
        int year = LocalDateTime.now().getYear() - 1;
        int minDay = (int) LocalDate.of(year, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(year, 12, 31).toEpochDay();
        int day = (int) (Math.random() * (maxDay - minDay + 1)) + minDay;

        LocalTime time = LocalTime.of(
                (int)(Math.random()*24),
                (int)(Math.random()*60),
                (int)(Math.random()*60)
        );

        return LocalDateTime.of(LocalDate.ofEpochDay(day), time);
    }
}
