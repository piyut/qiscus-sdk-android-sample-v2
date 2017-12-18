package id.technomotion.ui.homepagetab;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;
import com.squareup.picasso.Picasso;

import id.technomotion.R;
import id.technomotion.ui.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView displayName,userId,logoutText;
    private QiscusAccount qiscusAccount;
    private ImageView logoutButton;
    private com.qiscus.sdk.ui.view.QiscusCircularImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        picture = (com.qiscus.sdk.ui.view.QiscusCircularImageView) findViewById(R.id.single_avatar);
        displayName = (TextView) findViewById(R.id.profile_display_name);
        userId = (TextView) findViewById(R.id.profile_user_id);
        qiscusAccount = Qiscus.getQiscusAccount();
        displayName.setText(qiscusAccount.getUsername());
        this.setTitle("Profile");
        userId.setText(qiscusAccount.getEmail());
        logoutText =(TextView) findViewById(R.id.logout_text);
        logoutButton = (ImageView) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);
        logoutText.setOnClickListener(this);
        String avatarUrl = qiscusAccount.getAvatar();
        Picasso.with(this.picture.getContext()).load(avatarUrl).fit().centerCrop().into(picture);
    }

    @Override
    public void onClick(View view) {
        logout();
    }

    private void logout() {
        Qiscus.clearUser();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
    }
}
