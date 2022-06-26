package vku.lvthang.appmp3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vku.lvthang.appmp3.DanhsachbaihatActivity;
import vku.lvthang.appmp3.Model.PlaylistModel;
import vku.lvthang.appmp3.R;

public class DanhsachPlaylistAdapter extends RecyclerView.Adapter<DanhsachPlaylistAdapter.ViewHolder>{

    Context context;
    ArrayList<PlaylistModel> playlistModelArrayList;

    public DanhsachPlaylistAdapter(Context context, ArrayList<PlaylistModel> playlistModelArrayList) {
        this.context = context;
        this.playlistModelArrayList = playlistModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_allplaylist, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaylistModel playlistModel = playlistModelArrayList.get(position);
        Picasso.with(context).load(playlistModel.getIcon()).into(holder.imgDanhsachplaylist);
        holder.txtTenPlaylist.setText(playlistModel.getTen());
    }

    @Override
    public int getItemCount() {
        return playlistModelArrayList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDanhsachplaylist;
        TextView txtTenPlaylist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDanhsachplaylist = itemView.findViewById(R.id.imgDanhsachPlaylist);
            txtTenPlaylist = itemView.findViewById(R.id.txtDanhsachPlaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("itemplaylist",playlistModelArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
