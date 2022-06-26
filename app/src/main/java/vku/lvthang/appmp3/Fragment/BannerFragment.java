package vku.lvthang.appmp3.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Adapter.BannerAdapter;
import vku.lvthang.appmp3.Model.QuangcaoModel;
import vku.lvthang.appmp3.R;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;


public class BannerFragment extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;

    Handler handler;
    Runnable runnable;
    int currentItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        anhxa();
        GetData();
        return view;
    }

    private void anhxa() {
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator=view.findViewById(R.id.indicator);
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<QuangcaoModel>> callback = dataService.GetDataBanner();
        callback.enqueue(new Callback<List<QuangcaoModel>>() {
            @Override
            public void onResponse(Call<List<QuangcaoModel>> call, Response<List<QuangcaoModel>> response) {
                ArrayList<QuangcaoModel> banner= (ArrayList<QuangcaoModel>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(),banner);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);

                handler= new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if(currentItem>=viewPager.getAdapter().getCount()){
                            currentItem = 0;
                        }
                        viewPager.setCurrentItem(currentItem,true);
                        handler.postDelayed(runnable,5000);
                    }
                };
                handler.postDelayed(runnable,5000);
            }

            @Override
            public void onFailure(Call<List<QuangcaoModel>> call, Throwable t) {

            }
        });
    }
}