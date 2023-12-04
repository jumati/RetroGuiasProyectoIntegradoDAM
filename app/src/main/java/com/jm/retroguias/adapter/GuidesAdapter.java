package com.jm.retroguias.adapter;

import android.util.Log;
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

public class GuidesAdapter extends FirestoreRecyclerAdapter<Guides, GuidesAdapter.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GuidesAdapter(@NonNull FirestoreRecyclerOptions<Guides> options) {
        super(options);
    }
    private OnClickListener onClickListener;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Guides guide) {
        holder.title_textView.setText(guide.getGuide_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, guide);
                }
            }
        });

        /*
        // Acceder a un dato para asignarlo a platform_tetView
        ValueEventListener postListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Platforms platform = snapshot.getValue(Platforms.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        };

        mPostReference.addValueEventListener(postListener);
        */


        holder.platform_textView.setText(guide.getPlatform_id());
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Guides model);
    }

    /**
     * Conecta el Adaptador con item_guide
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
                .inflate(R.layout.item_guide, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_textView, platform_textView;
        ImageView fav_cb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_textView = itemView.findViewById(R.id.item_guide_title);
            platform_textView = itemView.findViewById(R.id.item_guide_platform);
            fav_cb = itemView.findViewById(R.id.item_guide_fav);






        }

    }
}
