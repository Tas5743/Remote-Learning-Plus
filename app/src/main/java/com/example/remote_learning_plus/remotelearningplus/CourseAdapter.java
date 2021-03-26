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

public class CourseAdapter extends FirestoreRecyclerAdapter<CourseModel, CourseAdapter.CourseHolder> {
    private OnItemClickListener listener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CourseAdapter(@NonNull FirestoreRecyclerOptions<CourseModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CourseHolder holder, int position, @NonNull CourseModel model) {
        holder.txtCourseID.setText(model.getCourseID());
        holder.txtCourseSection.setText(model.getCourseSection());

    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseHolder(v);
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        TextView txtCourseID;
        TextView txtCourseSection;


        public CourseHolder(@NonNull View itemView) {
            super(itemView);

            txtCourseID = itemView.findViewById(R.id.txtCourse);
            txtCourseSection = itemView.findViewById(R.id.txtSection);

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
