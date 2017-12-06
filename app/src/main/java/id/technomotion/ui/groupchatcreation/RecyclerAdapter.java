package id.technomotion.ui.groupchatcreation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import id.technomotion.R;
import id.technomotion.model.Person;
import id.technomotion.model.SelectableContact;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final ArrayList<SelectableContact> contacts = new ArrayList<>();
    private final ViewHolder.OnContactClickedListener listener;

    public RecyclerAdapter(ArrayList<Person> persons,ViewHolder.OnContactClickedListener listener) {
        this.listener = listener;
        for (Person person :
                persons) {
            this.contacts.add(new SelectableContact(person, person.isSelected()));
            }
    }

    public RecyclerAdapter(ArrayList<Person> persons,ViewHolder.OnContactClickedListener listener,boolean isSelected) {
        this.listener = listener;
        for (Person person :
                persons) {
            this.contacts.add(new SelectableContact(person,isSelected));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni, parent, false);
        return new ViewHolder(inflatedView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SelectableContact person = contacts.get(position);
        boolean newInitial;
        char initial = person.getName().charAt(0);
        newInitial = positionNewInitial(position,person);
        holder.bindAlumni(person,newInitial,initial);
    }

    private boolean positionNewInitial(int position,SelectableContact currentPerson) {
        boolean result;
        if (position==0) {
            result =true;
        }
        else {
           SelectableContact beforePerson= contacts.get(position-1);
           char currentInitial = currentPerson.getName().toLowerCase().charAt(0);
           char beforeInitial = beforePerson.getName().toLowerCase().charAt(0);
           result = !(currentInitial == beforeInitial);        }
        return result;
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }


}