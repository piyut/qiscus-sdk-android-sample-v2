package id.technomotion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_conversations);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerRecentConversation);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        fabCreateNewConversation = (FloatingActionButton) findViewById(R.id.buttonCreateNewConversation);
        fabCreateNewConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecentConversationsActivity.this, AlumnusListActivity.class);
                startActivity(intent);
            }
        });

        adapter = new RecentConversationRecyclerAdapter(rooms);
        recyclerView.setAdapter(adapter);

        QiscusApi.getInstance().getChatRooms(1, 20, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<QiscusChatRoom>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(List<QiscusChatRoom> qiscusChatRooms) {
                        Log.d(TAG, "onNext: size" +qiscusChatRooms.size());
                        for (int i = 0; i < qiscusChatRooms.size(); i++) {
                            rooms.add(new Room(qiscusChatRooms.get(i).getId(),qiscusChatRooms.get(i).getName()));
                        }
                    }
                });
    }
}
