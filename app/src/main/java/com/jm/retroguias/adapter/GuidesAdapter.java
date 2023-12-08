package com.jm.retroguias.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.jm.retroguias.GuiaActivity;
import com.jm.retroguias.R;
import com.jm.retroguias.model.Guides;

import java.util.List;

/*
public class GuidesAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Guides> guidesList;

    public GuidesAdapter(Context context, List<Guides> guidesList) {
        this.context = context;
        this.guidesList = guidesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.guia_title.setText(guidesList.get(position).getGuide_name());
        //holder.guia_pdf.setText(guidesList.get(position).getGuide_pdf());
        holder.guia_platform.setText(guidesList.get(position).getPlatform_id());
        //holder.guia_company.setText(guidesList.get(position).getCompany_id());

        holder.guidesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GuiaActivity.class);
                // intent.putExtra("guia_id", guidesList.get(holder.getAdapterPosition()).getGuide_id());
                intent.putExtra("guia_title", guidesList.get(holder.getAdapterPosition()).getGuide_name());
                intent.putExtra("guia_platform", guidesList.get(holder.getAdapterPosition()).getPlatform_id());
                //intent.putExtra("guia_company", guidesList.get(holder.getAdapterPosition()).getPlatform_id());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return guidesList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView guia_pdf, guia_title, guia_platform, guia_company;
    CardView guidesCard;
    public MyViewHolder(@NonNull View itemView)
    {
        super(itemView);
        //guia_pdf = itemView.findViewById(R.id.guia_pdf);
        guia_title = itemView.findViewById(R.id.guia_title_guia);
        guia_platform = itemView.findViewById(R.id.guia_platform);
        //guia_company = itemView.findViewById(R.id.guia_company);
    }
}
*/

// Adapter con Firestore
// Funciona pero la base de datos a utilizar es Firebase Realtime Database, no Firebase Firestore
// También presenta un error debido a que Firestore no es totalmente compatible con java a la hora
// de hacer un Adapter con un Holder ya que se enfoca más en utilizar métodos propios de kotlin.
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

            title_textView = itemView.findViewById(R.id.item_guia_title);
            platform_textView = itemView.findViewById(R.id.guia_platform);
            fav_cb = itemView.findViewById(R.id.item_guide_fav);
        }

    }
}