package id.technomotion.ui.groupchatcreation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.technomotion.R;
import id.technomotion.model.Person;
import id.technomotion.ui.privatechatcreation.ViewHolder;

public class RecyclerAdapter extends RecyclerView.Adapter<id.technomotion.ui.privatechatcreation.ViewHolder> {
    private final ArrayList<Person> persons;
    private final id.technomotion.ui.privatechatcreation.ViewHolder.OnContactClickedListener listener;

    public RecyclerAdapter(ArrayList<Person> persons, id.technomotion.ui.privatechatcreation.ViewHolder.OnContactClickedListener listener) {
        this.persons = persons;
        this.listener = listener;
    }

    @Override
    public id.technomotion.ui.privatechatcreation.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni, parent, false);
        return new id.technomotion.ui.privatechatcreation.ViewHolder(inflatedView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = persons.get(position);
        holder.bindAlumni(person);
    }

    @Override
    public int getItemCount() {
        return this.persons.size();
    }


}