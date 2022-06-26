package vku.lvthang.appmp3.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Adapter.AlbumAdapter;
import vku.lvthang.appmp3.DanhsachAlbumActivity;
import vku.lvthang.appmp3.Model.AlbumModel;
import vku.lvthang.appmp3.R;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;


public class AlbumFragment extends Fragment {

    View view;
    RecyclerView recyclerViewAlbum;
    TextView txtXemthem;
    AlbumAdapter albumAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_album, container, false);
        anhxa();
        GetData();
        txtXemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhsachAlbumActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void anhxa() {
        recyclerViewAlbum = view.findViewById(R.id.RcvAlbum);
        txtXemthem = view.findViewById(R.id.txtXemthemAlbum);
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<AlbumModel>> callback = dataService.GetDataAlbum();
        callback.enqueue(new Callback<List<AlbumModel>>() {
            @Override
            public void onResponse(Call<List<AlbumModel>> call, Response<List<AlbumModel>> response) {
                ArrayList<AlbumModel> albumArrayList = (ArrayList<AlbumModel>) response.body();
                albumAdapter = new AlbumAdapter(getActivity(),albumArrayList);

                LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewAlbum.setLayoutManager(linearLayoutManager);
                recyclerViewAlbum.setAdapter(albumAdapter);
            }


            @Override
            public void onFailure(Call<List<AlbumModel>> call, Throwable t) {

            }
        });
    }
}