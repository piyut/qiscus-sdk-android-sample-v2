package id.technomotion.ui.homepagetab;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.technomotion.R;
import id.technomotion.model.Room;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.technomotion.R;
import id.technomotion.model.Room;

/**
 * Created by omayib on 30/10/17.
 */

public class RecentConversationFragmentRecyclerAdapter extends RecyclerView.Adapter<id.technomotion.ui.homepagetab.RecentConversationFragmentHolder> {
    private ArrayList<Room> rooms;

    public RecentConversationFragmentRecyclerAdapter(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public id.technomotion.ui.homepagetab.RecentConversationFragmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_conversation, parent, false);
        return new id.technomotion.ui.homepagetab.RecentConversationFragmentHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(id.technomotion.ui.homepagetab.RecentConversationFragmentHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bindRecentConversation(room);
    }

    @Override
    public int getItemCount() {
        return this.rooms.size();
    }
}
