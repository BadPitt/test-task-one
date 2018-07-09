package ru.gpb.core;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Object representation of row in file
 *
 * @author Danil Popov
 * BadPit@211.ru
 * on 06.07.18
 */
@Data
public class Row {
    private Long operationNumber;
    private LocalDateTime operationDate;
    private String sellPoint;
    private BigDecimal operationSum;
}
