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
import vku.lvthang.appmp3.Model.AlbumModel;
import vku.lvthang.appmp3.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{

  Context context;
  ArrayList<AlbumModel> arrayAlbum;

    public AlbumAdapter(Context context, ArrayList<AlbumModel> arrayAlbum) {
        this.context = context;
        this.arrayAlbum = arrayAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_album,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlbumModel albumModel = arrayAlbum.get(position);
        holder.txtAlbum.setText(albumModel.getTen());
        holder.txtSinger.setText(albumModel.getCasi());
        Picasso.with(context).load(albumModel.getHinh()).into(holder.imgAlbum);
    }

    @Override
    public int getItemCount() {
        return arrayAlbum.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAlbum;
        TextView txtAlbum, txtSinger;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.imgAlbum);
            txtAlbum =itemView.findViewById(R.id.txtAlbum);
            txtSinger= itemView.findViewById(R.id.txtSingerAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("idalbum",arrayAlbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
