package vku.lvthang.appmp3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vku.lvthang.appmp3.DanhsachbaihatActivity;
import vku.lvthang.appmp3.Model.QuangcaoModel;
import vku.lvthang.appmp3.R;

public class BannerAdapter extends PagerAdapter {

    Context context;
    ArrayList<QuangcaoModel> arrayListBanner;

    public BannerAdapter(Context context, ArrayList<QuangcaoModel> arrayListBanner) {
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public int getCount() {
        return arrayListBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_banner,null);

        ImageView imgbannerbackground = view.findViewById(R.id.imgBackgroundBanner);
        ImageView imgsongBanner = view.findViewById(R.id.imgBanner);
        TextView txtBannerTitle = view.findViewById(R.id.txtBannerTitle);
        TextView txtBannerContent = view.findViewById(R.id.txtBannerContent);

        Picasso.with(context).load(arrayListBanner.get(position).getHinhquangcao()).into(imgbannerbackground);
        Picasso.with(context).load(arrayListBanner.get(position).getHinhbaihat()).into(imgsongBanner);
        txtBannerTitle.setText(arrayListBanner.get(position).getTenbaihat());
        txtBannerContent.setText(arrayListBanner.get(position).getNoidung());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                intent.putExtra("banner",arrayListBanner.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object) ;
    }
}
