import java.util.Date;
import java.util.Calendar;

public class Ambient {
    private Calendar calendar;

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        this.calendar.setTime(new Date());

    }

    public Date getCalendar() {
        return calendar.getTime();
    }

    public void advanceDays(int days){
        this.calendar.add(Calendar.DAY_OF_MONTH, days);

    }

    public Ambient(Calendar calendar) {
        setCalendar(calendar);
    }
}
