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
import vku.lvthang.appmp3.Model.SingerModel;
import vku.lvthang.appmp3.R;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.ViewHolder> {

    private List<SingerModel> singerList;
    private ListSingerFragment context;
    private IClickItemListener iClickItemListener;

    public interface IClickItemListener{
        void onClickItemSinger(int singerModel);
    }



    public SingerAdapter(List<SingerModel> singerList, ListSingerFragment context,IClickItemListener iClickItemListener) {
        this.singerList = singerList;
        this.context = context;
        this.iClickItemListener = iClickItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_sing,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.txtsinger.setText(singerList.get(position).getNameSinger());
        Glide.with(context)
                .load(singerList.get(position).getImg())
                .into(holder.imgSinger);

    }
    @Override
    public int getItemCount() {

        return singerList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtsinger;
        ImageView imgSinger;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSinger = itemView.findViewById(R.id.imgSingerModel);
            txtsinger = itemView.findViewById(R.id.txtSingerModel);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iClickItemListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            iClickItemListener.onClickItemSinger(position);
                        }
                    }
                }
            });

        }
    }
}