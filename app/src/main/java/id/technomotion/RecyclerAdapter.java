package id.technomotion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<AlumniHolder> {
    private ArrayList<Alumni> mPhotos;

    public RecyclerAdapter(ArrayList<Alumni> mPhotos) {
        this.mPhotos = mPhotos;
    }

    @Override
    public AlumniHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni, parent, false);
        return new AlumniHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(AlumniHolder holder, int position) {
        Alumni alumni = mPhotos.get(position);
        holder.bindAlumni(alumni);
    }

    @Override
    public int getItemCount() {
        return this.mPhotos.size();
    }
}