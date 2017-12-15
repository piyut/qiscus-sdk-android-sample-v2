package id.technomotion.ui.privatechatcreation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.SearchView;

import com.qiscus.sdk.Qiscus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.technomotion.R;
import id.technomotion.model.Person;
import id.technomotion.repository.AlumnusRepository;
import id.technomotion.repository.RepositoryTransactionListener;
import id.technomotion.ui.groupchatcreation.GroupChatCreationActivity;
import id.technomotion.ui.homepagetab.ContactFragment;
import retrofit2.HttpException;

/**
 * Created by omayib on 18/09/17.
 */

public class PrivateChatCreationActivity extends AppCompatActivity implements RepositoryTransactionListener, ViewHolder.OnContactClickedListener, ChatWithStrangerDialogFragment.onStrangerNameInputtedListener {
    private static final String TAG = "PrivateChatCreationActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private ArrayList<Person> alumnusList;
    private AlumnusRepository alumnusRepository;
    public static String GROUP_CHAT_ID="GROUP_CHAT_ID";
    public static String STRANGER_CHAT_ID="STRANGER_CHAT_ID";
    private Person groupChatHolder = new Person(GROUP_CHAT_ID,"Create Group Chat",GROUP_CHAT_ID,"placeholder");
    private Person strangerChatHolder = new Person(STRANGER_CHAT_ID,"Chat With Stranger",STRANGER_CHAT_ID,"placeholder");

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
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAlumni);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        alumnusRepository = new AlumnusRepository();
        alumnusRepository.setListener(this);
        ArrayList<Person> alumnusListTemp = alumnusRepository.getCachedData();
        alumnusList = new ArrayList<Person>(alumnusListTemp);
        //alumnusList = alumnusRepository.getCachedData();
        alumnusList.add(0,groupChatHolder);
        alumnusList.add(1,strangerChatHolder);

        mAdapter = new RecyclerAdapter(alumnusList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //alumnusRepository.loadAll();
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

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override

            public boolean onQueryTextSubmit(String query) {

                query = query.toString().toLowerCase();

                final ArrayList<Person> filteredList = new ArrayList<>();

                for (int i = 0; i < alumnusList.size(); i++) {

                    final String text = alumnusList.get(i).getEmail().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(alumnusList.get(i));
                    }
                }
                mAdapter = new RecyclerAdapter(filteredList,PrivateChatCreationActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed

                searchView.clearFocus();



                return true;

            }



            @Override

            public boolean onQueryTextChange(String newText) {
                newText = newText.toString().toLowerCase();

                final ArrayList<Person> filteredList = new ArrayList<>();

                for (int i = 0; i < alumnusList.size(); i++) {

                    final String text = alumnusList.get(i).getName().toLowerCase();
                    if (text.contains(newText)) {

                        filteredList.add(alumnusList.get(i));
                    }
                }
                mAdapter = new RecyclerAdapter(filteredList,PrivateChatCreationActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed

                return true;

            }

        });

        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public void onContactClicked(final String userEmail) {
        if (userEmail.equals(GROUP_CHAT_ID))
        {
            startActivity(new Intent(this, GroupChatCreationActivity.class));
            finish();
        }

        else if (userEmail.equals(STRANGER_CHAT_ID)) {
            ChatWithStrangerDialogFragment dialogFragment = new ChatWithStrangerDialogFragment(this);
            dialogFragment.show(getFragmentManager(),"show_group_name");
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure to make a conversation with " + userEmail + " ?")
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
                                //Log.e(TAG, errorMessage);
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
        if (item.getItemId() == R.id.action_search)
        {

        }
        else {
            finish();
        }

        return true;
    }

}
