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
import vku.lvthang.appmp3.Model.Theloai;
import vku.lvthang.appmp3.R;

public class DanhsachTheloaiAdapter extends RecyclerView.Adapter<DanhsachTheloaiAdapter.ViewHolder>{

    Context context;
    ArrayList<Theloai> theloaiArrayList;

    public DanhsachTheloaiAdapter(Context context, ArrayList<Theloai> theloaiArrayList) {
        this.context = context;
        this.theloaiArrayList = theloaiArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_theloai_chude, parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Theloai theloai = theloaiArrayList.get(position);
        Picasso.with(context).load(theloai.getHinhtheloai()).into(holder.imgDanhsachTheloai);
        holder.txtDanhsachTheloai.setText(theloai.getTentheloai());
    }

    @Override
    public int getItemCount() {
        return theloaiArrayList.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgDanhsachTheloai;
        TextView txtDanhsachTheloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDanhsachTheloai = itemView.findViewById(R.id.imgDanhsachTheloai);
            txtDanhsachTheloai = itemView.findViewById(R.id.txtDanhsachTheloai);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("idtheloai",theloaiArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
