import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigGlobal {
    public static int size=20;
    public static int amount=100;
    public static int radius=3;

    public static String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return now.format(format);
    }

    public static int calculateDifference(String start,String end){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime date1 = LocalDateTime.parse(start, format);
        LocalDateTime date2 = LocalDateTime.parse(end, format);
        Duration duration = Duration.between(date1, date2);
        return (int) (duration.getSeconds()/60);
    }
}
