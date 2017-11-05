package id.technomotion.ui.groupchatcreation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.technomotion.R;
import id.technomotion.model.Person;
import id.technomotion.repository.AlumnusRepository;
import id.technomotion.repository.RepositoryTransactionListener;

/**
 * Created by omayib on 05/11/17.
 */

public class GroupChatCreationActivity extends AppCompatActivity implements RepositoryTransactionListener, ViewHolder.OnContactClickedListener {
    private static final String TAG = "GroupChatCreationActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private ArrayList<Person> alumnusList;
    private AlumnusRepository alumnusRepository;
    private View viewGroupCreation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumni_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Select contact");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        viewGroupCreation = findViewById(R.id.newGroupLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAlumni);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        alumnusRepository = new AlumnusRepository();
        alumnusRepository.setListener(this);

        alumnusList = alumnusRepository.getCachedData();


        viewGroupCreation.setVisibility(View.GONE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        alumnusRepository.loadAll();
    }
    @Override
    public void onLoadAlumnusSucceeded(final List<Person> alumnus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new RecyclerAdapter((ArrayList<Person>) alumnus, GroupChatCreationActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onContactSelected(String userEmail) {
        Log.d(TAG, "onContactSelected() called with: userEmail = [" + userEmail + "]");
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onContactUnselected(String userEmail) {
        Log.d(TAG, "onContactUnselected() called with: userEmail = [" + userEmail + "]");
    }
}
