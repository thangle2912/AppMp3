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
import vku.lvthang.appmp3.Adapter.DanhsachChudeAdapter;
import vku.lvthang.appmp3.Model.Chude;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class DanhhsachChudeActivity extends AppCompatActivity {

    RecyclerView RcvDanhsachChude;
    Toolbar toolbar;
    DanhsachChudeAdapter danhsachChudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhhsach_chude);
        anhxa();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<Chude>> callback = dataService.GetDataDanhsachChude();
        callback.enqueue(new Callback<List<Chude>>() {
            @Override
            public void onResponse(Call<List<Chude>> call, Response<List<Chude>> response) {
                ArrayList<Chude> chudeArrayList = (ArrayList<Chude>) response.body();
                danhsachChudeAdapter = new DanhsachChudeAdapter(DanhhsachChudeActivity.this,chudeArrayList);
                RcvDanhsachChude.setLayoutManager(new GridLayoutManager(DanhhsachChudeActivity.this,2));
                RcvDanhsachChude.setAdapter(danhsachChudeAdapter);
            }

            @Override
            public void onFailure(Call<List<Chude>> call, Throwable t) {

            }
        });
    }


    private void anhxa() {
        RcvDanhsachChude = findViewById(R.id.RcvDanhsachchude);
        toolbar =findViewById(R.id.Toolbardanhsachchude);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách chủ đề");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}