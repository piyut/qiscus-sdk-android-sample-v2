package id.technomotion.ui.privatechatcreation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qiscus.sdk.Qiscus;

import java.util.ArrayList;
import java.util.List;

import id.technomotion.R;
import id.technomotion.model.Person;
import id.technomotion.repository.AlumnusRepository;
import id.technomotion.repository.RepositoryTransactionListener;
import id.technomotion.ui.groupchatcreation.GroupChatCreationActivity;

/**
 * Created by omayib on 18/09/17.
 */

public class PrivateChatCreationActivity extends Activity implements RepositoryTransactionListener, ViewHolder.OnContactClickedListener, View.OnClickListener {
    private static final String TAG = "PrivateChatCreationActivity";
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
        mAdapter = new RecyclerAdapter(alumnusList, this);
        mRecyclerView.setAdapter(mAdapter);

        viewGroupCreation.setOnClickListener(this);
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

    @Override
    public void onContactClicked(String userEmail) {
        Qiscus.buildChatWith(userEmail)
                .withSubtitle("Consultation")
                .build(this, new Qiscus.ChatActivityBuilderListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, GroupChatCreationActivity.class));
        finish();
    }
}
