package vku.lvthang.appmp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Adapter.DanhsachbaihatAdapter;
import vku.lvthang.appmp3.Model.AlbumModel;
import vku.lvthang.appmp3.Model.BaihatModel;
import vku.lvthang.appmp3.Model.PlaylistModel;
import vku.lvthang.appmp3.Model.QuangcaoModel;
import vku.lvthang.appmp3.Model.Theloai;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class DanhsachbaihatActivity extends AppCompatActivity {

    QuangcaoModel quangcaoModel;
    PlaylistModel playlistModel;
    Theloai theloai;
    AlbumModel albumModel;

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewDanhsachbaihat;
    FloatingActionButton floatingActionButton;

    ImageView imgDanhsachbaihat;
    ArrayList<BaihatModel> baihatModelArrayList;
    DanhsachbaihatAdapter danhsachbaihatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DataIntent();

        anhxa();
        init();
        if (quangcaoModel != null && !quangcaoModel.getTenbaihat().equals("")){
            setValueInView(quangcaoModel.getTenbaihat(),quangcaoModel.getHinhbaihat());
            GetDataQuangcao(quangcaoModel.getIdquangcao());
        }
        if (playlistModel != null && !playlistModel.getTen().equals("")){
            setValueInView(playlistModel.getTen(),playlistModel.getIcon());
            GetDataPlaylist(playlistModel.getIdPlaylist());
//            Toast.makeText(DanhsachbaihatActivity.this,playlistModel.getIdPlaylist(),Toast.LENGTH_SHORT).show();

        }
        if (theloai != null && !theloai.getTentheloai().equals("")){
            setValueInView(theloai.getTentheloai(),theloai.getHinhtheloai());
            GetDataTheloai(theloai.getIdtheloai());
        }
        if (albumModel!= null && !albumModel.getTen().equals("")){
            setValueInView(albumModel.getTen(),albumModel.getHinh());
            GetDataAlbum(albumModel.getId());
        }

    }




    private void setValueInView(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);
        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinh).into(imgDanhsachbaihat);
    }
    private void GetDataQuangcao(String idquangcao) {

        DataService dataService = APIService.getService();
        Call<List<BaihatModel>> callback = dataService.GetDataDanhsachbaihatQuangcao(idquangcao);
        callback.enqueue(new Callback<List<BaihatModel>>() {
            @Override
            public void onResponse(Call<List<BaihatModel>> call, Response<List<BaihatModel>> response) {
               baihatModelArrayList = (ArrayList<BaihatModel>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachbaihatActivity.this,baihatModelArrayList);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaihatModel>> call, Throwable t) {

            }
        });
    }
    private void GetDataPlaylist(String idplaylist) {
        DataService dataService = APIService.getService();
        Call<List<BaihatModel>> callback = dataService.GetDataDanhsachbaihatPlaylist(idplaylist);
        callback.enqueue(new Callback<List<BaihatModel>>() {
            @Override
            public void onResponse(Call<List<BaihatModel>> call, Response<List<BaihatModel>> response) {
                baihatModelArrayList = (ArrayList<BaihatModel>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachbaihatActivity.this,baihatModelArrayList);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaihatModel>> call, Throwable t) {

            }
        });
    }
    private void GetDataTheloai(String idtheloai) {
        DataService dataService = APIService.getService();
        Call<List<BaihatModel>> callback = dataService.GetDataDanhsachbaihatTheloai(idtheloai);
        callback.enqueue(new Callback<List<BaihatModel>>() {
            @Override
            public void onResponse(Call<List<BaihatModel>> call, Response<List<BaihatModel>> response) {
                baihatModelArrayList = (ArrayList<BaihatModel>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachbaihatActivity.this,baihatModelArrayList);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaihatModel>> call, Throwable t) {

            }
        });
    }
    private void GetDataAlbum(String idalbum) {
        DataService dataService = APIService.getService();
        Call<List<BaihatModel>> callback = dataService.GetDataDanhsachbaihatAlbum(idalbum);
        callback.enqueue(new Callback<List<BaihatModel>>() {
            @Override
            public void onResponse(Call<List<BaihatModel>> call, Response<List<BaihatModel>> response) {
                baihatModelArrayList = (ArrayList<BaihatModel>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachbaihatActivity.this,baihatModelArrayList);
                recyclerViewDanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachbaihatActivity.this));
                recyclerViewDanhsachbaihat.setAdapter(danhsachbaihatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaihatModel>> call, Throwable t) {

            }
        });
    }
    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void anhxa() {
        collapsingToolbarLayout = findViewById(R.id.Collapsingtoolbar);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        toolbar = findViewById(R.id.Toolbardanhsachbaihat);
        recyclerViewDanhsachbaihat = findViewById(R.id.RcvDanhsachbaihat);
        floatingActionButton= findViewById(R.id.Floatingactionbutton);
        imgDanhsachbaihat = findViewById(R.id.imgDanhsachbaiat);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("banner")){
                quangcaoModel= (QuangcaoModel) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemplaylist")){
                playlistModel= (PlaylistModel) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")){
                theloai = (Theloai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("idalbum")){
                albumModel = (AlbumModel) intent.getSerializableExtra("idalbum");
            }
        }
    }
    private  void eventClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhsachbaihatActivity.this,PhatNhacActivity.class);
                intent.putExtra("musics",baihatModelArrayList);
                startActivity(intent);
            }
        });
    }
}