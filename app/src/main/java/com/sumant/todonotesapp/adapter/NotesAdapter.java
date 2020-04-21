package com.sumant.todonotesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumant.todonotesapp.R;
import com.sumant.todonotesapp.clickListener.ItemClickListener;
import com.sumant.todonotesapp.model.Notes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Notes> notesList;
    private ItemClickListener itemClickListener;

    public NotesAdapter(List<Notes> notesList, ItemClickListener itemClickListener) {
        this.notesList = notesList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.notes_layout , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Notes notes = notesList.get(position);

        holder.titleTV.setText( notes.getTitle() );
        holder.descriptionTV.setText( notes.getDescription() );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(notes) ;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV , descriptionTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTextView);
            descriptionTV = itemView.findViewById(R.id.descriptionTextView);
        }

    }
}
