package vku.lvthang.appmp3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vku.lvthang.appmp3.Fragment.HitsVietFragment;
import vku.lvthang.appmp3.Fragment.ListSingerFragment;
import vku.lvthang.appmp3.Fragment.TamsuFragment;
import vku.lvthang.appmp3.Model.HitsVietModel;
import vku.lvthang.appmp3.Model.SingerModel;
import vku.lvthang.appmp3.Model.TamsuModel;
import vku.lvthang.appmp3.R;

public class HitsVietAdapter extends RecyclerView.Adapter<HitsVietAdapter.ViewHolder> {

    private List<HitsVietModel> hitsvietList;
    private HitsVietFragment context;
    private IClickItemListener iClickItemListener;

    public interface IClickItemListener{
        void onClickItemHits(int hitsvietModel);
    }



    public HitsVietAdapter(List<HitsVietModel> hitsvietList, HitsVietFragment context,IClickItemListener iClickItemListener) {
        this.hitsvietList = hitsvietList;
        this.context = context;
        this.iClickItemListener = iClickItemListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_hitsviet,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.txtHits.setText(hitsvietList.get(position).getTitle());
        Glide.with(context)
                .load(hitsvietList.get(position).getImg())
                .into(holder.imgHits);

    }
    @Override
    public int getItemCount() {

        return hitsvietList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHits;
        ImageView imgHits;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHits = itemView.findViewById(R.id.imgHitsviet);
            txtHits = itemView.findViewById(R.id.txtHitsviet);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iClickItemListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            iClickItemListener.onClickItemHits(position);
                        }
                    }
                }
            });

        }
    }
}