package retrofit;

import intefaces.VisitorInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class VisitorRestClient {
    private static String BASE_URL = "http://192.168.20.146:8080/EMEX51CRUDServer/webresources/visitor/";

    public static VisitorInterface getVisitor (){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit builder = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create()).client(httpClient.build()).build();
        VisitorInterface visitorInterface = builder.create(VisitorInterface.class);
        return visitorInterface;
    }
}
