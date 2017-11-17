package id.technomotion.ui.privatechatcreation;

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
    private ImageView picture;
    private Person selectedContact;
    private CheckBox checkBox;
    private final OnContactClickedListener listener;

    public ViewHolder(View itemView, OnContactClickedListener listener) {
        super(itemView);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        itemName = (TextView) itemView.findViewById(R.id.textViewName);
        itemJob = (TextView) itemView.findViewById(R.id.textViewJob);
        picture = (ImageView) itemView.findViewById(R.id.imageViewProfile);
        this.listener = listener;

        itemView.setOnClickListener(this);
        checkBox.setVisibility(View.GONE);
    }

    public void bindAlumni(Person person){
        this.selectedContact = person;
        this.itemName.setText(person.getName());
        this.itemJob.setText(person.getJob());
        //Picasso.with(this.picture.getContext()).load("http://lorempixel.com/200/200/people/"+ person.getName()).into(picture);
    }

    @Override
    public void onClick(final View v) {
        this.listener.onContactClicked(this.selectedContact.getEmail());
    }

    public interface OnContactClickedListener{
        public void onContactClicked(String userEmail);
    }
}
