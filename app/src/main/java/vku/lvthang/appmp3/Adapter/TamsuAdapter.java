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

import vku.lvthang.appmp3.Fragment.ListSingerFragment;
import vku.lvthang.appmp3.Fragment.TamsuFragment;
import vku.lvthang.appmp3.Model.SingerModel;
import vku.lvthang.appmp3.Model.TamsuModel;
import vku.lvthang.appmp3.R;

public class TamsuAdapter extends RecyclerView.Adapter<TamsuAdapter.ViewHolder> {

    private List<TamsuModel> tamsuList;
    private TamsuFragment context;
    private IClickItemListener iClickItemListener;

    public interface IClickItemListener{
        void onClickItemHits(int tamsuModel);
    }



    public TamsuAdapter(List<TamsuModel> tamsuList, TamsuFragment context,IClickItemListener iClickItemListener) {
        this.tamsuList = tamsuList;
        this.context = context;
        this.iClickItemListener =iClickItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_tamsu,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.txtTamsu.setText(tamsuList.get(position).getTitle());
        Glide.with(context)
                .load(tamsuList.get(position).getImg())
                .into(holder.imgTamsu);

    }
    @Override
    public int getItemCount() {

        return tamsuList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTamsu;
        ImageView imgTamsu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTamsu = itemView.findViewById(R.id.imgTamsu);
            txtTamsu = itemView.findViewById(R.id.txtTamsuModel);

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