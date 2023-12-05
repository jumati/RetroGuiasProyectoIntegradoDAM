package com.jm.retroguias.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.jm.retroguias.R;
import com.jm.retroguias.model.Guides;
import com.jm.retroguias.model.Users;

public class UsersAdapter extends FirestoreRecyclerAdapter<Users, UsersAdapter.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UsersAdapter(@NonNull FirestoreRecyclerOptions<Users> options) {
        super(options);
    }

    private UsersAdapter.OnClickListener onClickListener;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Users user) {
        holder.name_textView.setText(user.getName());
        holder.last_name_textView.setText(user.getLast_name());
        holder.platform_textView.setText(user.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, user);
                }
            }
        });
    }

    public void setOnClickListener(UsersAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Users model);
    }

    /**
     * Conecta el Adaptador con item_user
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_textView, last_name_textView, platform_textView;
        ImageView del_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_textView = itemView.findViewById(R.id.item_user_name);
            last_name_textView = itemView.findViewById(R.id.item_user_last_name);
            platform_textView = itemView.findViewById(R.id.item_user_platform);






        }

    }
}