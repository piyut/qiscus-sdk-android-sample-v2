package id.technomotion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactHolder> {
    private final ArrayList<Person> persons;

    public ContactRecyclerAdapter(ArrayList<Person> mPhotos) {
        this.persons = mPhotos;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni, parent, false);
        return new ContactHolder(inflatedView);
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