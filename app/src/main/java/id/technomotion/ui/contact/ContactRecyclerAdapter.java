package id.technomotion.ui.contact;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.technomotion.R;
import id.technomotion.model.Person;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactHolder> {
    private final ArrayList<Person> persons;
    private final ContactHolder.OnContactClickedListener listener;

    public ContactRecyclerAdapter(ArrayList<Person> persons, ContactHolder.OnContactClickedListener listener) {
        this.persons = persons;
        this.listener = listener;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni, parent, false);
        return new ContactHolder(inflatedView, listener);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Person person = persons.get(position);
        holder.bindAlumni(person);
    }

    @Override
    public int getItemCount() {
        return this.persons.size();
    }


}