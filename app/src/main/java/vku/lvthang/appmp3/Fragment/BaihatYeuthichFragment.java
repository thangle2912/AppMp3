package vku.lvthang.appmp3.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Adapter.AlbumAdapter;
import vku.lvthang.appmp3.Adapter.BaihatYeuthichAdapter;
import vku.lvthang.appmp3.Model.AlbumModel;
import vku.lvthang.appmp3.Model.BaihatModel;
import vku.lvthang.appmp3.R;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;


public class BaihatYeuthichFragment extends Fragment {

    View view;
    RecyclerView recyclerViewAlbumBaihatyeuthich;
    TextView txtXemthem;
    BaihatYeuthichAdapter baihatYeuthichAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_baihat_yeuthich, container, false);
        anhxa();
        GetData();
        return view;
    }

    private void anhxa() {
        recyclerViewAlbumBaihatyeuthich = view.findViewById(R.id.RcvBaihatyeuthich);
//        txtXemthem = view.findViewById(R.id.txtXemthembaihatyeuthich);
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<BaihatModel>> callback = dataService.GetDataBaiHatYeuThich();
        callback.enqueue(new Callback<List<BaihatModel>>() {
            @Override
            public void onResponse(Call<List<BaihatModel>> call, Response<List<BaihatModel>> response) {
                ArrayList<BaihatModel> baihatModelArrayList = (ArrayList<BaihatModel>) response.body();
                baihatYeuthichAdapter = new BaihatYeuthichAdapter(getActivity(),baihatModelArrayList);

                LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewAlbumBaihatyeuthich.setLayoutManager(linearLayoutManager);
                recyclerViewAlbumBaihatyeuthich.setAdapter(baihatYeuthichAdapter);
            }

            @Override
            public void onFailure(Call<List<BaihatModel>> call, Throwable t) {

            }
        });
    }
}