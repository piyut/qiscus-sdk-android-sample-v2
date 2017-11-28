package id.technomotion.ui.privatechatcreation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qiscus.sdk.Qiscus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.technomotion.R;
import id.technomotion.model.Person;
import id.technomotion.repository.AlumnusRepository;
import id.technomotion.repository.RepositoryTransactionListener;
import id.technomotion.ui.groupchatcreation.GroupChatCreationActivity;
import retrofit2.HttpException;

/**
 * Created by omayib on 18/09/17.
 */

public class PrivateChatCreationActivity extends AppCompatActivity implements RepositoryTransactionListener, ViewHolder.OnContactClickedListener, View.OnClickListener,ChatWithStrangerDialogFragment.onStrangerNameInputtedListener {
    private static final String TAG = "PrivateChatCreationActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private ArrayList<Person> alumnusList;
    private AlumnusRepository alumnusRepository;
    private View viewGroupCreation,viewChatWithStranger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //toolbar.setTitle("Select contact");
        //toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setVisibility(View.GONE);

        this.setTitle("Create New Chat");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewGroupCreation = findViewById(R.id.newGroupLayout);
        viewChatWithStranger = findViewById(R.id.chatWithStrangerLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAlumni);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        alumnusRepository = new AlumnusRepository();
        alumnusRepository.setListener(this);

        alumnusList = alumnusRepository.getCachedData();
        mAdapter = new RecyclerAdapter(alumnusList, this);
        mRecyclerView.setAdapter(mAdapter);

        viewGroupCreation.setOnClickListener(this);
        viewChatWithStranger.setOnClickListener(this);
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
    public void onContactClicked(final String userEmail) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure to make a conversation with "+userEmail+" ?")
                .setCancelable(true)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Qiscus.buildChatWith(userEmail)
                                .build(PrivateChatCreationActivity.this, new Qiscus.ChatActivityBuilderListener() {
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
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.newGroupLayout:
                startActivity(new Intent(this, GroupChatCreationActivity.class));
                finish();
                break;

            case R.id.chatWithStrangerLayout:
                ChatWithStrangerDialogFragment dialogFragment = new ChatWithStrangerDialogFragment(this);
                dialogFragment.show(getFragmentManager(),"show_group_name");
                break;
            default:
                break;
        }

    }

    @Override
    public void onStrangerNameInputted(String email) {
        Qiscus.buildChatWith(email)
                .build(this, new Qiscus.ChatActivityBuilderListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivity(intent);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof HttpException) { //Error response from server
                            HttpException e = (HttpException) throwable;
                            try {
                                String errorMessage = e.response().errorBody().string();
                                Log.e(TAG, errorMessage);
                                showError(errorMessage);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else if (throwable instanceof IOException) { //Error from network
                            showError("Can not connect to qiscus server!");
                        } else { //Unknown error
                            showError("Unexpected error!");
                        }
                    }
                });
    }

    private void showError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
