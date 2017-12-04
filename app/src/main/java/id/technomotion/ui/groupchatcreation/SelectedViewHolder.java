package id.technomotion.ui.groupchatcreation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import id.technomotion.R;
import id.technomotion.model.Person;
import id.technomotion.model.SelectableContact;

/**
 * Created by asyrof on 04/12/17.
 */

public class SelectedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "SelectedViewHolder";
    private TextView itemName;
    private com.qiscus.sdk.ui.view.QiscusCircularImageView picture;
    //private ImageView picture;
    private Person selectedContact;
    private final SelectedViewHolder.OnContactClickedListener listener;
    private com.qiscus.sdk.ui.view.QiscusCircularImageView removeContact;

    public SelectedViewHolder(View itemView, SelectedViewHolder.OnContactClickedListener listener) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.textViewName);
        picture = (com.qiscus.sdk.ui.view.QiscusCircularImageView) itemView.findViewById(R.id.imageViewProfile);
        removeContact = (com.qiscus.sdk.ui.view.QiscusCircularImageView) itemView.findViewById(R.id.remove_contact);
        this.listener = listener;

        removeContact.setOnClickListener(this);
        //itemView.setOnClickListener(this);

    }

    public void bindSelected(Person person) {

        this.selectedContact = person;
        this.itemName.setText(person.getName());
        String avatarUrl = person.getAvatarUrl();
        Picasso.with(this.picture.getContext()).load(avatarUrl).into(picture);
        //Picasso.with(this.picture.getContext()).load("http://lorempixel.com/200/200/people/"+ person.getName()).into(picture);
    }

    @Override
    public void onClick(final View v) {

        //this.checkView.setVisibility(View.INVISIBLE);
        //if (v.getId() == R.id.remove_contact) {
        switch (v.getId()) {
            case R.id.remove_contact:
                this.listener.onSelectionUnselected(this.selectedContact.getEmail());
        }

        //}

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    }

    public interface OnContactClickedListener {
        void onSelectionUnselected(String userEmail);
    }
}
