package com.teamremote.remotelearningplus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

public class CourseAdapter extends FirestoreRecyclerAdapter<CourseModel, CourseAdapter.CourseHolder> {


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
        holder.txtLevel.setText(model.getLevel());
        holder.txtSubject.setText(model.getSubject());
        holder.txtSection.setText(model.getSection());
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseHolder(v);
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        TextView txtLevel;
        TextView txtSubject;
        TextView txtSection;


        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            txtLevel = itemView.findViewById(R.id.txtLevel);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtSection = itemView.findViewById(R.id.txtSection);
        }
    }
}
