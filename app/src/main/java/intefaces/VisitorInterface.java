package intefaces;

import model.Visitor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
/**
 * Interface encapsulating methods for <code>Visitor</code> Management for Emex51 project.
 */
public interface VisitorInterface {
    @POST(".")
    public Call<Void> create(@Body Visitor visitor);
    @PUT(".")
    public Call<Void> edit(@Body Visitor visitor);
    @DELETE("{id}")
    public Call<Void> remove(@Path("id") Integer id);
    @GET("{id}")
    public Call <Visitor> find(@Path("id") Integer id);
    @GET("all")
    public Call <Visitor> findAllVisitors();
    @GET("name/{name}")
    public Call <Visitor> findVisitorsByName(@Path("name") String name);
    @GET("loginVisitor/{login}/{password}")
    public Call <Visitor> loginVisitor(@Path("login") String login, @Path("password") String password);
}
