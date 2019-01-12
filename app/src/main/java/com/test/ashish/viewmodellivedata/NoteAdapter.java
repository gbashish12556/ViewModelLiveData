package com.test.ashish.viewmodellivedata;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteHolder> {


    private OnItemClickListener onItemClickListener;

    protected NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note>DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>(){

        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getTitle() == newItem.getTitle() &&
                    oldItem.getDescription() == newItem.getDescription() &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = getItem(position);
        holder.noteTitleTextView.setText(note.getTitle());
        holder.noteDescriptionTextView.setText(note.getDescription());
        holder.notePriorityTextView.setText(note.getPriority());
    }



    public Note getNoteAt(int index){
        return getItem(index);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        private TextView noteTitleTextView;
        private TextView noteDescriptionTextView;
        private TextView notePriorityTextView;

        public NoteHolder(View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.title);
            noteDescriptionTextView = itemView.findViewById(R.id.description);
            notePriorityTextView = itemView.findViewById(R.id.priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
         void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
