package id.technomotion.ui.groupchatcreation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.ui.QiscusGroupChatActivity;

import java.util.ArrayList;
import java.util.List;

import id.technomotion.R;
import id.technomotion.model.Person;
import id.technomotion.repository.AlumnusRepository;
import id.technomotion.repository.RepositoryTransactionListener;
import id.technomotion.ui.privatechatcreation.PrivateChatCreationActivity;

/**
 * Created by omayib on 05/11/17.
 */

public class GroupChatCreationActivity extends AppCompatActivity implements RepositoryTransactionListener, ViewHolder.OnContactClickedListener, View.OnClickListener, GroupNameDialogFragment.OnGroupNameCreatedListener,GroupInfoFragment.OnFragmentInteractionListener {
    private static final String TAG = "GroupChatCreationActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private ArrayList<Person> alumnusList;
    private ArrayList<Person> selectedList = new ArrayList<>();
    private AlumnusRepository alumnusRepository;
    private View viewGroupCreation,viewChatWithStranger;
    private ArrayList<String> contacts = new ArrayList<>();
    private FloatingActionButton nextFab;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumni_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setVisibility(View.GONE);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        this.setTitle("Select Participants");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        nextFab = (FloatingActionButton) findViewById(R.id.nextFloatingButton);
        viewGroupCreation = findViewById(R.id.newGroupLayout);
        viewChatWithStranger = findViewById(R.id.chatWithStrangerLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAlumni);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        alumnusRepository = new AlumnusRepository();
        alumnusRepository.setListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        alumnusList = alumnusRepository.getCachedData();

        viewChatWithStranger.setVisibility(View.GONE);
        viewGroupCreation.setVisibility(View.GONE);
        nextFab.setVisibility(View.VISIBLE);
        nextFab.setOnClickListener(this);
    }


    @SuppressLint("LongLogTag")
    private void createGroupChat(String groupName) {
        progressDialog.show();
        Qiscus.buildGroupChatRoom(groupName,contacts).build(new Qiscus.ChatBuilderListener() {
            @Override
            public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                progressDialog.dismiss();
                startActivity(QiscusGroupChatActivity.generateIntent(GroupChatCreationActivity.this, qiscusChatRoom));
                finish();
            }

            @Override
            public void onError(Throwable throwable) {
                progressDialog.dismiss();
                throwable.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if  (!isFragmentOn()) {
            alumnusRepository.loadAll();
        }
    }

    private boolean isFragmentOn() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        return !(currentFragment == null || !currentFragment.isVisible());

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
        if (!contacts.contains(userEmail))contacts.add(userEmail);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onContactUnselected(String userEmail) {
        if(contacts.contains(userEmail))contacts.remove(userEmail);
    }

    @Override
    public void onClick(View view) {
        if (isFragmentOn())
            {
                GroupInfoFragment currentFragment = (GroupInfoFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                currentFragment.proceedCreateGroup();
            }
        else
            {
                if (selectedContactIsMoreThanOne()){
                    selectedList.clear();
                    for (Person person: alumnusList){
                        for (String  email: contacts)
                        {
                            if (email.equals(person.getEmail())) {
                                selectedList.add(person);
                                Toast.makeText(this,person.getEmail(),Toast.LENGTH_SHORT);
                            }
                        }

                    }
                    Fragment fr =  GroupInfoFragment.newInstance(contacts,selectedList);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.frameLayout, fr)
                            .addToBackStack( "tag" )
                            .commit();

                    //GroupNameDialogFragment dialogFragment = new GroupNameDialogFragment(this);
                    //dialogFragment.show(getFragmentManager(),"show_group_name");
                }else{
                    Toast.makeText(this, "select at least one", Toast.LENGTH_SHORT).show();
                }
            }

    }

    private boolean selectedContactIsMoreThanOne(){
        return this.contacts.size() > 0;
    }

    @Override
    public void onGroupNameCreated(String groupName) {
        createGroupChat(groupName);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public boolean onOptionsItemSelected(MenuItem item){
        onReturn();

        return true;
    }

    private void onReturn() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment == null || !currentFragment.isVisible() ) {
            startActivity(new Intent(this, PrivateChatCreationActivity.class));
            finish();
        }
        else {
//            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.remove(currentFragment);
//
//            getSupportFragmentManager().popBackStack();
//            fragmentTransaction.commit();
            Toast.makeText(this,"remove fragment",Toast.LENGTH_SHORT);
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(currentFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        onReturn();
    }

}
