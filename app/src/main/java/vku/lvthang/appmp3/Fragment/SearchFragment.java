package vku.lvthang.appmp3.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Adapter.SearchBaihatAdapter;
import vku.lvthang.appmp3.Model.BaihatModel;
import vku.lvthang.appmp3.R;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;
import vku.lvthang.appmp3.VNCharacterUtils;

public class SearchFragment extends Fragment {

    View v;
    Toolbar toolbar;
    RecyclerView Rcvtimkem;
    TextView txtNull;
    SearchBaihatAdapter searchBaihatAdapter;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);
//        searchView = (SearchView) v.findViewById(R.id.search); // inititate a search view
//        CharSequence query = searchView.getQuery(); // get the query string currently in the text field
//        Toast.makeText(getActivity(),query,Toast.LENGTH_SHORT).show();
        toolbar = v.findViewById(R.id.ToolbarTimkiem);
        Rcvtimkem = v.findViewById(R.id.Rcvtimkiem);
        txtNull = v.findViewById(R.id.txtNull);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
       inflater.inflate(R.menu.search_view,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTukhoa(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    public  void SearchTukhoa(String query){
        DataService dataService= APIService.getService();
        Call<List<BaihatModel>> callback =dataService.GetDataSearchbaihat(query);
        callback.enqueue(new Callback<List<BaihatModel>>() {
            @Override
            public void onResponse(Call<List<BaihatModel>> call, Response<List<BaihatModel>> response) {
                ArrayList<BaihatModel> baihatModelArrayList = (ArrayList<BaihatModel>) response.body();
                if (baihatModelArrayList.size()>0){
                    searchBaihatAdapter = new SearchBaihatAdapter(getActivity(),baihatModelArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    Rcvtimkem.setLayoutManager(linearLayoutManager);
                    Rcvtimkem.setAdapter(searchBaihatAdapter);
                    txtNull.setVisibility(View.GONE);
                    Rcvtimkem.setVisibility(View.VISIBLE);
                }else {
                    Rcvtimkem.setVisibility(View.GONE);
                    txtNull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BaihatModel>> call, Throwable t) {

            }
        });
    }
}