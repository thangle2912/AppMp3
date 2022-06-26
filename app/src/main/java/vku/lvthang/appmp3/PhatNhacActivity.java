package vku.lvthang.appmp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import vku.lvthang.appmp3.Adapter.ViewPagerPhatnhacAdapter;
import vku.lvthang.appmp3.Fragment.DanhsachbaihatPhatnhacFragment;
import vku.lvthang.appmp3.Fragment.DiaNhacFragment;
import vku.lvthang.appmp3.Model.BaihatModel;
import vku.lvthang.appmp3.Model.Theloai;

public class PhatNhacActivity extends AppCompatActivity {
    Toolbar toolbar;
    SeekBar seekBar;
    TextView txtTimesong,txtTotalTimesong;
    ImageButton imgPlay,imgNext,imgPre,imgRepeat,imgRandom;
    ViewPager viewPager;
    DiaNhacFragment diaNhacFragment;
    DanhsachbaihatPhatnhacFragment danhsachbaihatPhatnhacFragment;
    MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false,random=false,next = false;
    public static ArrayList<BaihatModel> arrayListBaihat = new ArrayList<>();
    public static ViewPagerPhatnhacAdapter viewPagerPhatnhacAdapter;
    ShareDialog shareDialog;
    ShareLinkContent linkContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phat_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        GetDataIntent();
        init();
        eventClick();

    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPagerPhatnhacAdapter.getItem(1) != null){
                    if (arrayListBaihat.size()>0){
                        diaNhacFragment.Playnhac(arrayListBaihat.get(0).getHinh());
                        handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this,000);
                    }
                }
            }
        },500);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.ic_play);
                    if (diaNhacFragment.objectAnimator!=null) {
                        diaNhacFragment.objectAnimator.pause();
                    }
                }else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.ic_pause);
                        if (diaNhacFragment.objectAnimator!=null){
                            diaNhacFragment.objectAnimator.resume();
                        }
                }
            }
        });
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false){
                    if (random==true){
                        random=false;
                        imgRepeat.setImageResource(R.drawable.process_arrowsed);
                        imgRandom.setImageResource(R.drawable.shuffleee);
                    }
                    imgRepeat.setImageResource(R.drawable.process_arrowsed);
                    repeat=true;
                }else {
                    imgRepeat.setImageResource(R.drawable.process_arrows);
                    repeat= false;
                }
            }
        });
        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random == false){
                    if (repeat==true){
                        repeat=false;
                        imgRandom.setImageResource(R.drawable.shuffleeddd);
                        imgRepeat.setImageResource(R.drawable.process_arrows);
                    }
                    imgRandom.setImageResource(R.drawable.shuffleeddd);
                    random =true;
                }else {
                    imgRandom.setImageResource(R.drawable.shuffleee);
                    random= false;
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListBaihat.size()>0){
                    if (mediaPlayer.isPlaying() || mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                    if (position <arrayListBaihat.size()){
                        imgPlay.setImageResource(R.drawable.ic_pause);
                        position++;
                        if (repeat == true){
                            if (position==0){
                                position = arrayListBaihat.size();
                            }
                            position -=1;
                        }
                        if (random == true){
                            Random randomindex = new Random();
                            int index = randomindex.nextInt(arrayListBaihat.size());
                            if (index ==position){
                                position = index-1;
                            }
                            position = index;
                        }
                        if (position > (arrayListBaihat.size()-1)){
                            position=0;
                        }
                        new PhatNhac().execute(arrayListBaihat.get(position).getLink());
                        diaNhacFragment.Playnhac(arrayListBaihat.get(position).getHinh());
                        getSupportActionBar().setTitle(arrayListBaihat.get(position).getTen());
                        updateTime();

                    }
                }
                imgRepeat.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgRepeat.setClickable(true);
                        imgNext.setClickable(true);
                    }
                },500);
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListBaihat.size()>0){
                    if (mediaPlayer.isPlaying() || mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                    if (position <arrayListBaihat.size()){
                        imgPlay.setImageResource(R.drawable.ic_pause);
                        position--;
                        if(position <0){
                            position = arrayListBaihat.size()-1;
                        }

                        if (repeat == true){
                            position +=1;
                        }
                        if (random == true){
                            Random randomindex = new Random();
                            int index = randomindex.nextInt(arrayListBaihat.size());
                            if (index ==position){
                                position = index-1;
                            }
                            position = index;
                        }
                        new PhatNhac().execute(arrayListBaihat.get(position).getLink());
                        diaNhacFragment.Playnhac(arrayListBaihat.get(position).getHinh());
                        getSupportActionBar().setTitle(arrayListBaihat.get(position).getTen());
                        updateTime();
                    }
                }
                imgRepeat.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgRepeat.setClickable(true);
                        imgNext.setClickable(true);
                    }
                },500);
            }
        });
    }


    private void GetDataIntent() {
        Intent intent = getIntent();
        arrayListBaihat.clear();
        if (intent!=null){
            if (intent.hasExtra("music")){
                BaihatModel baihatModel = intent.getParcelableExtra("music");
                arrayListBaihat.add(baihatModel);
            }
            if (intent.hasExtra("musics")){
                ArrayList<BaihatModel> baihatModelArrayList = intent.getParcelableArrayListExtra("musics");
                arrayListBaihat = baihatModelArrayList;
            }
        }

    }


    private void init() {
        toolbar = findViewById(R.id.ToolbarPhatnhac);
        seekBar= findViewById(R.id.SeebarSong);
        imgNext= findViewById(R.id.imgBtnNext);
        imgPlay =findViewById(R.id.imgBtnPlay);
        imgPre=findViewById(R.id.imgBtnpre);
        imgRepeat=findViewById(R.id.imgBtnRepeat);
        imgRandom=findViewById(R.id.imgBtnshuffle);

        txtTimesong = findViewById(R.id.txtTimesong);
        txtTotalTimesong =findViewById(R.id.txtTotalTimesong);

        viewPager=findViewById(R.id.VPPhatnhac);

        diaNhacFragment= new DiaNhacFragment();



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Danh sách chủ đề");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.reset();
                arrayListBaihat.clear();
            }
        });

        danhsachbaihatPhatnhacFragment = new DanhsachbaihatPhatnhacFragment();
        viewPagerPhatnhacAdapter = new ViewPagerPhatnhacAdapter(getSupportFragmentManager());
        viewPagerPhatnhacAdapter.AddFragment(danhsachbaihatPhatnhacFragment);
        viewPagerPhatnhacAdapter.AddFragment(diaNhacFragment);
        viewPager.setAdapter(viewPagerPhatnhacAdapter);


        diaNhacFragment= (DiaNhacFragment) viewPagerPhatnhacAdapter.getItem(1);
        if (arrayListBaihat.size()>0){
            getSupportActionBar().setTitle(arrayListBaihat.get(0).getTen());
            new PhatNhac().execute(arrayListBaihat.get(0).getLink());
            imgPlay.setImageResource(R.drawable.ic_pause);
        }
    }

    class PhatNhac extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String music) {
            super.onPostExecute(music);
            try {
                mediaPlayer= new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(music);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            updateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void updateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer !=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,100);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },100);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next==true){
                        if (position <arrayListBaihat.size()){
                            imgPlay.setImageResource(R.drawable.ic_pause);
                            position++;
                            if (repeat == true){
                                if (position==0){
                                    position = arrayListBaihat.size();
                                }
                                position -=1;
                            }
                            if (random == true){
                                Random randomindex = new Random();
                                int index = randomindex.nextInt(arrayListBaihat.size());
                                if (index ==position){
                                    position = index-1;
                                }
                                position = index;
                            }
                            if (position > (arrayListBaihat.size()-1)){
                                position=0;
                            }
                            new PhatNhac().execute(arrayListBaihat.get(position).getLink());
                            diaNhacFragment.Playnhac(arrayListBaihat.get(position).getHinh());
                            getSupportActionBar().setTitle(arrayListBaihat.get(position).getTen());
                        }
                    imgRepeat.setClickable(false);
                    imgNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgRepeat.setClickable(true);
                            imgNext.setClickable(true);
                        }
                     },500);
                    next = false;
                    handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this,100);
                }
            }
        },100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tooblbar_phatnhac,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.share:
//                if (arrayListBaihat.size()>0) {
//                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                            .setQuote(arrayListBaihat.get(position).getTen() +" - "+arrayListBaihat.get(position).getCasi())
//                            .setContentUrl(Uri.parse(arrayListBaihat.get(position).getLink()))
//                            .build();
//
//                }
//
//                ShareDialog.show(PhatNhacActivity.this, linkContent);
                break;
            case R.id.dowl:

                if (arrayListBaihat.size()>0) {
                    String URL = arrayListBaihat.get(position).getLink();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
                    String title = URLUtil.guessFileName(URL, null, null);
                    request.setTitle(title);
                    request.setDescription("Đang tải " + arrayListBaihat.get(position).getTen());
                    String cookie = CookieManager.getInstance().getCookie(URL);
                    request.addRequestHeader("cookie", cookie);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    Toast.makeText(PhatNhacActivity.this, "Bắt đầu tải ", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return true;
    }

}