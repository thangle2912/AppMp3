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

import vku.lvthang.appmp3.Adapter.SingerAdapter;

import vku.lvthang.appmp3.DanhsachbaiActivity;
import vku.lvthang.appmp3.Model.SingerModel;

import vku.lvthang.appmp3.R;




public class ListSingerFragment extends Fragment {


    public static final String EXTRA_SINGER = "Singer";
    public static final String EXTRA_IMGsinger = "Img";
    View v;
    private static String JSON_URL = "https://run.mocky.io/v3/eefbb127-ad17-4d93-b01d-932c9838b7dd";
    RecyclerView recyclerViewSinger;
    List<SingerModel> listSinger;

    private SingerAdapter singerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_list_singer, container, false);
        recyclerViewSinger =(RecyclerView) v.findViewById(R.id.recylcerViewSinger);
        listSinger = new ArrayList<>();

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
                JSONArray jsonArray = jsonObject.getJSONArray("singer");
                for (int i=0; i< jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    String Sing = jsonObject1.getString("sing");
                    String Img = jsonObject1.getString("img");


                    listSinger.add(new SingerModel(Img,Sing));

                }

            } catch (JSONException e) {
                Toast.makeText(getActivity(),"Không có kết nối internet",Toast.LENGTH_SHORT).show();
            }
            PutDataIntoRecylcerView( listSinger);
        }
    }
    private void PutDataIntoRecylcerView(List<SingerModel> listSinger){
        singerAdapter =new SingerAdapter(listSinger, this, new SingerAdapter.IClickItemListener() {
            @Override
            public void onClickItemSinger(int singerModel) {
                Intent intent = new Intent(getActivity(), DanhsachbaiActivity.class);

                SingerModel clickItem = listSinger.get(singerModel);

                intent.putExtra(EXTRA_IMGsinger, clickItem.getImg());
                intent.putExtra(EXTRA_SINGER, clickItem.getNameSinger());

                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewSinger.setLayoutManager(linearLayoutManager);
        recyclerViewSinger.setAdapter(singerAdapter);
    }
}