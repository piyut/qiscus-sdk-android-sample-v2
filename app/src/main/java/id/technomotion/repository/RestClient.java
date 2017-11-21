package id.technomotion.repository;
import android.support.annotation.NonNull;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 10/14/17
 */

public class RestClient {
    private String url="http://dashboard-sample.herokuapp.com/rest/";
    private static RestClient ourInstance;

    private RestClient() {
    }

    public static RestClient getInstance() {
        if (ourInstance == null) ourInstance = new RestClient();
        return ourInstance;
    }

    public ApiService getApi(String baseUrl) {
        return getRetrofit(baseUrl).create(ApiService.class);
    }

    public ApiService getApi() {
        return getApi(url);
    }

    @NonNull
    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}