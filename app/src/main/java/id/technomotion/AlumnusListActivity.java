package id.technomotion;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.technomotion.repository.AlumnusRepository;
import id.technomotion.repository.RepositoryTransactionListener;

/**
 * Created by omayib on 18/09/17.
 */

public class AlumnusListActivity extends Activity implements RepositoryTransactionListener {
    private static final String TAG = "AlumnusListActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private ArrayList<Person> alumnusList;
    private AlumnusRepository alumnusRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAlumni);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        alumnusRepository = new AlumnusRepository();
        alumnusRepository.setListener(this);

        alumnusList = alumnusRepository.getCachedData();
        mAdapter = new RecyclerAdapter(alumnusList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        alumnusRepository.loadAll();
    }

    @Override
    public void onLoadAlumnusSucceeded(List<Person> alumnus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });

    }
}
