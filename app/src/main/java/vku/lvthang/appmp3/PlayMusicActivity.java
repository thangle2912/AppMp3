package vku.lvthang.appmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chootdev.blurimg.BlurImage;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

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
import java.util.Random;
import java.util.concurrent.Delayed;

import javax.net.ssl.HttpsURLConnection;

import vku.lvthang.appmp3.Model.SongModel;

import static vku.lvthang.appmp3.MainActivity.EXTRA_IMG;
import static vku.lvthang.appmp3.MainActivity.EXTRA_SING;
import static vku.lvthang.appmp3.MainActivity.EXTRA_SONG;
import static vku.lvthang.appmp3.MainActivity.EXTRA_URL;
import static vku.lvthang.appmp3.MainActivity.EXTRA_ID;

public class PlayMusicActivity extends AppCompatActivity {

    List<SongModel> listSong;
    private static String JSON_URL = "https://run.mocky.io/v3/2c4a0e1f-1e20-4f54-b3fc-42d017e0a419";

    ImageView imgBackground, imgplay, imgCD, imgAlarm, imgFavorite,
            imgBtnNext,imgBtnPreviou, imgBack, imgShare, imgDownload,imgAddlist, imgMore,imgLap,imgRandom;
    TextView txtTimeMusicRun, txtTimeMusicTotal, txtSong, txtSinger,txtTotal;
    MediaPlayer player;
    SeekBar seekBar;
    private boolean Favorite;


    Handler handler = new Handler();
    Intent intent;

    float gocQuay = 0;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    int  IdFake = 0;
    int  IdPreviou = 0;
    int JsonArrayLeng =0;
    int IdCurrent = 0;
    int x =0;

    boolean lap = false;
    boolean random= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_play_music);

        Favorite = true;
        txtSong = findViewById(R.id.txtSong_play);
        txtSinger = findViewById(R.id.txtSing_play);
        txtTimeMusicRun = findViewById(R.id.tvRuntime);
        txtTimeMusicTotal = findViewById(R.id.tvTimetotal);

//        txtLyrics =findViewById(R.id.txtLyrics);
        imgBackground = findViewById(R.id.imgBackground);
        imgplay = findViewById(R.id.imgBtnplaypause);
        imgCD = findViewById(R.id.imgCD);
        imgMore = findViewById(R.id.imgmore);
        imgAddlist = findViewById(R.id.imgAddlist);
        imgAlarm = findViewById(R.id.imgAlarm);
        imgFavorite = findViewById(R.id.imgFavorite);
        imgBtnNext = findViewById(R.id.imgBtnnext);
        imgBtnPreviou= findViewById(R.id.imgBtnpreviou);
        imgBack = findViewById(R.id.imgBack);
        imgShare = findViewById(R.id.imgShare);
        imgDownload = findViewById(R.id.imgDownload);
        imgLap=findViewById(R.id.imgBtnlap);
        imgRandom= findViewById(R.id.imgBtnrandom);

        seekBar = findViewById(R.id.seekBartime);
        player = new MediaPlayer();

        seekBar.setMax(100);
        gocQuay = 0;


        intent = getIntent();
        String Song_play = intent.getStringExtra(EXTRA_SONG);
        String Img = intent.getStringExtra(EXTRA_IMG);
        String Url = intent.getStringExtra(EXTRA_URL);
        String Sing_play = intent.getStringExtra(EXTRA_SING);
        String Id = intent.getStringExtra(EXTRA_ID);

//        txtTotal.setText(JsonArrayLeng);
//        IdPreviou = Integer.parseInt(Id);

        txtSinger.setText(Sing_play);
        txtSong.setText(Song_play);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        prepareMediaPlayer(Url);
        setupDownload(Url,Song_play);
        setupShare(Song_play,Sing_play,Url);
        setUp();


        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        imgAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupAlarm = new PopupMenu(PlayMusicActivity.this, v);
                popupAlarm.getMenuInflater().inflate(R.menu.menu_alarm, popupAlarm.getMenu());
                popupAlarm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.thirtyMin) {
                            Toast.makeText(PlayMusicActivity.this, "Click", Toast.LENGTH_SHORT).show();
                        }
                        if (item.getItemId() == R.id.oneHour) {
                            Toast.makeText(PlayMusicActivity.this, "Click", Toast.LENGTH_SHORT).show();
                        }

                        if (item.getItemId() == R.id.fiveTimer) {
                            Toast.makeText(PlayMusicActivity.this, "Click", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
                popupAlarm.show();
            }
        });
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMore = new PopupMenu(PlayMusicActivity.this, v);
                popupMore.getMenuInflater().inflate(R.menu.menu_more, popupMore.getMenu());
                popupMore.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item1) {
                            Toast.makeText(PlayMusicActivity.this, "Click", Toast.LENGTH_SHORT).show();
                        }
                        if (item.getItemId() == R.id.item2) {
                            Toast.makeText(PlayMusicActivity.this, "Click", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
                popupMore.show();
            }
        });

        imgAddlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayMusicActivity.this,"Đã thêm vào đanh sách",Toast.LENGTH_SHORT).show();
            }
        });

        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    handler.removeCallbacks(updater);
                    player.pause();
                    imgplay.setImageResource(R.drawable.ic_play);

                } else {
                    player.start();
                    imgplay.setImageResource(R.drawable.ic_pause);
                    updateSeekbar();
                    if (x==0){
                        updateImgCD();
                    }
                    x= 1;
                }
            }
        });
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Favorite == true) {
                    imgFavorite.setImageResource(R.drawable.lover);

                    Favorite = false;
                } else {
                    imgFavorite.setImageResource(R.drawable.ic_favorite);
                    Favorite = true;
                }
            }
        });
        imgLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lap == false){
                    lap = true;
                    imgLap.setImageResource(R.drawable.process_arrowsed);
                }else {
                    if (lap == true){
                        lap = false;
                        imgLap.setImageResource(R.drawable.process_arrows);
                    }
                }
            }
        });
        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random == false){
                    random = true;
                    imgRandom.setImageResource(R.drawable.shuffleeddd);
                }else {
                    if (random == true){
                        random = false;
                        imgRandom.setImageResource(R.drawable.shuffleee);
                    }
                }
            }
        });

//        setClick();

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (player.getDuration() / 100) * seekBar.getProgress();
                player.seekTo(playPosition);
                txtTimeMusicRun.setText(milliSecondsToTimer(player.getCurrentPosition()));
                return false;
            }
        });
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress(percent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
                player.stop();
                player.reset();
                player.release();
                Intent intent1 = new Intent(PlayMusicActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        listSong = new ArrayList<>();

        GetData getData = new GetData();
        getData.execute() ;
//        printKeyHash();



    }


    /////////////////////////////////////////////////////////
//    private void printKeyHash() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("vku.lvthang.appmp3"
//                    , PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }
    private void setupDownload(String a,String b){
        imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URL = a;
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
                String title = URLUtil.guessFileName(URL, null, null);
                request.setTitle(title);
                request.setDescription("Đang tải "+b);
                String cookie = CookieManager.getInstance().getCookie(URL);
                request.addRequestHeader("cookie", cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

                Toast.makeText(PlayMusicActivity.this, "Bắt đầu tải ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupShare(String a,String b,String c){
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote(a +" - "+b)
                        .setContentUrl(Uri.parse(c))
                        .build();

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(linkContent);
                }
            }
        });
    }
    private void updateImgCD() {
        new CountDownTimer(300000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (player.isPlaying()) {
                    gocQuay = gocQuay + 0.3f;
                    if (gocQuay == 360) {
                        gocQuay = 0;
                    }
                    imgCD.setRotation(gocQuay);
                } else {
                    imgCD.setRotation(gocQuay);
                }
            }
            @Override
            public void onFinish() {
                start();
            }
        }.start();
    }

//    private  Runnable delayedStart = new Runnable() {
//        @Override
//        public void run() {
//            player.start();
//            imgplay.setImageResource(R.drawable.ic_pause);
//            updateSeekbar();
//        }
//    };
    private void prepareMediaPlayer(String s) {
        try {
            player.setDataSource(s);
            player.prepare();
//            player.start();
            txtTimeMusicTotal.setText(milliSecondsToTimer(player.getDuration()));

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    seekBar.setProgress(0);
                    imgplay.setImageResource(R.drawable.ic_play);
                    txtTimeMusicRun.setText(R.string.zero);
//                txtTimeMusicTotal.setText(R.string.zero);
                    player.reset();
                    prepareMediaPlayer(s);
                    if (random == true){
                        GetData getData = new GetData();
                        getData.execute();

                    }else {
                        if (lap == true){
                            player.start();
                            imgplay.setImageResource(R.drawable.ic_pause);
                            updateSeekbar();
                        }
                    }


                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
            long currentDuration = player.getCurrentPosition();
            txtTimeMusicRun.setText(milliSecondsToTimer(currentDuration));
        }
    };
    private void updateSeekbar() {
        if (player.isPlaying()) {
            seekBar.setProgress((int) (((float) player.getCurrentPosition() / player.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private String milliSecondsToTimer(long milliSeconds) {
        String timerString = "";
        String minutesString = null;
        String secondsString;

        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) (((milliSeconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        if (hours > 0) {
            timerString = hours + ":";
        }
        if(minutes<10){
            minutesString ="0"+ minutes;
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        timerString = timerString + minutesString + ":" + secondsString;
        return timerString;
    }

    private void setUp() {

        Glide.with(this)
                .load(intent.getStringExtra(EXTRA_IMG))
                .thumbnail()
                .into(imgCD);
        setImgBackground(R.drawable.aswefall);

    }

    private void setImgBackground(int anh) {
        BlurImage.withContext(this)
                .setBlurRadius(15.5f)
                .setBitmapScale(0.1f)
                .blurFromResource(anh)
                .into(imgBackground);
    }

    public class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpsURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpsURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while (data != -1) {
                        current += (char) data;
                        data = inputStreamReader.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
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
                IdPreviou= jsonArray.length() +1;
            } catch (JSONException e) {
                e.printStackTrace();

            }
            if (random == true){
                addDataRandom(s);
            }
            imgBtnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDataNext(s);
                }
            });
            imgBtnPreviou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDataPreviou(s);
                }
            });
        }
    }
    private void addDataNext(String s) {
        int a;
        int b;

        String Id = intent.getStringExtra(EXTRA_ID);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("music");
            JsonArrayLeng = jsonArray.length();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                String id1 = jsonObject1.getString("id");
                String Song1 = jsonObject1.getString("song");
                String Img1 = jsonObject1.getString("img");
                String Url1 = jsonObject1.getString("url");
                String Sing1 = jsonObject1.getString("sing");
                a = Integer.parseInt(id1);
                b = Integer.parseInt(Id);

//                b == JsonArrayLeng ||
                if ( IdCurrent == JsonArrayLeng){
                    imgBtnNext.setImageResource(R.drawable.ic_next_end);
                    Toast.makeText(this,"Đã hết danh sách !",Toast.LENGTH_SHORT).show();
                    b=Integer.parseInt(Id)-1;
                    return;
                }else {
                    if( IdCurrent == 0){
                        if (a >b) {
//                            Toast.makeText(PlayMusicActivity.this, id1, Toast.LENGTH_SHORT).show();
                            IdCurrent= a;
                            txtSinger.setText(Sing1);
                            txtSong.setText(Song1);
                            Glide.with(this)
                                    .load(Img1)
                                    .thumbnail()
                                    .into(imgCD);
                            player.pause();
                            player.stop();
                            player.reset();
                            player.release();
                            player = new MediaPlayer();
                            seekBar.setProgress(0);
                            imgplay.setImageResource(R.drawable.ic_play);
                            txtTimeMusicRun.setText(R.string.zero);
                            prepareMediaPlayer(Url1);
                            setupDownload(Url1,Song1);
                            setupShare(Song1,Sing1,Url1);
//                            Toast.makeText(PlayMusicActivity.this, Url1, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else{
                       if (a>IdCurrent){
                           if (Integer.parseInt(Id) == 1){
                               imgBtnPreviou.setImageResource(R.drawable.ic_previou);
                           }
//                           Toast.makeText(PlayMusicActivity.this, id1, Toast.LENGTH_SHORT).show();
                           IdCurrent= a;
                           txtSinger.setText(Sing1);
                           txtSong.setText(Song1);
                           Glide.with(this)
                                   .load(Img1)
                                   .thumbnail()
                                   .into(imgCD);
                           player.pause();
                           player.stop();
                           player.reset();
                           player.release();
                           player = new MediaPlayer();
                           seekBar.setProgress(0);
                           imgplay.setImageResource(R.drawable.ic_play);
                           txtTimeMusicRun.setText(R.string.zero);
                           prepareMediaPlayer(Url1);
                           setupDownload(Url1,Song1);
                           setupShare(Song1,Sing1,Url1);
//                            Toast.makeText(PlayMusicActivity.this, Url1, Toast.LENGTH_SHORT).show();
                           return;
                       }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void addDataPreviou(String s) {
        int a;
        int b;
        String Id = intent.getStringExtra(EXTRA_ID);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("music");
            JsonArrayLeng = jsonArray.length() -1;
            for (int i = JsonArrayLeng; i>=0; i--) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String id1 = jsonObject1.getString("id");
                String Song1 = jsonObject1.getString("song");
                String Img1 = jsonObject1.getString("img");
                String Url1 = jsonObject1.getString("url");
                String Sing1 = jsonObject1.getString("sing");
//                int y = IdFuture.compareTo(Id);
//                Toast.makeText(this,id1,Toast.LENGTH_SHORT).show();
                a = Integer.parseInt(id1);
                b = Integer.parseInt(Id);
//                b == 1||
                if (  IdCurrent == 1){
                    imgBtnPreviou.setImageResource(R.drawable.ic_previous);
                    return;
                }
                else {
                    if (IdCurrent == 0){
                        if (a < b){
//                            Toast.makeText(PlayMusicActivity.this, id1, Toast.LENGTH_SHORT).show();
                            IdCurrent= a;
                            txtSinger.setText(Sing1);
                            txtSong.setText(Song1);
                            Glide.with(this)
                                    .load(Img1)
                                    .thumbnail()
                                    .into(imgCD);
                            player.pause();
                            player.stop();
                            player.reset();
                            player.release();
                            player = new MediaPlayer();
                            seekBar.setProgress(0);
                            imgplay.setImageResource(R.drawable.ic_play);
                            txtTimeMusicRun.setText(R.string.zero);
                            prepareMediaPlayer(Url1);
                            setupDownload(Url1,Song1);
                            setupShare(Song1,Sing1,Url1);
                            return;
                        }
                    }else {
                        if (a<IdCurrent){
                            if (Integer.parseInt(Id) == JsonArrayLeng+1){
                                imgBtnNext.setImageResource(R.drawable.ic_next);
                            }
//                            Toast.makeText(PlayMusicActivity.this, id1, Toast.LENGTH_SHORT).show();
                            IdCurrent= a;
                            txtSinger.setText(Sing1);
                            txtSong.setText(Song1);
                            Glide.with(this)
                                    .load(Img1)
                                    .thumbnail()
                                    .into(imgCD);
                            player.pause();
                            player.stop();
                            player.reset();
                            player.release();
                            player = new MediaPlayer();
                            seekBar.setProgress(0);
                            imgplay.setImageResource(R.drawable.ic_play);
                            txtTimeMusicRun.setText(R.string.zero);
                            prepareMediaPlayer(Url1);
                            setupDownload(Url1,Song1);
                            setupShare(Song1,Sing1,Url1);

                            return;
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void addDataRandom(String s) {

        Random random1 = new Random();
        int a;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("music");
            JsonArrayLeng = jsonArray.length();
            int number = random1.nextInt(JsonArrayLeng = jsonArray.length())+1;
            IdCurrent = number;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                String id1 = jsonObject1.getString("id");
                String Song1 = jsonObject1.getString("song");
                String Img1 = jsonObject1.getString("img");
                String Url1 = jsonObject1.getString("url");
                String Sing1 = jsonObject1.getString("sing");
                a = Integer.parseInt(id1);
                if ( IdCurrent == JsonArrayLeng){
                    if (a == IdCurrent){

                        txtSinger.setText(Sing1);
                        txtSong.setText(Song1);
                        Glide.with(this)
                                .load(Img1)
                                .thumbnail()
                                .into(imgCD);
                        player.pause();
                        player.stop();
                        player.reset();
                        player.release();
                        player = new MediaPlayer();

                        prepareMediaPlayer(Url1);
                        setupDownload(Url1,Song1);
                        setupShare(Song1,Sing1,Url1);

                        imgBtnNext.setImageResource(R.drawable.ic_next_end);
                        Toast.makeText(this,"Đã hết danh sách !",Toast.LENGTH_SHORT).show();
                        player.start();
                        imgplay.setImageResource(R.drawable.ic_pause);
                        updateSeekbar();
                        return;
                    }

                }else {
                    if( IdCurrent == a){
                            Toast.makeText(PlayMusicActivity.this, id1, Toast.LENGTH_SHORT).show();
                            IdCurrent= a;
                            txtSinger.setText(Sing1);
                            txtSong.setText(Song1);
                            Glide.with(this)
                                    .load(Img1)
                                    .thumbnail()
                                    .into(imgCD);
                            player.pause();
                            player.stop();
                            player.reset();
                            player.release();
                            player = new MediaPlayer();

                            prepareMediaPlayer(Url1);
                            setupDownload(Url1,Song1);
                            setupShare(Song1,Sing1,Url1);
                            player.start();
                            imgplay.setImageResource(R.drawable.ic_pause);
                            updateSeekbar();
//                            Toast.makeText(PlayMusicActivity.this, Url1, Toast.LENGTH_SHORT).show();
                            return;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}