package vku.lvthang.appmp3.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import vku.lvthang.appmp3.Adapter.MoodAdapter;
import vku.lvthang.appmp3.DanhsachbaiActivity;
import vku.lvthang.appmp3.Model.MoodModel;
import vku.lvthang.appmp3.R;


public class MoodFragment extends Fragment {

    public static final String EXTRA_TITLE_Mood = "Title";
    public static final String EXTRA_IMG_Mood = "Img";

    View v;
    private static String JSON_URL = "https://run.mocky.io/v3/fbc54388-328b-4e73-97bb-33b8f52c43b1";
    RecyclerView recyclerViewMood;
    List<MoodModel> listMood;

    private MoodAdapter moodAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoodFragment.GetData getData = new MoodFragment.GetData();
        getData.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_mood, container, false);
        recyclerViewMood =(RecyclerView) v.findViewById(R.id.recylcerViewMood);
        listMood= new ArrayList<>();

        return v;
    }
    @SuppressLint("StaticFieldLeak")
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
                JSONArray jsonArray = jsonObject.getJSONArray("tamtrangvahoatdong");
                for (int i=0; i< jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    String Img = jsonObject1.getString("img");
                    String Title = jsonObject1.getString("title");


                    listMood.add(new MoodModel(Img,Title));

                }

            } catch (JSONException e) {
                Toast.makeText(getActivity(),"Không có kết nối internet",Toast.LENGTH_SHORT).show();
            }
            PutDataIntoRecylcerView( listMood);
        }
    }
    private void PutDataIntoRecylcerView(List<MoodModel> listMood){
        moodAdapter =new MoodAdapter(listMood, this, new MoodAdapter.IClickItemListener() {
            @Override
            public void onClickItemHits(int moodModel) {
                Intent intent = new Intent(getActivity(), DanhsachbaiActivity.class);
//
                MoodModel clickItem = listMood.get(moodModel);

                intent.putExtra(EXTRA_IMG_Mood, clickItem.getImg());
                intent.putExtra(EXTRA_TITLE_Mood, clickItem.getTitle());
//
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewMood.setLayoutManager(linearLayoutManager);
        recyclerViewMood.setAdapter(moodAdapter);
    }
}