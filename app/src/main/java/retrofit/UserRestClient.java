package retrofit;

import intefaces.UserInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class UserRestClient {
    private static String BASE_URL = "http://localhost:8080/EMEX51Server/webresources/user/";
    
    public static UserInterface getClient (){

        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient).build();
        UserInterface userInterface = retrofit.create(UserInterface.class);
        return userInterface;
    }
}
