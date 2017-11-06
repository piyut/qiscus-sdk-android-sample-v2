package id.technomotion.ui.recentconversation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;

import java.util.ArrayList;
import java.util.List;

import id.technomotion.ui.privatechatcreation.PrivateChatCreationActivity;
import id.technomotion.R;
import id.technomotion.model.Room;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecentConversationsActivity extends AppCompatActivity {
    private static final String TAG = "RecentConversationsActi";
    private FloatingActionButton fabCreateNewConversation;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Room> rooms = new ArrayList<>();
    private RecentConversationRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_conversations);

        getSupportActionBar().setTitle("Recent conversation");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerRecentConversation);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        fabCreateNewConversation = (FloatingActionButton) findViewById(R.id.buttonCreateNewConversation);
        fabCreateNewConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecentConversationsActivity.this, PrivateChatCreationActivity.class);
                startActivity(intent);
            }
        });

        adapter = new RecentConversationRecyclerAdapter(rooms);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadRecentConversation();
            }
        });

        reloadRecentConversation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        reloadRecentConversation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //gotoContact
        return super.onOptionsItemSelected(item);
    }

    public void reloadRecentConversation(){
        swipeRefreshLayout.setRefreshing(true);
        QiscusApi.getInstance().getChatRooms(1, 20, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<QiscusChatRoom>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<QiscusChatRoom> qiscusChatRooms) {
                        Log.d(TAG, "onNext: size" +qiscusChatRooms.size());
                        for (int i = 0; i < qiscusChatRooms.size(); i++) {
                            Room room = new Room(qiscusChatRooms.get(i).getId(),qiscusChatRooms.get(i).getName());
                            room.setLatestConversation(qiscusChatRooms.get(i).getLastComment().getMessage());
                            if (!rooms.contains(room)){
                                rooms.add(room);
                            }
                        }
                    }
                });
    }
}
