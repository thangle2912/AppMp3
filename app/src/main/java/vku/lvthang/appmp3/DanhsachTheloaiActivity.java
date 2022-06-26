package vku.lvthang.appmp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Adapter.DanhsachTheloaiAdapter;
import vku.lvthang.appmp3.Model.Chude;
import vku.lvthang.appmp3.Model.Theloai;
import vku.lvthang.appmp3.R;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class DanhsachTheloaiActivity extends AppCompatActivity {
    RecyclerView RcvDanhsachTheloai;
    Toolbar toolbar;
    Chude chude;

    DanhsachTheloaiAdapter danhsachTheloaiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_theloai);
        GetIntent();
        anhxa();
        GetData();

    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<Theloai>> callback =dataService.GetDataDanhsachtheloaiChude(chude.getIdchude());
        callback.enqueue(new Callback<List<Theloai>>() {
            @Override
            public void onResponse(Call<List<Theloai>> call, Response<List<Theloai>> response) {
                ArrayList<Theloai> theloaiArrayList = (ArrayList<Theloai>) response.body();
                danhsachTheloaiAdapter = new DanhsachTheloaiAdapter(DanhsachTheloaiActivity.this,theloaiArrayList);
                RcvDanhsachTheloai.setLayoutManager(new GridLayoutManager(DanhsachTheloaiActivity.this,2));
                RcvDanhsachTheloai.setAdapter(danhsachTheloaiAdapter);
            }

            @Override
            public void onFailure(Call<List<Theloai>> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        RcvDanhsachTheloai = findViewById(R.id.RcvDanhsachtheloai);
        toolbar = findViewById(R.id.Toolbardanhsachtheloai);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chude.getTenchude());
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("idchude")){
            chude= (Chude) intent.getSerializableExtra("idchude");

        }
    }
}