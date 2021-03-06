package vku.lvthang.appmp3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Model.BaihatModel;
import vku.lvthang.appmp3.PhatNhacActivity;
import vku.lvthang.appmp3.R;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class SearchBaihatAdapter extends  RecyclerView.Adapter<SearchBaihatAdapter.ViewHolder>{
    Context context;
    ArrayList<BaihatModel> baihatModelArrayList;

    public SearchBaihatAdapter(Context context, ArrayList<BaihatModel> baihatModelArrayList) {
        this.context = context;
        this.baihatModelArrayList = baihatModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_searchbaihat,parent,false);

        return new ViewHolder(view); }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaihatModel baihatModel = baihatModelArrayList.get(position);
        holder.txtName.setText(baihatModel.getTen());
        holder.txtSinger.setText(baihatModel.getCasi());
        Picasso.with(context).load(baihatModel.getHinh()).into(holder.imgSong);
    }

    @Override
    public int getItemCount() {
        return baihatModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtSinger;
        ImageView imgSong,imgFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtSearchbaihat);
            txtSinger = itemView.findViewById(R.id.txtSingerSearchbaihat);
            imgSong =itemView.findViewById(R.id.imgSearchbaihat);
            imgFavorite = itemView.findViewById(R.id.imgYeuthichSearchbaihat);
            imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgFavorite.setImageResource(R.drawable.ic_favorited);
                    DataService dataService = APIService.getService();
                    Call<String> callback =  dataService.UpdateLuotthich("1",baihatModelArrayList.get(getPosition()).getId());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String kq = response.body();
                            if(kq.equals("Thanh cong")){
                                Toast.makeText(context,"???? th??ch",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,"L???i",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imgFavorite.setEnabled(false);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PhatNhacActivity.class);
                    intent.putExtra("music",baihatModelArrayList.get(getPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }
}
