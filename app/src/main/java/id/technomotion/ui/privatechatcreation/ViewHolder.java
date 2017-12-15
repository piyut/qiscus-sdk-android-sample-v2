package id.technomotion.ui.privatechatcreation;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.UUID;

import id.technomotion.R;
import id.technomotion.model.Person;


/**
 * Created by omayib on 18/09/17.
 */

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "ViewHolder";
    private TextView itemName;
    private TextView itemJob;
    private com.qiscus.sdk.ui.view.QiscusCircularImageView picture;
    private Person selectedContact;
    private CheckBox checkBox;
    private final OnContactClickedListener listener;

    public ViewHolder(View itemView, OnContactClickedListener listener) {
        super(itemView);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        itemName = (TextView) itemView.findViewById(R.id.textViewName);
        itemJob = (TextView) itemView.findViewById(R.id.textViewJob);
        picture = (com.qiscus.sdk.ui.view.QiscusCircularImageView) itemView.findViewById(R.id.imageViewProfile);
        this.listener = listener;

        itemView.setOnClickListener(this);
        checkBox.setVisibility(View.GONE);
    }

    public void bindAlumni(Person person){
        this.selectedContact = person;
        this.itemName.setText(person.getName());
        this.itemJob.setText(person.getJob());
        Context context = this.picture.getContext();
        if (person.getEmail().equals(PrivateChatCreationActivity.GROUP_CHAT_ID)) {
            Picasso.with(context).load(R.drawable.ic_create_group).into(picture);
            picture.setColorFilter(ContextCompat.getColor(context, R.color.orangeIcon), PorterDuff.Mode.MULTIPLY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                itemName.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
            }
        }
        else if (person.getEmail().equals(PrivateChatCreationActivity.STRANGER_CHAT_ID))
        {
            Picasso.with(context).load(R.drawable.ic_stranger).into(picture);
            picture.setColorFilter(ContextCompat.getColor(context, R.color.orangeIcon), PorterDuff.Mode.MULTIPLY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                itemName.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
            }
        }
        else {
            String avatarUrl = person.getAvatarUrl();
            Picasso.with(this.picture.getContext()).load(avatarUrl).fit().centerCrop().into(picture);
        }
    }

    @Override
    public void onClick(final View v) {
        this.listener.onContactClicked(this.selectedContact.getEmail());
    }

    public interface OnContactClickedListener{
        public void onContactClicked(String userEmail);
    }
}
