package intefaces;

import model.Sector;
import model.Sectores;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SectorInterface {
    @GET("{id}")
    public Call<Sector> find(@Path("id") Integer id);
    @GET("all")
    public Call<Sectores> findAll();
}
