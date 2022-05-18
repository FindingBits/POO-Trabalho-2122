import java.time.Period;
import java.util.Date;
import java.util.Calendar;

public class Ambient {

    private Calendar calendar;
    private Date start;
    protected int advanced;

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        this.calendar.setTime(new Date());
        this.start = calendar.getTime();
    }

    public Date getCalendar() {
        return calendar.getTime();
    }
    public Date getStart() {
        return this.start;
    }
    public int getElapsed(){
        return this.advanced;
    }

    /**
     * Updates the days in the Ambient
     * @param days days in the Ambient
     */

    public void advanceDays(int days){
        this.calendar.add(Calendar.DAY_OF_MONTH, days);
        this.advanced = days;

    }


    /**
     * Building the Ambient
     * @param calendar the Ambient
     */
    public Ambient(Calendar calendar) {
        setCalendar(calendar);
    }
}
