package vku.lvthang.appmp3.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import vku.lvthang.appmp3.Adapter.SongAdapter;
import vku.lvthang.appmp3.Model.SongModel;
import vku.lvthang.appmp3.PlayMusicActivity;
import vku.lvthang.appmp3.R;


public class AllSongsFragment extends Fragment implements SongAdapter.OnItemClickListener{

    public static final String EXTRA_SONG = "Song";
    public static final String EXTRA_IMG = "Img";
    public static final String EXTRA_URL = "Url";
    public static final String EXTRA_SING = "Sing";
    public static final String EXTRA_ID = "Id";

    private static String JSON_URL = "https://run.mocky.io/v3/2c4a0e1f-1e20-4f54-b3fc-42d017e0a419";
    RecyclerView recyclerView;
    List<SongModel> listSong;
    TextView txtSongTotal,txtNowifi;
    ImageView imgNowifi;
    View v;
    ProgressDialog progressDialog;

    int jsonArrayLeng;
    private SongAdapter songAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.progress_dialog);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_allsong, container, false);
        recyclerView =(RecyclerView) v.findViewById(R.id.recylcerView);
        txtSongTotal =(TextView) v.findViewById(R.id.txtSongTotal);
        listSong = new ArrayList<>();

        return v;

    }

    @Override
    public void onItemClick(int position) {


        Intent detailIntent =new Intent(getActivity(), PlayMusicActivity.class);
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
                jsonArrayLeng= jsonArray.length();
                for (int i=0; i< jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    String id = jsonObject1.getString("id");
                    String Song = jsonObject1.getString("song");
                    String Img = jsonObject1.getString("img");
                    String Url = jsonObject1.getString("url");
                    String Sing = jsonObject1.getString("sing");
                    String Playlist = jsonObject1.getString("playlist");

                    listSong.add(new SongModel(Song, Img, Url,Sing,id,Playlist));

                }

            } catch (JSONException e) {
                Toast.makeText(getActivity(),"Không có kết nối internet",Toast.LENGTH_SHORT).show();

            }
            txtSongTotal.setText("Tất cả (" + jsonArrayLeng +")");
            PutDataIntoRecylcerView( listSong);
            songAdapter.setOnItemClickListener(AllSongsFragment.this);
//            progressDialog.dismiss();
        }
    }
    private void PutDataIntoRecylcerView(List<SongModel> listSong){
         songAdapter =new SongAdapter(listSong, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(songAdapter);
    }
    public AllSongsFragment() {
        // Required empty public constructor
    }

}
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.Menu;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.chootdev.blurimg.BlurImage;
//import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.net.ssl.HttpsURLConnection;
//
//
//public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
//
//    public static final String EXTRA_SONG = "Song";
//    public static final String EXTRA_IMG = "Img";
//    public static final String EXTRA_URL = "Url";
//    public static final String EXTRA_SING = "Sing";
//    public static final String EXTRA_ID = "Id";
//
//
//    RecyclerView recyclerView;
//    List<SongModel> listSong;
//    TextView txtSongTotal;
//
//
//    int jsonArrayLeng;
//    private MyAdapter myAdapter;
//    private static String JSON_URL = "https://run.mocky.io/v3/ab26057a-203a-4b58-a131-e8952f5b8819";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        recyclerView = findViewById(R.id.recylcerView);
//        listSong = new ArrayList<>();
//        txtSongTotal= findViewById(R.id.txtSongTotal);
//
////        Toolbar toolbar = findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
//
//        GetData getData = new GetData();
//        getData.execute();
//
//    }
//
//    @Override
//    public void onItemClick(int position) {
//        Intent detailIntent =new Intent(MainActivity.this,PlayMusicActivity.class);
//        SongModel clickItem = listSong.get(position);
//
//        detailIntent.putExtra(EXTRA_SONG, clickItem.getName());
//        detailIntent.putExtra(EXTRA_ID, clickItem.getId());
//        detailIntent.putExtra(EXTRA_IMG, clickItem.getImg());
//        detailIntent.putExtra(EXTRA_URL, clickItem.getUrl());
//        detailIntent.putExtra(EXTRA_SING, clickItem.getSinger());
//
//
//        startActivity(detailIntent);
//    }
//
//    public class GetData extends AsyncTask<String, Void ,String> {
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            String current = "";
//            try {
//                URL url;
//                HttpsURLConnection urlConnection = null;
//                try {
//                    url= new URL(JSON_URL);
//                    urlConnection=(HttpsURLConnection) url.openConnection();
//
//                    InputStream inputStream = urlConnection.getInputStream();
//                    InputStreamReader inputStreamReader =new InputStreamReader(inputStream);
//
//                    int data = inputStreamReader.read();
//                    while (data!=-1){
//                        current+=(char) data;
//                        data=inputStreamReader.read();
//                    }
//                    return current;
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (urlConnection!=null){
//                        urlConnection.disconnect();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return current;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                JSONObject jsonObject =new JSONObject(s);
//                JSONArray jsonArray = jsonObject.getJSONArray("music");
//                jsonArrayLeng= jsonArray.length();
//                for (int i=0; i< jsonArray.length();i++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
////                    SongModel model = new SongModel();
////                    model.setName(jsonObject1.getString("song"));
////                    model.setSinger(jsonObject1.getString("sing"));
////                    model.setImg(jsonObject1.getString("img"));
////                         listSong.add(model);
//                    String id = jsonObject1.getString("id");
//                    String Song = jsonObject1.getString("song");
//                    String Img = jsonObject1.getString("img");
//                    String Url = jsonObject1.getString("url");
//                    String Sing = jsonObject1.getString("sing");
//
//                    listSong.add(new SongModel(Song, Img, Url,Sing,id));
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//
//            }
//            txtSongTotal.setText("Tất cả (" + jsonArrayLeng +")");
//            PutDataIntoRecylcerView( listSong);
//            myAdapter.setOnItemClickListener(MainActivity.this);
//
//        }
//    }
//    private void PutDataIntoRecylcerView(List<SongModel> listSong){
//         myAdapter =new MyAdapter(listSong, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(myAdapter);
//    }
//}