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
        holder.txtStudent.setText(model.getStudent());

    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new QuestionHolder(v);
    }

    class QuestionHolder extends RecyclerView.ViewHolder{
        TextView txtQuestion;
        TextView txtStudent;


        public QuestionHolder(@NonNull View itemView) {
            super(itemView);

            txtQuestion = itemView.findViewById(R.id.txtCourse);
            txtStudent = itemView.findViewById(R.id.txtSection);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
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

}
