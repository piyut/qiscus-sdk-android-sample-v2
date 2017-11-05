package id.technomotion.ui.recentconversation;

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

public class RecentConversationRecyclerAdapter extends RecyclerView.Adapter<RecentConversationHolder> {
    private ArrayList<Room> rooms;

    public RecentConversationRecyclerAdapter(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public RecentConversationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_conversation, parent, false);
        return new RecentConversationHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecentConversationHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bindRecentConversation(room);
    }

    @Override
    public int getItemCount() {
        return this.rooms.size();
    }
}
