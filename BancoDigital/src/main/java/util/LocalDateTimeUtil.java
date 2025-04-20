package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {
    private LocalDateTimeUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora.format(formatter);
    }
}
