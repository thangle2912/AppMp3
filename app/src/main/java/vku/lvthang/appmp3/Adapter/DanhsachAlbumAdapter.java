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

public class DanhsachAlbumAdapter extends RecyclerView.Adapter<DanhsachAlbumAdapter.Viewholder>{

    Context context;
    ArrayList<AlbumModel> albumModelArrayList;

    public DanhsachAlbumAdapter(Context context, ArrayList<AlbumModel> albumModelArrayList) {
        this.context = context;
        this.albumModelArrayList = albumModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_allalbum,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        AlbumModel albumModel = albumModelArrayList.get(position);
        Picasso.with(context).load(albumModel.getHinh()).into(holder.imgDanhsachAlbum);
        holder.txtDanhsachAlbum.setText(albumModel.getTen());
        holder.txtSinger.setText(albumModel.getCasi());
    }

    @Override
    public int getItemCount() {
        return albumModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imgDanhsachAlbum;
        TextView txtDanhsachAlbum,txtSinger;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgDanhsachAlbum = itemView.findViewById(R.id.imgDanhsachAlbum);
            txtDanhsachAlbum= itemView.findViewById(R.id.txtDanhsachAlbum);
            txtSinger = itemView.findViewById(R.id.txtDanhsachSingerAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("idalbum",albumModelArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
