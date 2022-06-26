package vku.lvthang.appmp3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vku.lvthang.appmp3.DanhsachTheloaiActivity;
import vku.lvthang.appmp3.Model.Chude;
import vku.lvthang.appmp3.R;

public class DanhsachChudeAdapter extends RecyclerView.Adapter<DanhsachChudeAdapter.ViewHolder>{
    Context context;
    ArrayList<Chude> chudeArrayList;

    public DanhsachChudeAdapter(Context context, ArrayList<Chude> chudeArrayList) {
        this.context = context;
        this.chudeArrayList = chudeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_allchude, parent,false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chude chude = chudeArrayList.get(position);
        Picasso.with(context).load(chude.getHinhchude()).into(holder.imgDanhsachChude);
    }

    @Override
    public int getItemCount() {
        return chudeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDanhsachChude;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDanhsachChude =itemView.findViewById(R.id.imgDanhsachChude);
            imgDanhsachChude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachTheloaiActivity.class);
                    intent.putExtra("idchude",chudeArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
