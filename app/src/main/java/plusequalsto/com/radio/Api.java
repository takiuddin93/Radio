package plusequalsto.com.radio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("schedule.json")
    Call<List<Schedule>> getSchedules();
}
