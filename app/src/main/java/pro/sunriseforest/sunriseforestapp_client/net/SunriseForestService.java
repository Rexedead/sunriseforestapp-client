package pro.sunriseforest.sunriseforestapp_client.net;



import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.Contractor;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface SunriseForestService {

    @GET("data/tasks")
    Call<List<Task>> getTasks();

    @GET("data/tasks/{id}")
    Call<Task> getTask(@Path("id") int id);

    @GET("auth/login")
    Call<Contractor> login(@Query("login")String login, @Query("password") String password);

    @POST("auth/reg")
    Call<Contractor> registration(@Body Contractor contractor);

    @PATCH("task/{id}/book")
    Call book(@Path("id") int id);

}
