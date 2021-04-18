package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Text;

public class QuestionAdapter extends FirestoreRecyclerAdapter<QuestionModel, QuestionAdapter.QuestionHolder> {
    private OnItemClickListener listener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public QuestionAdapter(@NonNull FirestoreRecyclerOptions<QuestionModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuestionHolder holder, int position, @NonNull QuestionModel model) {
        holder.txtQuestion.setText(model.getQuestion());
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new QuestionHolder(v);
    }

    class QuestionHolder extends RecyclerView.ViewHolder{
        TextView txtQuestion = itemView.findViewById(R.id.txtQuestion);
        public QuestionHolder(@NonNull View itemView) {
            super(itemView);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION && listener != null){
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
