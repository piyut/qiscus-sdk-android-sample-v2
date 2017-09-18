package id.technomotion;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by omayib on 18/09/17.
 */

public class AlumniListActivity extends Activity{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAlumni);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ArrayList<Alumni> alumnis = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            alumnis.add(new Alumni(UUID.randomUUID().toString(),"name "+i,"email"+i, "job "+i));
        }
        mAdapter = new RecyclerAdapter(alumnis);
        mRecyclerView.setAdapter(mAdapter);
    }
}
