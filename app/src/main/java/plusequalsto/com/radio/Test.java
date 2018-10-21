package plusequalsto.com.radio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("Day")
    @Expose
    private String day;
    @SerializedName("Show")
    @Expose
    private String show;
    @SerializedName("Time")
    @Expose
    private String time;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}