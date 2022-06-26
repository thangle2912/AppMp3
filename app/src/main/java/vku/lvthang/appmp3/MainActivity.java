package vku.lvthang.appmp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import vku.lvthang.appmp3.Adapter.MainViewPagerAdapter;
import vku.lvthang.appmp3.Fragment.AllSongsFragment;
import vku.lvthang.appmp3.Fragment.SearchFragment;
import vku.lvthang.appmp3.Fragment.HomeFragment;

public class MainActivity extends AppCompatActivity
{
    public static final String EXTRA_SONG = "Song";
    public static final String EXTRA_IMG = "Img";
    public static final String EXTRA_URL = "Url";
    public static final String EXTRA_SING = "Sing";
    public static final String EXTRA_ID = "Id";
    BottomNavigationView bnv;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        init();
//        loadFragment(new HomeFragment());
//
////        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//
////        getSupportFragmentManager().beginTransaction().replace(R.id.FrameConatiner,new HomeFragment()).commit();
//
//
//
//        bnv=(BottomNavigationView)findViewById(R.id.bottomNavigation);
//        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item)
//            {
//                Fragment temp=null;
//
//                switch (item.getItemId())
//                {
//                    case R.id.menu_home : temp=new HomeFragment();
//                        break;
//                    case R.id.menu_search : temp=new SearchFragment();
//                        break;
//                    case R.id.menu_allSongs: temp=new AllSongsFragment();
//                        break;
//
//
//                }
//
//               loadFragment(temp);
//
//                return true;
//            }
//        });

    }
    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new HomeFragment(),"Trang Chủ");
        mainViewPagerAdapter.addFragment(new SearchFragment(),"Tìm Kiếm");
//        mainViewPagerAdapter.addFragment(new AllSongsFragment(),"Bài Hát");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_library_music);
    }
    private void anhxa() {
        tabLayout = findViewById(R.id.myTablayout);
        viewPager = findViewById(R.id.myViewPager);
    }
//    public boolean loadFragment(Fragment fragment){
//        if (fragment != null){
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.FrameConatiner,fragment)
//                    .commit();
//        }
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (bnv.getSelectedItemId()==R.id.menu_home){
//            super.onBackPressed();
//            finish();
//        }else {
//            bnv.setSelectedItemId(R.id.menu_home);
//        }
//
//    }
}
