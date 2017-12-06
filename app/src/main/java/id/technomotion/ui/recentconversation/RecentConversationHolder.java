package id.technomotion.ui.recentconversation;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.ui.QiscusGroupChatActivity;
import com.qiscus.sdk.util.QiscusRxExecutor;
import com.squareup.picasso.Picasso;

import id.technomotion.R;
import id.technomotion.model.Room;

/**
 * Created by omayib on 30/10/17.
 */

public class RecentConversationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "ViewHolder";
    private TextView itemName;
    private TextView itemJob;
    private com.qiscus.sdk.ui.view.QiscusCircularImageView picture;
    private Room selectedRoom;
    private TextView lastMessageTime;
    private TextView unreadCounter;

    public RecentConversationHolder(View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.textViewRoomName);
        itemJob = (TextView) itemView.findViewById(R.id.textViewJob);
        picture = (com.qiscus.sdk.ui.view.QiscusCircularImageView) itemView.findViewById(R.id.imageViewRoomAvatar);
        lastMessageTime = (TextView) itemView.findViewById(R.id.textViewRoomTime);
        unreadCounter = (TextView) itemView.findViewById(R.id.unreadCounterView);
        itemView.setOnClickListener(this);
    }

    public void bindRecentConversation(Room room) {
        this.selectedRoom = room;
        this.itemName.setText(room.getName());
        this.itemJob.setText(room.getLatestConversation());
        this.lastMessageTime.setText(room.getLastMessageTime());
        this.unreadCounter.setText(String.valueOf(room.getUnreadCounter()));
        String imagePath = "http://lorempixel.com/200/200/people/" + room.getName();
        imagePath = room.getOnlineImage();
        Picasso.with(this.picture.getContext()).load(imagePath).into(picture);
    }

    @Override
    public void onClick(final View v) {
        final Activity currentActivity = (RecentConversationsActivity) v.getContext();
        //fetch qiscuschatroom in qiscus database
        QiscusChatRoom savedChatRoom = Qiscus.getDataStore().getChatRoom(selectedRoom.getId());

        if (savedChatRoom != null) {
            currentActivity.startActivity(QiscusGroupChatActivity.generateIntent(currentActivity, savedChatRoom));
        } else {
            //fetching API when we dont have any qiscus chat room in qiscus database
            QiscusRxExecutor.execute(QiscusApi
                    .getInstance().getChatRoom(selectedRoom.getId()),
                    new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                @Override
                public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                    Qiscus.getDataStore().addOrUpdate(qiscusChatRoom);
                    currentActivity.startActivity(QiscusGroupChatActivity.
                            generateIntent(currentActivity, qiscusChatRoom));
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }

    }
}
