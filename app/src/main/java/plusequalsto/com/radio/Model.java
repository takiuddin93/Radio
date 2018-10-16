package plusequalsto.com.radio;

public class Model {

    public static final int IMAGE_TYPE = 1;
    public String day, show, time;
    public int type;

    public Model(int mtype, String mday, String mshow, String mtime) {
        this.type = mtype;
        this.day = mday;
        this.show = mshow;
        this.time = mtime;
    }
}
