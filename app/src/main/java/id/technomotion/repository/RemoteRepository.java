package id.technomotion.repository;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.technomotion.Person;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by omayib on 22/09/17.
 */

public class RemoteRepository  implements Repository {
    private static final String TAG = "RemoteRepository";
    private OkHttpClient okHttpClient;
    public RemoteRepository() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void loadAll(final RepositoryCallback<List<Person>> callback) {
        final ArrayList<Person> alumnus = new ArrayList<>();
        Request request = new Request.Builder().url("http://10.0.2.2:3000/alumnus").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray data = new JSONArray(response.body().string());
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        Log.d(TAG, "onResponse: "+obj.toString());
                        alumnus.add(new Person(obj.getString("id"),obj.getString("name"),obj.getString("email"),obj.getString("work")));
                    }
                    callback.onSucceed(alumnus);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void save(List<Person> persons) {

    }

    @Override
    public void save(Person person) {

    }
}
