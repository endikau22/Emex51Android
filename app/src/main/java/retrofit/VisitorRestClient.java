package retrofit;

import intefaces.UserInterface;
import intefaces.VisitorInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class VisitorRestClient {
    private static String BASE_URL = "http://192.168.1.36:8080/EMEX51Server/webresources/visitor/";

    public static VisitorInterface getVisitor (){

        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient).build();
        VisitorInterface visitorInterface = retrofit.create(VisitorInterface.class);
        return visitorInterface;
    }
}
