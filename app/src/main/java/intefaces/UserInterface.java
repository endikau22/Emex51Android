package intefaces;

import java.util.List;

import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserInterface {

    @POST
    public Call<Void> create(@Body User user);
    @PUT
    public Call<Void> edit(@Body User user);
    @DELETE("{id}")
    public Call<Void> remove(@Path("id") Integer id);
    @GET("{id}")
    public Call<User> find(@Path("id") Integer id);
    @GET("all")
    public Call<List<User>> findAllEmployees();
    @GET("login/{login}")
    public Call<List<User>> findUsersByLogin(@Path("login") String login);
    @PUT("forgotPassword/{email}")
    public Call<Void> editForgotPassword(@Path("email") String email);
    @PUT("changePassword/{oldPass}/{newPass}")
    public Call<Void> editChangePassword(@Path("oldPass") String oldPass,@Path("newPass") String newPass,@Body User user);
    @GET("loginUser/{login}/{password}")
    public Call<Void> loginUser(@Path("login") String login, @Path("password") String password);
}
