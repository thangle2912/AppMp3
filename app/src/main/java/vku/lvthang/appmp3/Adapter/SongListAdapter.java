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

import vku.lvthang.appmp3.DanhsachbaiActivity;
import vku.lvthang.appmp3.Model.SongModel;
import vku.lvthang.appmp3.R;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {

    private List<SongModel> songList;
    private DanhsachbaiActivity mContext;

    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public SongListAdapter(List<SongModel> songList, DanhsachbaiActivity mContext) {
        this.songList = songList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_songlist,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtsongName.setText(songList.get(position).getName());
        holder.txtsinger.setText(songList.get(position).getSinger());


        //Using glide library
        Glide.with(mContext)
                .load(songList.get(position).getImg())
                .into(holder.imgUrl);

    }
    @Override
    public int getItemCount() {

        return songList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtsongName, txtsinger;
        ImageView imgUrl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUrl = itemView.findViewById(R.id.tvimgUrl);
            txtsinger = itemView.findViewById(R.id.txtsinger);
            txtsongName = itemView.findViewById(R.id.txtnameSong);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
