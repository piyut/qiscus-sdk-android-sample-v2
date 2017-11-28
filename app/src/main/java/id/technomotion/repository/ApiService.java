package id.technomotion.repository;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by asyrof on 20/11/17.
 */

public interface ApiService {

    @GET("/api/contacts")
    Call<JsonObject> getContacts();

}
