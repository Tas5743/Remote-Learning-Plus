package com.teamremote.remotelearningplus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.rpc.context.AttributeContext;

public class ResourceAdapter extends FirestoreRecyclerAdapter<ResourceModel, ResourceAdapter.ResourceHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public ResourceAdapter(@NonNull FirestoreRecyclerOptions<ResourceModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ResourceAdapter.ResourceHolder holder, int position, @NonNull ResourceModel model) {
        holder.resourceTitle.setText(model.getTitle());

    }

    @NonNull
    @Override
    public ResourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item, parent, false);

        return new ResourceHolder(v);
    }


    class ResourceHolder extends RecyclerView.ViewHolder {
        TextView resourceTitle;

        public ResourceHolder(@NonNull View itemView){
            super(itemView);
            resourceTitle = itemView.findViewById(R.id.resourceTitle);
        }


    }
}
