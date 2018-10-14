package plusequalsto.com.radio;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Belal on 10/2/2017.
 */

public class Schedule {

    @SerializedName("day")
    private String day;
    private String show;
    private String time;


    public Schedule(String day, String show, String time) {
        this.day = day;
        this.show = show;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public String getShow() {
        return show;
    }

    public String getTime() {
        return time;
    }

}
