package vku.lvthang.appmp3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.ArrayList;

import vku.lvthang.appmp3.Model.BaihatModel;
import vku.lvthang.appmp3.R;

public class DanhsachPhatnhacAdapter extends RecyclerView.Adapter<DanhsachPhatnhacAdapter.ViewHolder>{

    Context context;
    ArrayList<BaihatModel> baihatModelArrayList;

    public DanhsachPhatnhacAdapter(Context context, ArrayList<BaihatModel> baihatModelArrayList) {
        this.context = context;
        this.baihatModelArrayList = baihatModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_danhsachphatnhac, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaihatModel baihatModel = baihatModelArrayList.get(position);
        holder.txtDanhsachPhatnhac.setText(baihatModel.getTen());
        holder.txtSingerDanhsachPhatnhac.setText(baihatModel.getCasi());
        Picasso.with(context).load(baihatModel.getHinh()).into(holder.imgDanhsachPhatnhac);
    }

    @Override
    public int getItemCount() {
        return baihatModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgDanhsachPhatnhac;
        TextView txtDanhsachPhatnhac,txtSingerDanhsachPhatnhac;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDanhsachPhatnhac = itemView.findViewById(R.id.txtDanhsachPhatnhac);
            txtSingerDanhsachPhatnhac=itemView.findViewById(R.id.txtSingerDanhsachPhatnhac);
            imgDanhsachPhatnhac=itemView.findViewById(R.id.imgDanhsachPhatnhac);
        }
    }
}
