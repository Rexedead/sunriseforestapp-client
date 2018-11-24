package pro.sunriseforest.sunriseforestapp_client.net;



import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SunriseForestService {
    @GET("data/tasks")
    Call<List<Task>> getTasks();

    @GET("data/tasks/{id}")
    Call<List<Task>> getTask(@Path("id") int id);

    @GET("auth/login")
    Call<User> userLogin(@Query("mail_or_phone")String mail, @Query("password") String password);

    @POST("auth/reg")
    Call<User> userRegistration(@Body User user);

    @POST("data/newtask")
    Call<Task> addtask(@Body Task task);

    @PATCH("task/{id}/book")
    Call book(@Path("id") int id);

}
