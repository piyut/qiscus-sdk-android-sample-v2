package id.technomotion;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.qiscus.sdk.Qiscus;

import id.technomotion.util.Configuration;
import io.realm.Realm;

/**
 * Created by omayib on 18/09/17.
 */

public class SocialAlumniApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Qiscus.init(this, Configuration.QISCUS_APP_ID);

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
    }
}
