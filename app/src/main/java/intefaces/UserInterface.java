package intefaces;

import java.util.List;

import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
/**
 * Interface encapsulating methods for <code>User</code> Management for Emex51 project.
 */
public interface UserInterface {
    @POST
    public void create(@Body User user);
    @PUT
    public void edit(@Body User user);
    @DELETE("{id}")
    public void remove(@Path("id") Integer id);
    @GET("{id}")
    public Call<User> find(@Path("id") String id);
    @GET("all")
    public Call<List<User>> findAll();
    @GET("login/{login}")
    public Call<User> findUsersByLogin(@Path("login") String login);
    @PUT("forgotPassword")
    public Call<Void> editForgotPassword(@Body User user);
    @PUT("changePassword/{oldPass}/{newPass}")
    public Call<Void> editChangePassword(@Body User user,@Path("oldPass") String oldPass, @Path("newPass") String newPass);
    @GET("loginUser/{login}/{password}")
    public Call<User> loginUser(@Path("login") String login, @Path("password") String password);
}
