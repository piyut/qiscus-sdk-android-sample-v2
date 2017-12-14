package id.technomotion;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import id.technomotion.util.Configuration;
import id.technomotion.util.RealTimeChatroomHandler;
import io.realm.Realm;

/**
 * Created by omayib on 18/09/17.
 */

public class SampleApp extends Application {
    private RealTimeChatroomHandler chatroomHandler;
    private static SampleApp INSTANCE;

    public static SampleApp getInstance() {
        return INSTANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Qiscus.init(this, Configuration.QISCUS_APP_ID);
        chatroomHandler = new RealTimeChatroomHandler();
        Qiscus.getChatConfig()
                .setStatusBarColor(R.color.colorPrimaryDark)
                .setAppBarColor(R.color.colorPrimary)
                .setLeftBubbleColor(R.color.emojiSafeYellow)
                .setRightBubbleColor(R.color.colorPrimary)
                .setRightBubbleTextColor(R.color.qiscus_white)
                .setRightBubbleTimeColor(R.color.qiscus_white)
                .setReadIconColor(R.color.colorAccent)
                .setEmptyRoomImageResource((R.drawable.ic_room_empty))
                .setNotificationSmallIcon(R.drawable.ic_logo_qiscus_small)
                .setNotificationBigIcon(R.drawable.ic_logo_qiscus)
                .setEmptyRoomTitleColor(R.color.orangeIcon)
                .setAccentColor(R.color.colorAccent);
        Realm.init(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe
    public void onReceivedComment(QiscusCommentReceivedEvent event) {
        chatroomHandler.updateChatrooms(event.getQiscusComment());
    }

    public RealTimeChatroomHandler getChatroomHandler() {
        return chatroomHandler;
    }
}
