package vku.lvthang.appmp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import vku.lvthang.appmp3.Adapter.SongListAdapter;
import vku.lvthang.appmp3.Model.SongModel;

import static vku.lvthang.appmp3.Fragment.HitsVietFragment.EXTRA_IMG_Hit;
import static vku.lvthang.appmp3.Fragment.HitsVietFragment.EXTRA_TITLE_Hit;

import static vku.lvthang.appmp3.Fragment.ListSingerFragment.EXTRA_IMGsinger;
import static vku.lvthang.appmp3.Fragment.ListSingerFragment.EXTRA_SINGER;


import static vku.lvthang.appmp3.Fragment.MoodFragment.EXTRA_IMG_Mood;
import static vku.lvthang.appmp3.Fragment.MoodFragment.EXTRA_TITLE_Mood;
import static vku.lvthang.appmp3.Fragment.TamsuFragment.EXTRA_IMG_Tamsu;
import static vku.lvthang.appmp3.Fragment.TamsuFragment.EXTRA_TITLE_Tamsu;


public class DanhsachbaiActivity extends AppCompatActivity implements SongListAdapter.OnItemClickListener{


    public static final String EXTRA_SONG = "Song";
    public static final String EXTRA_IMG = "Img";
    public static final String EXTRA_URL = "Url";
    public static final String EXTRA_SING = "Sing";
    public static final String EXTRA_ID = "Id";

    ImageView imgDanhsach, imgback;
    TextView txtDanhsach,txtDanhsach1;
    Intent intent;


    private static String JSON_URL = "https://run.mocky.io/v3/2c4a0e1f-1e20-4f54-b3fc-42d017e0a419";
    RecyclerView recyclerView;
    List<SongModel> listSong;
    private SongListAdapter songListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbai);
        txtDanhsach= findViewById(R.id.txtDanhsach);
        txtDanhsach1= findViewById(R.id.txtDanhsach1);
        imgDanhsach =findViewById(R.id.imgDanhsach);
        imgback = findViewById(R.id.imgback);
        recyclerView =findViewById(R.id.recyclerviewPlaylist);

        listSong = new ArrayList<>();

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DanhsachbaiActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        intent =getIntent();
        String Img_Hit = intent.getStringExtra(EXTRA_IMG_Hit);
        String Title_hit = intent.getStringExtra(EXTRA_TITLE_Hit);

        String Img_Mood = intent.getStringExtra(EXTRA_IMG_Mood);
        String Title_Mood = intent.getStringExtra(EXTRA_TITLE_Mood);

        String Img_Tamsu = intent.getStringExtra(EXTRA_IMG_Tamsu);
        String Title_Tamsu = intent.getStringExtra(EXTRA_TITLE_Tamsu);

        String Img = intent.getStringExtra(EXTRA_IMGsinger);
        String Singer = intent.getStringExtra(EXTRA_SINGER);

        if (Img != null && Singer != null){
            txtDanhsach.setText(Singer);
            txtDanhsach1.setText(Singer);
            Glide.with(this)
                    .load(Img)
                    .thumbnail()
                    .into(imgDanhsach);
        }
        if (Img_Hit != null || Title_hit != null){
                txtDanhsach.setText(Title_hit);
                txtDanhsach1.setText(Title_hit);

                Glide.with(this)
                        .load(Img_Hit)
                        .thumbnail()
                        .into(imgDanhsach);
            }

                if (Img_Mood != null || Title_Mood != null){
                    txtDanhsach.setText(Title_Mood);
                    txtDanhsach1.setText(Title_Mood);

                    Glide.with(this)
                            .load(Img_Mood)
                            .thumbnail()
                            .into(imgDanhsach);
                }


                if (Img_Tamsu != null || Title_Tamsu != null){
                    txtDanhsach.setText(Title_hit);
                    txtDanhsach1.setText(Title_hit);

                    Glide.with(this)
                            .load(Img_Tamsu)
                            .thumbnail()
                            .into(imgDanhsach);
                }






      GetData getData = new GetData();
        getData.execute();

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent =new Intent(this, PlayMusicActivity.class);
        SongModel clickItem = listSong.get(position);

        detailIntent.putExtra(EXTRA_SONG, clickItem.getName());
        detailIntent.putExtra(EXTRA_ID, clickItem.getId());
        detailIntent.putExtra(EXTRA_IMG, clickItem.getImg());
        detailIntent.putExtra(EXTRA_URL, clickItem.getUrl());
        detailIntent.putExtra(EXTRA_SING, clickItem.getSinger());


        startActivity(detailIntent);
    }

    public class GetData extends AsyncTask<String, Void ,String> {


        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpsURLConnection urlConnection = null;
                try {
                    url= new URL(JSON_URL);
                    urlConnection=(HttpsURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader =new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while (data!=-1){
                        current+=(char) data;
                        data=inputStreamReader.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject =new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("music");
                for (int i=0; i< jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

//                    SongModel model = new SongModel();
//                    model.setName(jsonObject1.getString("song"));
//                    model.setSinger(jsonObject1.getString("sing"));
//                    model.setImg(jsonObject1.getString("img"));
//                         listSong.add(model);
                    String id = jsonObject1.getString("id");
                    String Song = jsonObject1.getString("song");
                    String Img = jsonObject1.getString("img");
                    String Url = jsonObject1.getString("url");
                    String Sing = jsonObject1.getString("sing");
                    String Playlist = jsonObject1.getString("playlist");


                    if (intent.getStringExtra(EXTRA_SINGER) != null){
                        if (Sing.contains(intent.getStringExtra(EXTRA_SINGER))){
                            listSong.add(new SongModel(Song, Img, Url,Sing,id,Playlist));
                        }
                    }else {
                        if (intent.getStringExtra(EXTRA_TITLE_Hit) != null){
                            if (Playlist.contains(intent.getStringExtra(EXTRA_TITLE_Hit))){
                                listSong.add(new SongModel(Song, Img, Url,Sing,id,Playlist));
                            }
                        }else {
                            if (intent.getStringExtra(EXTRA_TITLE_Mood) != null){
                                if (Playlist.contains(intent.getStringExtra(EXTRA_TITLE_Mood))){
                                    listSong.add(new SongModel(Song, Img, Url,Sing,id,Playlist));
                                }
                            }else {
                                if (intent.getStringExtra(EXTRA_TITLE_Tamsu) != null){
                                    if (Playlist.contains(intent.getStringExtra(EXTRA_TITLE_Tamsu))){
                                        listSong.add(new SongModel(Song, Img, Url,Sing,id,Playlist));
                                    }
                                }
                            }
                        }
                }


                }

            } catch (JSONException e) {
                Toast.makeText(DanhsachbaiActivity.this,"Không có kết nối internet",Toast.LENGTH_SHORT).show();
            }
            PutDataIntoRecylcerView( listSong);

            songListAdapter.setOnItemClickListener(DanhsachbaiActivity.this);
        }
    }
    private void PutDataIntoRecylcerView(List<SongModel> listSong){
        songListAdapter =new SongListAdapter(listSong, DanhsachbaiActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(DanhsachbaiActivity.this));
        recyclerView.setAdapter(songListAdapter);
    }
}