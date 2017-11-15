package id.technomotion.ui.recentconversation;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.ui.QiscusGroupChatActivity;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import id.technomotion.R;
import id.technomotion.model.Room;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by omayib on 30/10/17.
 */

public class RecentConversationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "ViewHolder";
    private TextView itemName;
    private TextView itemJob;
    private ImageView picture;
    private Room selectedRoom;

    public RecentConversationHolder(View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.textViewRoomName);
        itemJob = (TextView) itemView.findViewById(R.id.textViewJob);
        picture = (ImageView) itemView.findViewById(R.id.imageViewRoomAvatar);
        itemView.setOnClickListener(this);
    }

    public void bindRecentConversation(Room room){
        this.selectedRoom = room;
        this.itemName.setText(room.getName());
        this.itemJob.setText(room.getLatestConversation());
        Picasso.with(this.picture.getContext()).load("http://lorempixel.com/200/200/people/" + room.getName()).into(picture);
    }

    @Override
    public void onClick(final View v) {
        final Activity currentActivity = (RecentConversationsActivity)v.getContext();
        QiscusApi.getInstance()
                .getChatRoom(this.selectedRoom.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QiscusChatRoom>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(QiscusChatRoom qiscusChatRoom) {
                        currentActivity.startActivity(QiscusGroupChatActivity.generateIntent(currentActivity, qiscusChatRoom));
                    }
                });
    }
}
