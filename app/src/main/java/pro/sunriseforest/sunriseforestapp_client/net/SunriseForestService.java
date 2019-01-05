package pro.sunriseforest.sunriseforestapp_client.net;



import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.Client;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface SunriseForestService {
    @GET("data/tasks")
    Call<List<Task>> getTasks(@Query("sr_user_token") String token);

    @GET("data/tasks/{id}")
    Call<List<Task>> getTask(@Path("id") int id);

    @Headers("Cache-Control: no-cache")
    @GET("auth/login")
    Call<User> userLoginByEmail(@Query("email_or_phone")String mail, @Query("password") String password);

    @Headers("Cache-Control: no-cache")
    @POST("auth/reg")
    Call<User> userRegistration(@Body User user);

    @POST("data/tasks")
    Call<Task> addtask(@Body Task task, @Query("sr_user_token") String token);

    @POST("data/client")
    Call<Client> addclient(@Body Client client, @Query("sr_user_token") String token);

    @PATCH("data/task/{id}/upd_cont")
    Call<Task> upd_cont(@Path("id") String id, @Body User user);

    @PATCH("data/task/{id}/upd_desc")
    Call<Task> upd_desc(@Path("id") String id,
                        @Query("sr_task_description")String description,
                        @Query("sr_task_start_date")String start,
                        @Query("sr_task_end_date")String end_date,
                        @Query("sr_task_reward")int reward);
}