package airport.server.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;

/**
 * @author natalija
 */
public class DateUtil {
    /**
     * Util class. Private constructor.
     */
    private DateUtil() {
    }

    /**
     * Formats Local date time as appropriate String representation.
     * This is a value specially made for frontend preview.
     *
     * @param localDateTime as date to be formatted.
     * @return formatted date.
     */
    public static String convertToDateString(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(localDateTime);
    }

    /**
     * Prepares current date time for message to be saved.
     * I was thinking whether it is okay to send message from UI with date information or to prepare this info on backed.
     * I decided to get this info on server, inside of the transaction.
     *
     * @return date in milliseconds.
     */
    public static LocalDateTime convertToLocalDateTimeNow() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis()).toLocalDateTime();
    }
}
