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
import rx.Observable;


public interface SunriseForestService {
    @GET("data/tasks")
    Observable<List<Task>> getTasks(@Query("sr_user_token") String token);

    @GET("data/tasks/{id}")
    Observable<List<Task>> getTask(@Path("id") int id);

    @Headers("Cache-Control: no-cache")
    @GET("auth/login")
    Observable<User> userLoginByEmail(@Query("email_or_phone")String mail, @Query("password") String password);

    @Headers("Cache-Control: no-cache")
    @POST("auth/reg")
    Observable<User> userRegistration(@Body User user);

    @POST("data/tasks")
    Observable<Task> addtask(@Body Task task, @Query("sr_user_token") String token);

    @POST("data/client")
    Observable<Client> addclient(@Body Client client, @Query("sr_user_token") String token);

    @PATCH("data/task/{id}/upd_task_contractor")
    Observable<Task> taskReservation(@Path("id") String id, @Body User user);

    @PATCH("data/task/{id}/upd_task_description")
    Observable<Task> updDescription(@Path("id") String id,
                        @Query("sr_task_description")String description,
                        @Query("sr_task_start_date")String start,
                        @Query("sr_task_end_date")String end_date,
                        @Query("sr_task_reward")int reward);


    @PATCH("data/user/{id}/upd_user_profile")
    Observable<User> updProfile(@Path("id") String id,
                          @Body User user);

    @GET("data/user/{id}/get_user_tasks_info")
    Observable<User> getProfileInfo(@Path("id") String id);

}