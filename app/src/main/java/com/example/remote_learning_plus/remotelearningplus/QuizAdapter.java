package com.example.remote_learning_plus.remotelearningplus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class QuizAdapter extends FirestoreRecyclerAdapter<Quiz, QuizAdapter.QuizHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public QuizAdapter(@NonNull FirestoreRecyclerOptions<Quiz> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuizAdapter.QuizHolder holder, int position, @NonNull Quiz model) {
        holder.quizTitle.setText(model.getTitle());

    }

    @NonNull
    @Override
    public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_quizzes_item, parent, false);

        return new QuizHolder(v);
    }


    class QuizHolder extends RecyclerView.ViewHolder {
        TextView quizTitle;

        public QuizHolder(@NonNull View itemView){
            super(itemView);
            quizTitle = itemView.findViewById(R.id.quizTitle);
        }


    }
}