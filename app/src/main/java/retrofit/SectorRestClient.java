package retrofit;

import intefaces.SectorInterface;
import intefaces.UserInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class SectorRestClient {
    private static String BASE_URL = "http://192.168.20.146:8080/EMEX51CRUDServer/webresources/sector/";

    public static SectorInterface getSector (){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit builder = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
        .client(httpClient.build()).build();
        SectorInterface sectorInterface = builder.create(SectorInterface.class);
        return sectorInterface;
    }
}
