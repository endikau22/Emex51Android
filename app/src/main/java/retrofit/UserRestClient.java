package retrofit;

import intefaces.UserInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class UserRestClient {
    private static String BASE_URL = "http://192.168.1.36:8080/EMEX51CRUDServer/webresources/user/";
    
    public static UserInterface getUser (){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient).build();
        UserInterface userInterface = retrofit.create(UserInterface.class);
        return userInterface;
    }
}
