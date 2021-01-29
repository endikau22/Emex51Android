package retrofit;

import intefaces.UserInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * REST client generated for REST resource:UserFacadeREST
 */
public class UserRestClient {
    private static String BASE_URL = "http:// 192.168.1.36:8080/EMEX51CRUDServer/webresources/user/";
    
    public static UserInterface getUser (){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit builder = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create()).client(httpClient.build()).build();
        UserInterface userInterface = builder.create(UserInterface.class);
        return userInterface;
    }
}
