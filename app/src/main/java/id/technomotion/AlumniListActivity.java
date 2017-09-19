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
        alumnis.add(new Alumni("21dd","Andri Atmojo","andri@yahoo.com", "Freelance Android"));
        alumnis.add(new Alumni("21ds","Joki Widyawan","joki@yahoo.com", "Freelance Web"));
        alumnis.add(new Alumni("2eds","Eko Purnawan","eko@yahoo.com", "Freelance Web"));
        alumnis.add(new Alumni("2gds","Susilo Andi S","silo@yahoo.com", "Freelance iOS"));
        alumnis.add(new Alumni("3gds","Prakoso Binar","binar@yahoo.com", "Freelance Android"));
        alumnis.add(new Alumni("53ds","Gilang Santoso","gilang@yahoo.com", "Freelance Web"));
        alumnis.add(new Alumni("3tgs","Atmojo Rakoso","mojo@yahoo.com", "Freelance Backend"));
        mAdapter = new RecyclerAdapter(alumnis);
        mRecyclerView.setAdapter(mAdapter);
    }
}
