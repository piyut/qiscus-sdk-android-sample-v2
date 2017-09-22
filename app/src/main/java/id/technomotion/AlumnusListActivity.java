package id.technomotion;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import id.technomotion.db.PersonPersistance;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by omayib on 18/09/17.
 */

public class AlumnusListActivity extends Activity{
    private static final String TAG = "AlumnusListActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private Realm realm;
    private ArrayList<Person> alumnus = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_list);

        realm = Realm.getDefaultInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAlumni);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new RecyclerAdapter(alumnus);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (alumnus.isEmpty()){
            alumnus.addAll(loadDataFromLocal());
            if (alumnus.isEmpty()){
                loadDataFromServer();
            }
        }else{
            Log.d(TAG, "onResume: load data from cache");
        }
        mAdapter.notifyDataSetChanged();


    }

    private void loadDataFromServer() {
        Log.d(TAG, "loadDataFromServer: ");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.0.2.2:3000/alumnus").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            makePersistanceInLocal(alumnus);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makePersistanceInLocal(List<Person> persons){
        Log.d(TAG, "makePersistanceInLocal: ");
        realm.beginTransaction();
        for (Person p :
                persons) {
            PersonPersistance personPersistance = realm.createObject(PersonPersistance.class);
            personPersistance.setEmail(p.getEmail());
            personPersistance.setId(p.getId());
            personPersistance.setJob(p.getJob());
            personPersistance.setName(p.getName());
        }
        realm.commitTransaction();
        

    }
    private ArrayList<Person> loadDataFromLocal(){
        Log.d(TAG, "loadDataFromLocal: ");
        RealmResults<PersonPersistance> personPersistances = realm.where(PersonPersistance.class).findAll();
        ArrayList<Person> alumnus = new ArrayList<>();
        for (int i = 0; i < personPersistances.size(); i++) {
            PersonPersistance item = personPersistances.get(i);
            alumnus.add(new Person(item.getId(),item.getName(),item.getEmail(),item.getJob()));
        }
        return alumnus;
    }
}
