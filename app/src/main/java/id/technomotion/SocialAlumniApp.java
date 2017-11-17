package id.technomotion;

import android.app.Application;

import com.qiscus.sdk.Qiscus;

import io.realm.Realm;

/**
 * Created by omayib on 18/09/17.
 */

public class SocialAlumniApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Qiscus.init(this,"sampleapp-65ghcsaysse");
        Qiscus.getChatConfig()
                .setStatusBarColor(R.color.colorPrimaryDark)
                .setAppBarColor(R.color.colorPrimary)
                .setLeftBubbleColor(R.color.colorAccent)
                .setRightBubbleColor(R.color.colorPrimary)
                .setRightBubbleTextColor(R.color.qiscus_white)
                .setAccentColor(R.color.colorAccent);
        Realm.init(this);
    }
}
