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
import vku.lvthang.appmp3.Adapter.DanhsachPlaylistAdapter;
import vku.lvthang.appmp3.Model.PlaylistModel;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class DanhsachPlaylistActivity extends AppCompatActivity {
    RecyclerView RcvDanhsachplaylist;
    Toolbar toolbar;
    DanhsachPlaylistAdapter danhsachPlaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_playlist);

        anhxa();
        init();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<PlaylistModel>> callback = dataService.GetDataDanhsachPlaylist();
        callback.enqueue(new Callback<List<PlaylistModel>>() {
            @Override
            public void onResponse(Call<List<PlaylistModel>> call, Response<List<PlaylistModel>> response) {
                ArrayList<PlaylistModel> playlistModelArrayList = (ArrayList<PlaylistModel>) response.body();
                danhsachPlaylistAdapter = new DanhsachPlaylistAdapter(DanhsachPlaylistActivity.this,playlistModelArrayList);
                RcvDanhsachplaylist.setLayoutManager(new GridLayoutManager(DanhsachPlaylistActivity.this,2));
                RcvDanhsachplaylist.setAdapter(danhsachPlaylistAdapter );
            }

            @Override
            public void onFailure(Call<List<PlaylistModel>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("PlayLists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.Toolbardanhsachplaylist);
        RcvDanhsachplaylist = findViewById(R.id.RcvDanhsachplaylist);
    }

}