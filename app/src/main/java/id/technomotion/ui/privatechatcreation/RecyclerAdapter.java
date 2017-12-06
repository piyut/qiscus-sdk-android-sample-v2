package id.technomotion.ui.privatechatcreation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.technomotion.R;
import id.technomotion.model.Person;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final ArrayList<Person> persons;
    private final ViewHolder.OnContactClickedListener listener;

    public RecyclerAdapter(ArrayList<Person> persons, ViewHolder.OnContactClickedListener listener) {
        this.persons = persons;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni, parent, false);
        return new ViewHolder(inflatedView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = persons.get(position);
        boolean newInitial;
        char initial = person.getName().charAt(0);
        newInitial = positionNewInitial(position,person);
        holder.bindAlumni(person,newInitial,initial);
    }

    private boolean positionNewInitial(int position,Person currentPerson) {
        boolean result;
        if (position==0) {
            result =true;
        }
        else {
            Person beforePerson= persons.get(position-1);
            char currentInitial = currentPerson.getName().toLowerCase().charAt(0);
            char beforeInitial = beforePerson.getName().toLowerCase().charAt(0);
            result = !(currentInitial == beforeInitial);        }
        return result;
    }

    @Override
    public int getItemCount() {
        return this.persons.size();
    }


}