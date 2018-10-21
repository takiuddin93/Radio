package plusequalsto.com.radio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("schedule.json")
    Call<List<Schedule>> getSchedules();

    @GET("test.json")
    Call<List<Test>> getTest();

    @GET("mysql_to_json.php")
    Call<List<Complaints>> getComplaints();
}
