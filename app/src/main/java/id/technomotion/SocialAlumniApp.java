package id.technomotion;

import android.app.Application;

import com.qiscus.sdk.Qiscus;

/**
 * Created by omayib on 18/09/17.
 */

public class SocialAlumniApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Qiscus.init(this,"socialalu-djhi8lqbprs");
    }
}
