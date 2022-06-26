package vku.lvthang.appmp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Adapter.DanhsachAlbumAdapter;
import vku.lvthang.appmp3.Model.AlbumModel;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class DanhsachAlbumActivity extends AppCompatActivity {

    RecyclerView RcvDanhsachAlbum;
    Toolbar toolbar;
    DanhsachAlbumAdapter danhsachAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_album);

        anhxa();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<AlbumModel>> callback = dataService.GetDataDanhsachAlbum();
        callback.enqueue(new Callback<List<AlbumModel>>() {
            @Override
            public void onResponse(Call<List<AlbumModel>> call, Response<List<AlbumModel>> response) {
                ArrayList<AlbumModel> albumModelArrayList = (ArrayList<AlbumModel>) response.body();
                danhsachAlbumAdapter = new DanhsachAlbumAdapter(DanhsachAlbumActivity.this,albumModelArrayList);
                RcvDanhsachAlbum.setLayoutManager(new GridLayoutManager(DanhsachAlbumActivity.this,1));
                RcvDanhsachAlbum.setAdapter(danhsachAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<AlbumModel>> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        RcvDanhsachAlbum = findViewById(R.id.RcvDanhsachalbum);
        toolbar = findViewById(R.id.Toolbardanhsachalbum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Albums");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}