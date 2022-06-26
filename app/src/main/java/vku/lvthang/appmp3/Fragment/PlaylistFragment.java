package vku.lvthang.appmp3.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.lvthang.appmp3.Adapter.BannerAdapter;
import vku.lvthang.appmp3.Adapter.PlayListAdapter;
import vku.lvthang.appmp3.DanhsachPlaylistActivity;
import vku.lvthang.appmp3.DanhsachbaihatActivity;
import vku.lvthang.appmp3.Model.PlaylistModel;
import vku.lvthang.appmp3.Model.QuangcaoModel;
import vku.lvthang.appmp3.R;
import vku.lvthang.appmp3.Sevice.APIService;
import vku.lvthang.appmp3.Sevice.DataService;

public class PlaylistFragment extends Fragment {

    View view;
    ListView lvPlaylist;
    TextView txtTitlePlaylit,txtMorePlaylist;

    PlayListAdapter playListAdapter;
    ArrayList<PlaylistModel> arrayplaylist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_playlist, container, false);
        lvPlaylist= view.findViewById(R.id.LVPlaylist);
        txtTitlePlaylit= view.findViewById(R.id.txtTitlePlaylist);
        txtMorePlaylist=view.findViewById(R.id.txtMorePlaylist);
        anhxa();
       GetData();
       txtMorePlaylist.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), DanhsachPlaylistActivity.class);
               startActivity(intent);
           }
       });
        return view;
    }

    private void anhxa() {

    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<PlaylistModel>> callback = dataService.GetDataPlaylistDay();
        callback.enqueue(new Callback<List<PlaylistModel>>() {
            @Override
            public void onResponse(Call<List<PlaylistModel>> call, Response<List<PlaylistModel>> response) {
               arrayplaylist = (ArrayList<PlaylistModel>) response.body();
               playListAdapter = new PlayListAdapter(getActivity(), android.R.layout.simple_list_item_1,arrayplaylist);
               lvPlaylist.setAdapter(playListAdapter );
                setListViewHeightBasedOnChildren(lvPlaylist);
                lvPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), DanhsachbaihatActivity.class);
                        intent.putExtra("itemplaylist",arrayplaylist.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PlaylistModel>> call, Throwable t) {

            }
        });

    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}