package id.technomotion.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qiscus.sdk.Qiscus;

import android.os.Handler;

import id.technomotion.R;
import id.technomotion.ui.homepagetab.HomePageTabActivity;
import id.technomotion.ui.login.LoginActivity;
import id.technomotion.ui.recentconversation.RecentConversationsActivity;

public class SplashScreen extends AppCompatActivity {
    private static int splashInterval = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (!Qiscus.hasSetupUser()) {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }
                else {
                    //startActivity(new Intent(SplashScreen.this, RecentConversationsActivity.class));
                    startActivity(new Intent(SplashScreen.this, HomePageTabActivity.class));
                    this.finish();
                }
                this.finish();
            }

            private void finish() {
                // TODO Auto-generated method stub

            }
        }, splashInterval);

    };

}
