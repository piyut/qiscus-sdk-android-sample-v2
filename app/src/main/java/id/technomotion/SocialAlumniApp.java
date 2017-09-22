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
        Qiscus.init(this,"socialalu-djhi8lqbprs");
        Realm.init(this);
    }
}
