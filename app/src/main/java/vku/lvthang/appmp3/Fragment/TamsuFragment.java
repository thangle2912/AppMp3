package vku.lvthang.appmp3.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

import vku.lvthang.appmp3.Adapter.TamsuAdapter;
import vku.lvthang.appmp3.DanhsachbaiActivity;
import vku.lvthang.appmp3.Model.TamsuModel;
import vku.lvthang.appmp3.R;

public class TamsuFragment extends Fragment {
    public static final String EXTRA_TITLE_Tamsu = "Title";
    public static final String EXTRA_IMG_Tamsu = "Img";

    ProgressDialog progressDialog;
    View v;
    private static String JSON_URL = "https://run.mocky.io/v3/4c24663d-c39e-4f66-868c-ee7b6e40d555";
    RecyclerView recyclerViewTamsu;
    List<TamsuModel> tamsuList;

    private TamsuAdapter tamsuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_tamsu, container, false);
        recyclerViewTamsu =(RecyclerView) v.findViewById(R.id.recylcerViewTamsu);
        tamsuList = new ArrayList<>();

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
                JSONArray jsonArray = jsonObject.getJSONArray("tamsu");
                for (int i=0; i< jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    String Title = jsonObject1.getString("title");
                    String Img = jsonObject1.getString("img");


                    tamsuList.add(new TamsuModel(Img,Title));

                }

            } catch (JSONException e) {
                Toast.makeText(getActivity(),"Không có kết nối internet",Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
            PutDataIntoRecylcerView( tamsuList);
        }
    }
    private void PutDataIntoRecylcerView(List<TamsuModel> tamsuList){
        tamsuAdapter =new TamsuAdapter(tamsuList, this, new TamsuAdapter.IClickItemListener() {
            @Override
            public void onClickItemHits(int tamsuModel) {
                Intent intent = new Intent(getActivity(), DanhsachbaiActivity.class);
//
                TamsuModel clickItem =  tamsuList.get(tamsuModel);

                intent.putExtra(EXTRA_IMG_Tamsu, clickItem.getImg());
                intent.putExtra(EXTRA_TITLE_Tamsu, clickItem.getTitle());
//
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewTamsu.setLayoutManager(linearLayoutManager);
        recyclerViewTamsu.setAdapter(tamsuAdapter);
    }
}