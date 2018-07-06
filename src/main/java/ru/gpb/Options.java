package ru.gpb;

import lombok.Builder;
import lombok.Getter;

/**
 * Representation of application's arguments
 *
 * @author Danil Popov
 */
@Builder
public class Options {

    public static Options parseOptions(String[] args) {
        if ("--help".equals(args[0])) {
            return new OptionsBuilder()
                    .isHelp(true)
                    .build();
        } else {
            return new OptionsBuilder()
                    .sellPointsFileName(args[0])
                    .quantityOfOperations(Long.parseLong(args[1]))
                    .outputFileName(args[2])
                    .isHelp(false)
                    .build();
        }
    }

    @Getter
    private String sellPointsFileName;
    @Getter
    private Long   quantityOfOperations;
    @Getter
    private String outputFileName;
    @Getter
    private Boolean isHelp;
}
